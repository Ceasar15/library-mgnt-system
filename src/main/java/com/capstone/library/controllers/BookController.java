package com.capstone.library.controllers;

import com.capstone.library.exception.ResourceNotFoundException;
import com.capstone.library.model.Book;
import com.capstone.library.model.Catalogue;
import com.capstone.library.payload.request.CreateBook;
import com.capstone.library.repository.BookRepository;
import com.capstone.library.repository.CatalogueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

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

    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            logger.error("IOException: " + e.getMessage());
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
            logger.error("IOException: " + ioe.getMessage());

        } catch (DataFormatException dfe) {
            logger.error("DataFormatException: " + dfe.getMessage());

        }
        return outputStream.toByteArray();
    }

    //    @PostMapping("/createBook")
    @RequestMapping(value = "/createBook", method = RequestMethod.POST, consumes =
            {"multipart/form" + "-data"})
    public ResponseEntity<?> createBook(@ModelAttribute CreateBook createBook) throws IOException {
        Book new_book = new Book(createBook.getTitle(), createBook.getAuthor(), true);
        String strCatalogue = createBook.getCatalogue();
        MultipartFile imageFile = createBook.getImageFile();
//        System.out.println("Original Image Byte Size - " + imageFile.getBytes().length);
//        System.out.println("Original Image Byte Size - " + imageFile.getName());
        Set<Catalogue> catalogue = new HashSet<>();
        if (strCatalogue == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            Catalogue bookCatalogue =
                    catalogueRepository.findByCatalogue(strCatalogue).orElseThrow(() -> new ResourceNotFoundException("No catalogue found"));
            catalogue.add(bookCatalogue);
        }
        new_book.setImage(imageFile.getBytes());
        new_book.setCatalogue(catalogue);
        Book responseBook = bookRepository.save(new_book);
//        System.out.println("responseBook: " + Arrays.toString(responseBook.getImage()));
//        ByteArrayResource resource = new ByteArrayResource(responseBook.getImage());
//        System.out.println("resource:" + resource);
        return new ResponseEntity<>(responseBook, HttpStatus.CREATED);
//        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength
//        (resource.contentLength()).header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition
//        .attachment().filename("whatever").build().toString()).body(resource);

    }

    @GetMapping("/getBook/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") Long id) throws IOException {
        Book book =
                bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No " +
                        "book with id: " + id + " found"));
//        decompressBytes(book.getImage());
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            List<Book> book;
            book = bookRepository.findByIsAvailable(true);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
