package com.capstone.library.controllers;

import com.capstone.library.model.Book;
import com.capstone.library.model.Catalogue;
import com.capstone.library.payload.request.BookRequest;
import com.capstone.library.repository.BookRepository;
import com.capstone.library.repository.CatalogueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("book/")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(CatalogueController.class);

    @Autowired
    BookRepository bookRepository;
    @Autowired
    CatalogueRepository catalogueRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping("/createBook")
    public ResponseEntity<?> createBook(@RequestBody BookRequest bookRequest) {
        Book new_book = new Book(bookRequest.getTitle(), bookRequest.getAuthor(), true);
        try {
            logger.error("Book details" + new_book);
            Set<String> strCatalogue = bookRequest.getCatalogue();
            Set<Catalogue> catalogue = new HashSet<>();
            if (strCatalogue == null) {
                return new ResponseEntity<>(new_book, HttpStatus.BAD_REQUEST);
            } else {
                Catalogue bookCatalogue = catalogueRepository.findByCatalogue(String.valueOf(strCatalogue)).orElseThrow(() -> new ResourceNotFoundException("No catalogue found"));
                catalogue.add(bookCatalogue);
            }

        } catch (Exception e) {
            logger.error("error: " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        new_book.set
        return new ResponseEntity<>(new_book, HttpStatus.CREATED);

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
                bookRepository.findByIsAvailable(true).orElseThrow(() -> new ResourceNotFoundException("Not even one book is available!"));
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}
