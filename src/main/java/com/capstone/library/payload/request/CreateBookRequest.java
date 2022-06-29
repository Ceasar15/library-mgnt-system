package com.capstone.library.payload.request;

import javax.validation.constraints.NotBlank;

public class CreateBookRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    private String catalogue;

    public CreateBookRequest(String title, String author, String catalogue) {
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


}
