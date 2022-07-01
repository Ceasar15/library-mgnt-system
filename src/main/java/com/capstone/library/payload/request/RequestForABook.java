package com.capstone.library.payload.request;

import javax.validation.constraints.NotBlank;

public class RequestForABook {
    @NotBlank
    private Long id;

    public RequestForABook(Long id) {
        this.id = id;
    }

    public RequestForABook() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
