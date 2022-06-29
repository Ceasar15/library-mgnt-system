package com.capstone.library.repository;

import com.capstone.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitle(Book title);

    List<Book> findByIsAvailable(Boolean isAvailable);

    Optional<Book> findById(Book id);
}
