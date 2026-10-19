package org.hospitalspring2dam.domain.exceptions;

public class ForeignKeyException extends RuntimeException {
    public ForeignKeyException(String message) {
        super(message);
    }
}
