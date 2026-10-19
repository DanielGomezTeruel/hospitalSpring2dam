package org.hospitalspring2dam.domain.exceptions;

public class AppError extends RuntimeException {
    public AppError(String message) {
        super(message);
    }
}
