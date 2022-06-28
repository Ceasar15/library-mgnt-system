package com.capstone.library.controllers;

import com.capstone.library.model.Actors;
import com.capstone.library.model.RoleType;
import com.capstone.library.model.UserModel;
import com.capstone.library.payload.request.UserRequest;
import com.capstone.library.repository.RoleTypeRepository;
import com.capstone.library.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("user/")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(CatalogueController.class);

    @Autowired
    RoleTypeRepository roleTypeRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/createLibrarian")
    public ResponseEntity<?> createLibrarian(@RequestBody UserRequest userRequest) {
        UserModel user = new UserModel(userRequest.getUsername(), userRequest.getPassword());
        String strRoleType = userRequest.getRoleType();
        Set<RoleType> roleType = new HashSet<>();

        if (strRoleType == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            RoleType roleType1 =
                    roleTypeRepository.findByName(Actors.valueOf(strRoleType)).orElseThrow(() -> new ResourceNotFoundException("This role does not exists!"));
            roleType.add(roleType1);
        }
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

}
