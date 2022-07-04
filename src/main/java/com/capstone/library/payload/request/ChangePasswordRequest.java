package com.capstone.library.payload.request;

public class ChangePasswordRequest {
    String newPassword;
    String confirmPassword;


    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "ChangePasswordRequest{" + "newPassword='" + newPassword + '\'' + ", confirmPassword='" + confirmPassword + '\'' + '}';
    }
}
