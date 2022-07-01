package com.capstone.library.repository;

import com.capstone.library.model.ApprovalStatus;
import com.capstone.library.model.BookRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRequestRepository extends JpaRepository<BookRequestModel, Long> {
    public static final String FindAllBookRequests = "SELECT book_request.id, " + "book_request" +
            ".approval_status, " + "book_request.date_returned, " + "book_request" + ".date_requested "
            + " FROM " + "book_request LEFT JOIN book_request_book ON " + "book_request_book" +
            ".book_request = book_request.id ORDER BY book_request.id";

    List<BookRequestModel> findByApprovalStatus(ApprovalStatus Accepted);

    @Query(value = FindAllBookRequests, nativeQuery = true)
    public List<AllBookRequest> findAllBookRequests();

}
