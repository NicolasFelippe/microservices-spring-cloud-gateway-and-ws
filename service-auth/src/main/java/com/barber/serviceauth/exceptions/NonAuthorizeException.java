package com.barber.serviceauth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Usuário não autenticado!")
public class NonAuthorizeException extends Exception {
    public NonAuthorizeException(String message) {
        super(message);
    }

    public NonAuthorizeException() {
        super();
    }
}
