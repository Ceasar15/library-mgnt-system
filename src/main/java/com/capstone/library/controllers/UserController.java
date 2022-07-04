package com.capstone.library.controllers;

import com.capstone.library.model.Actors;
import com.capstone.library.model.RoleType;
import com.capstone.library.model.UserModel;
import com.capstone.library.payload.request.ChangePasswordRequest;
import com.capstone.library.payload.request.LoginRequest;
import com.capstone.library.payload.request.SignupRequest;
import com.capstone.library.payload.request.UserRequest;
import com.capstone.library.payload.response.JwtResponse;
import com.capstone.library.payload.response.MessageResponse;
import com.capstone.library.repository.RoleTypeRepository;
import com.capstone.library.repository.UserModelMini;
import com.capstone.library.repository.UserRepository;
import com.capstone.library.security.jwt.JwtUtils;
import com.capstone.library.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
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

    @PostMapping("/admin/createLibrarian")
    public ResponseEntity<?> createLibrarian(@RequestBody UserRequest userRequest) {
        UserModel user = new UserModel(userRequest.getUsername(), userRequest.getEmail());
        String strRoleType = userRequest.getRoleType();
        Set<RoleType> roleType = new HashSet<>();
        if (strRoleType == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            RoleType roleType1 =
                    roleTypeRepository.findByRole(Actors.valueOf(strRoleType)).orElseThrow(() -> new ResourceNotFoundException("This role does not exists!"));
            roleType.add(roleType1);
        }

        user.setPassword(encoder.encode(user.finalPassword));
        user.setRoleType(roleType);
        UserModel responseUser = userRepository.save(user);
        Map<String, Object> response = new HashMap<>();
        response.put("id", responseUser.getId());
        response.put("username", responseUser.getUsername());
        response.put("email", responseUser.getEmail());
        response.put("generatedPassword", responseUser.finalPassword);
        response.put("role", responseUser.getRoleType());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/admin/getAllLibrarians")
    public ResponseEntity<?> getAllLibrarians() {
        try {
            List<UserModelMini> librarians;
            librarians = userRepository.findAllLibrarians();
            return new ResponseEntity<>(librarians, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        logger.info("Authentication : " + authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles =
                userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
                userDetails.getEmail(), roles));
    }

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already " + "taken!"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in " + "use!"));
        }
        // Create new user's account
        UserModel user = new UserModel(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        String strRoles = signUpRequest.getRoleType();
        Set<RoleType> roles = new HashSet<>();
        if (strRoles == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: No role provided! "));
        } else {
            if (strRoles.equals("Librarian")) {
                RoleType Librarian =
                        roleTypeRepository.findByRole(Actors.valueOf(strRoles)).orElseThrow(() -> new ResourceNotFoundException("Error: Role librarian is not " + "found."));
                roles.add(Librarian);
            } else if (strRoles.equals("Admin")) {
                RoleType Admin =
                        roleTypeRepository.findByRole(Actors.valueOf(strRoles)).orElseThrow(() -> new ResourceNotFoundException("Error: Role admin is not found" + "."));
                roles.add(Admin);
            } else {
                RoleType User =
                        roleTypeRepository.findByRole(Actors.valueOf(strRoles)).orElseThrow(() -> new ResourceNotFoundException("Error: Role user is not found" + "."));
                roles.add(User);
            }
            ;

        }
        user.setRoleType(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PutMapping(value = "/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserModel user = userRepository.findByEmail(((UserDetailsImpl) principal).getEmail());

        if (!Objects.equals(changePasswordRequest.getNewPassword(),
                changePasswordRequest.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Passwords do not match!"));
        }

        user.setPassword(encoder.encode(changePasswordRequest.getConfirmPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Password changed Successfully"));
    }
}
