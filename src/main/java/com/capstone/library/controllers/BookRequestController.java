package com.capstone.library.controllers;


import com.capstone.library.model.BookRequestModel;
import com.capstone.library.payload.response.MessageResponse;
import com.capstone.library.repository.BookRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("bookRequest/")
public class BookRequestController {
    private static final Logger logger = LoggerFactory.getLogger(BookRequestController.class);

    @Autowired
    BookRequestRepository bookRequestRepository;

    public BookRequestController(BookRequestRepository bookRequestRepository) {
        this.bookRequestRepository = bookRequestRepository;
    }

    @GetMapping("/admin/getAllBookRequest")
    public ResponseEntity<?> getAllBookRequest() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            List<BookRequestModel> allBookRequest;
            allBookRequest = bookRequestRepository.findAll();
            return new ResponseEntity<>(allBookRequest, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("No book request available!"));

        }

    }


}
