package com.capstone.library.payload.request;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

public class CreateBook {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    private String catalogue;

    private MultipartFile imageFile;

    public CreateBook(String title, String author, String catalogue) {
        this.title = title;
        this.author = author;
        this.catalogue = catalogue;
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

    public String getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(String catalogue) {
        this.catalogue = catalogue;
    }


    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
