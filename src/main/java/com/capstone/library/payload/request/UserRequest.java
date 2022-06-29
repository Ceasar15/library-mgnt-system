package com.capstone.library.payload.request;

import javax.validation.constraints.NotBlank;

public class UserRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String roleType;

    @NotBlank
    private String email;

    public UserRequest() {
    }

    public UserRequest(String username, String roleType) {
        this.username = username;
        this.roleType = roleType;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    @Override
    public String toString() {
        return "UserRequest{" + "username='" + username + '\'' + "," + " roleType='" + roleType + '\'' + '}';
    }


}
