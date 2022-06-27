package com.capstone.library.repository;

import com.capstone.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitle(Book title);

    Optional<Book> findById(Book id);
}
