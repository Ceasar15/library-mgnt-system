package com.capstone.library.repository;

import com.capstone.library.model.ApprovalStatus;
import com.capstone.library.model.BookRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRequestRepository extends JpaRepository<BookRequestModel, Long> {
    List<BookRequestModel> findByApprovalStatus(ApprovalStatus Accepted);

}
