package org.hospitalspring2dam.domain.exceptions;

public class DuplicatedUsernameException extends RuntimeException {
    public DuplicatedUsernameException(){super();}
    public DuplicatedUsernameException(String message) {
        super(message);
    }
}
