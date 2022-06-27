package com.capstone.library.payload.request;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class BookRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    private Set<String> catalogue;

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

    public Set<String> getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(Set<String> catalogue) {
        this.catalogue = catalogue;
    }


}
