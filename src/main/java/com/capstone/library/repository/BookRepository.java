package com.capstone.library.repository;

import com.capstone.library.model.Book;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitle(Book title);

    @Cacheable("book")
    List<Book> findByIsAvailable(Boolean isAvailable);

    Optional<Book> findById(Book id);

//    List<Book> findBookById(Set<Book> id);


}
