package com.math713.bankapi.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super(String.format("User registration failed: email '%s' is already in use.", email));
    }
}
