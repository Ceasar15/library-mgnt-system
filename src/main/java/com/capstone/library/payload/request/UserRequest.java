package com.capstone.library.payload.request;

import javax.validation.constraints.NotBlank;

public class UserRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String roleType;

    public UserRequest() {
    }

    public UserRequest(String username, String password, String roleType) {
        this.username = username;
        this.password = password;
        this.roleType = roleType;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}
