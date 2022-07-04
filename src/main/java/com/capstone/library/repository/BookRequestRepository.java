package com.capstone.library.repository;

import com.capstone.library.model.ApprovalStatus;
import com.capstone.library.model.Book;
import com.capstone.library.model.BookRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface BookRequestRepository extends JpaRepository<BookRequestModel, Long> {

    public static final String FindAllBookRequests = "SELECT book_request.id,\n" + "       " +
            "book_request.approval_status,\n" + "       book_request.date_returned,\n" + "       " +
            "book_request.date_requested,\n" + "       book_request_book.book bookId,\n" + "       " + "book.author,\n" + "       book.title,\n" + "       u.id                   userId,\n" + " " + "      u.email,\n" + "       u.username\n" + "FROM book_request\n" + "         LEFT JOIN " + "book_request_book ON book_request_book.book_request = book_request.id\n" + "         LEFT" + " JOIN book ON book_request_book.book = book.id\n" + "         LEFT JOIN book_request_user" + " bru on book_request.id = bru.book_request\n" + "         LEFT JOIN users u on bru.users " + "= u.id\n" + "ORDER BY book_request.id DESC;";
    public static final String FindBookById = "SELECT id FROM book WHERE id = 7;";

    List<BookRequestModel> findByApprovalStatus(ApprovalStatus Accepted);

    @Query(value = FindAllBookRequests, nativeQuery = true)
    public List<AllBookRequest> findAllBookRequests();

    @Query(value = FindBookById, nativeQuery = true)
    public List<Book> FindBookById(long id, Set<Book> book);
}
