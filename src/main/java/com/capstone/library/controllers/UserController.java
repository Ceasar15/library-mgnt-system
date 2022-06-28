package com.capstone.library.controllers;

import com.capstone.library.payload.request.UserRequest;
import com.capstone.library.repository.RoleTypeRepository;
import com.capstone.library.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> createLlibrarian(@RequestBody UserRequest userRequest){

    }

}
