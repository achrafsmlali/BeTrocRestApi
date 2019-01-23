package com.betroc.payload;

public class EmailUpdateRequest {

    private long userId;

    private String newEmail;

    public EmailUpdateRequest() {
    }

    public EmailUpdateRequest(long userId, String newEmail) {
        this.userId = userId;
        this.newEmail = newEmail;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
