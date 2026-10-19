package org.hospitalspring2dam.domain.exceptions;

import org.hospitalspring2dam.common.Constants;

public class CredentialsDBException extends DatabaseException {
    public CredentialsDBException() {
        super(Constants.CREDENTIAL_DB_ERROR);
    }
    public CredentialsDBException(String message) {super(message);}
}
