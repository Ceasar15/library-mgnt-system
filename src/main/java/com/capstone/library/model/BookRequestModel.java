package com.capstone.library.model;


import javax.persistence.*;
import java.time.LocalDateTime;
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
    private LocalDateTime dateRequested;

    @Column(name = "date_returned")
    private LocalDateTime dateReturned;

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


    public BookRequestModel(LocalDateTime dateRequested, LocalDateTime dateReturned,
                            ApprovalStatus approvalStatus, Set<UserModel> user, Set<Book> book) {
        this.dateRequested = dateRequested;
        this.dateReturned = dateReturned;
        this.approvalStatus = approvalStatus;
        this.book = book;

    }

    public BookRequestModel() {
    }

    public BookRequestModel(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(LocalDateTime dateRequested) {
        this.dateRequested = dateRequested;
    }

    public LocalDateTime getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(LocalDateTime dateReturned) {
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

    @Override
    public String toString() {
        return "BookRequestModel{" + "id=" + id + ", dateRequested=" + dateRequested + ", dateReturned"
                + "=" + dateReturned + ", approvalStatus=" + approvalStatus + ", user=" + user + ", " + "book=" + book + '}';
    }
}
