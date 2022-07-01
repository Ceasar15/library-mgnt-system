package com.capstone.library.payload.request;

import javax.validation.constraints.NotBlank;

public class RequestForABook {
    @NotBlank
    private String id;

    public RequestForABook(String id) {
        this.id = id;
    }

    public RequestForABook() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
