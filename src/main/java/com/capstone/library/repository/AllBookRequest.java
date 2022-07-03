package com.capstone.library.repository;

import com.capstone.library.model.ApprovalStatus;

import java.time.LocalDateTime;

public interface AllBookRequest {
    String getId();

    String getDate_requested();

    LocalDateTime getDate_returned();

    ApprovalStatus getApproval_status();

    String getBookId();

    String getTitle();

    String getAuthor();

    String getUserId();

    String getEmail();

    String getUsername();

}
