package org.hospitalspring2dam.domain.exceptions;

import org.hospitalspring2dam.common.Constants;

public class DatabaseException extends RuntimeException {
    public DatabaseException() {
        super(Constants.DATABASE_ERROR);
    }
    public DatabaseException(String message) {
        super(message);
    }
}
