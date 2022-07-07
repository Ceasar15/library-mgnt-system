package com.capstone.library.controllers;


import com.capstone.library.exception.ResourceNotFoundException;
import com.capstone.library.model.Book;
import com.capstone.library.model.BookRequestModel;
import com.capstone.library.model.UserModel;
import com.capstone.library.payload.request.RequestForABook;
import com.capstone.library.payload.response.MessageResponse;
import com.capstone.library.repository.AllBookRequest;
import com.capstone.library.repository.BookRepository;
import com.capstone.library.repository.BookRequestRepository;
import com.capstone.library.repository.UserRepository;
import com.capstone.library.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.capstone.library.model.ApprovalStatus.Accepted;
import static com.capstone.library.model.ApprovalStatus.Pending;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("bookRequest/")
public class BookRequestController {
    private static final Logger logger = LoggerFactory.getLogger(BookRequestController.class);

    @Autowired
    BookRequestRepository bookRequestRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;

    public BookRequestController(BookRequestRepository bookRequestRepository) {
        this.bookRequestRepository = bookRequestRepository;
    }

    @GetMapping("/admin/getAllBookRequest")
    public ResponseEntity<?> getAllBookRequest() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            List<AllBookRequest> allBookRequest;
            allBookRequest = bookRequestRepository.findAllBookRequests();

            return new ResponseEntity<>(allBookRequest, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("No book request available!"));

        }
    }

    @PostMapping("/user/makeBookRequest")
    public ResponseEntity<?> makeBookRequest(@RequestBody RequestForABook requestForABook) {
        System.out.println("request: " + requestForABook.getId());


        Long book_id = requestForABook.getId();
        BookRequestModel bookRequest = new BookRequestModel(requestForABook.getId());
        LocalDateTime date = LocalDateTime.now();
        bookRequest.setDateRequested(date);
//      save the book
        Set<Book> book = new HashSet<>();
        Book bookInstance =
                bookRepository.findById(book_id).orElseThrow(() -> new ResourceNotFoundException("No " + "book of id:" + book_id + " found"));
        book.add(bookInstance);
//         save the user
        Set<UserModel> user = new HashSet<>();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserModel userInstance = userRepository.findByEmail(((UserDetailsImpl) principal).getEmail());
        user.add(userInstance);

        bookRequest.setDateReturned(null);
        bookRequest.setApprovalStatus(Pending);
        bookRequest.setUser(user);
        bookRequest.setBook(book);
        BookRequestModel savedBookRequestBook = bookRequestRepository.save(bookRequest);

        return ResponseEntity.ok("Book booked!");
    }

    @PutMapping("/admin/approveBookRequest/{id}/{bookId}")
    public ResponseEntity<?> approveBookRequest(@PathVariable("id") Long id,
                                                @PathVariable("bookId") Long bookId) {
        try {
            BookRequestModel bookRequest =
                    bookRequestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No" + " " + "book request with id: " + id + " found"));

            Book bookInstance =
                    bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException(
                            "No " + "book of id:" + bookRequest.getBook() + " found"));

            bookInstance.setAvailable(Boolean.FALSE);
            bookRepository.save(bookInstance);
            bookRequest.setApprovalStatus(Accepted);
            bookRequestRepository.save(bookRequest);
            return ResponseEntity.ok("Book Approved Successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Error:" + e.getMessage());
        }

    }
}