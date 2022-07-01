package com.capstone.library.model;


import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    private ApprovalStatus approvalStatus;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "bookRequest_user", joinColumns = @JoinColumn(name = "book_request"),
            inverseJoinColumns = @JoinColumn(name = "users"))
    private Set<UserModel> user = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "bookRequest_book", joinColumns = @JoinColumn(name = "book_request"),
            inverseJoinColumns = @JoinColumn(name = "book"))
    private Set<Book> book = new HashSet<>();


    public BookRequestModel(Date dateRequested, Date dateReturned, ApprovalStatus approvalStatus,
                            Set<UserModel> user, Set<Book> book) {
        this.dateRequested = dateRequested;
        this.dateReturned = dateReturned;
        this.approvalStatus = approvalStatus;
        this.book = book;

    }

    public BookRequestModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(Date dateRequested) {
        this.dateRequested = dateRequested;
    }

    public Date getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }


    public Set<UserModel> getUser() {
        return user;
    }

    public void setUser(Set<UserModel> user) {
        this.user = user;
    }

    public Set<Book> getBook() {
        return book;
    }

    public void setBook(Set<Book> book) {
        this.book = book;
    }
}
