package com.capstone.library.controllers;

import com.capstone.library.model.Book;
import com.capstone.library.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("book/")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(CatalogueController.class);

    @Autowired
    BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping("/createBook")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        try {
            Book new_book = bookRepository.save(new Book(book.getTitle(), book.getAuthor(), true));
            return new ResponseEntity<>(new_book, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getBook/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") Long id) {
        Book book =
                bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No " +
                        "book with id: " + id + " found"));
        return new ResponseEntity<>(book, HttpStatus.OK);
    }


    @GetMapping("/getAllBooks")
    public ResponseEntity<Book> getAllBooks() {
        Book book =
                bookRepository.findByIsAvailable(true).orElseThrow(() -> new ResourceNotFoundException(
                        "Not even one book is available!"));
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}
