package com.capstone.library.controllers;

import com.capstone.library.model.Actors;
import com.capstone.library.model.RoleType;
import com.capstone.library.model.UserModel;
import com.capstone.library.payload.request.LoginRequest;
import com.capstone.library.payload.request.UserRequest;
import com.capstone.library.payload.response.JwtResponse;
import com.capstone.library.repository.RoleTypeRepository;
import com.capstone.library.repository.UserRepository;
import com.capstone.library.security.jwt.JwtUtils;
import com.capstone.library.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("user/")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(CatalogueController.class);
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RoleTypeRepository roleTypeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/createLibrarian")
    public ResponseEntity<?> createLibrarian(@RequestBody UserRequest userRequest) {
        UserModel user = new UserModel(userRequest.getUsername(), userRequest.getEmail());
        String strRoleType = userRequest.getRoleType();
        Set<RoleType> roleType = new HashSet<>();
        logger.warn("Request: " + userRequest);
        logger.info("roleType: " + strRoleType);
        if (strRoleType == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            RoleType roleType1 =
                    roleTypeRepository.findByName(Actors.valueOf(strRoleType)).orElseThrow(() -> new ResourceNotFoundException("This role does not exists!"));
            roleType.add(roleType1);
        }
        user.setPassword();
        user.setRoleType(roleType);
        UserModel responseUser = userRepository.save(user);
        logger.info("User of name: " + responseUser.getUsername() + " is created.");
        return new ResponseEntity<>(responseUser, HttpStatus.CREATED);
    }

    @GetMapping("/getAllLibrarians")
    public ResponseEntity<?> getAllLibrarians() {
        try {
            List<UserModel> librarians;
            librarians = userRepository.findAll();
            return new ResponseEntity<>(librarians, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles =
                userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
                userDetails.getEmail(), roles));
    }

}
