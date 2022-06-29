package com.capstone.library.controllers;

import com.capstone.library.exception.ResourceNotFoundException;
import com.capstone.library.model.Book;
import com.capstone.library.model.Catalogue;
import com.capstone.library.payload.request.CreateBookRequest;
import com.capstone.library.repository.BookRepository;
import com.capstone.library.repository.CatalogueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
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
    public ResponseEntity<?> createBook(@RequestBody CreateBookRequest createBookRequest) {
        Book new_book = new Book(createBookRequest.getTitle(), createBookRequest.getAuthor(), true);
        String strCatalogue = createBookRequest.getCatalogue();
        Set<Catalogue> catalogue = new HashSet<>();
        if (strCatalogue == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            Catalogue bookCatalogue =
                    catalogueRepository.findByCatalogue(String.valueOf(strCatalogue)).orElseThrow(() -> new ResourceNotFoundException("No catalogue found"));
            catalogue.add(bookCatalogue);
        }

        new_book.setCatalogue(catalogue);
        Book responseBook = bookRepository.save(new_book);
        return new ResponseEntity<>(responseBook, HttpStatus.CREATED);

    }

    @GetMapping("/getBook/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") Long id) {
        Book book =
                bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No " +
                        "book with id: " + id + " found"));
        return new ResponseEntity<>(book, HttpStatus.OK);
    }


    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            List<Book> book;
            book = bookRepository.findByIsAvailable(true);
//                .orElseThrow(() -> new ResourceNotFoundException(
//                "Not even one book is available!"));
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }
}
