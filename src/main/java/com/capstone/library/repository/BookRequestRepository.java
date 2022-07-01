package com.capstone.library.repository;

import com.capstone.library.model.ApprovalStatus;
import com.capstone.library.model.BookRequestModel;
import com.capstone.library.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface BookRequestRepository extends JpaRepository<BookRequestModel, Long> {
    List<BookRequestModel> findByApprovalStatus(ApprovalStatus Accepted);

    List<BookRequestModel> findByUser(Set<UserModel> user);
}
