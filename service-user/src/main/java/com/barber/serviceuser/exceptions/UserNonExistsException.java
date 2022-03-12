package com.barber.serviceuser.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Usuário não existe!")
public class UserNonExistsException extends Exception {
    public UserNonExistsException(String message) {
        super(message);
    }

    public UserNonExistsException() {
    }
}
