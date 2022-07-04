package com.capstone.library.model;

import javax.persistence.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "is_available")
    private Boolean isAvailable;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_catalogue", joinColumns = @JoinColumn(name = "book"), inverseJoinColumns =
    @JoinColumn(name = "catalogue"))
    private Set<Catalogue> catalogue = new HashSet<>();


    @Column(name = "image", unique = false, nullable = true, length = 100000)
    private byte[] image;

    public Book() {

    }

    public Book(String title, String author, Boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }


    public Set<Catalogue> getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(Set<Catalogue> catalogue) {
        this.catalogue = catalogue;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", title='" + title + '\'' + ", author='" + author + '\'' + ", " + "isAvailable=" + isAvailable + ", catalogue=" + catalogue + ", image=" + Arrays.toString(image) + '}';
    }

    public byte[] getImage() throws IOException {
//        return ImageIO.read(new ByteArrayInputStream(image));
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
