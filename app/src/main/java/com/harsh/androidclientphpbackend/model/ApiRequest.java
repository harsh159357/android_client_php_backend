package com.harsh.androidclientphpbackend.model;


public class ApiRequest {

    private String operation;
    private User user;

    public ApiRequest() {
    }

    public ApiRequest(String operation, User user) {
        this.operation = operation;
        this.user = user;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
