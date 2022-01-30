package com.barber.servicewebsocket.models;

public class Greeting {

    public Greeting(String message) {
        this.message = message;
    }

    public Greeting() {
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
