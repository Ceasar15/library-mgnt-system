package com.capstone.library.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "book_request")
public class BookRequestModel {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "date_requested")
    private Date dateRequested;

    @Column(name = "date_returned")
    private Date dateReturned;

    @Column(name = "approval_status")
    private Boolean approvalStatus;



}
