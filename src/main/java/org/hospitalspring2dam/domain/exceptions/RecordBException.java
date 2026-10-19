package org.hospitalspring2dam.domain.exceptions;


import org.hospitalspring2dam.common.Constants;

public class RecordBException extends RuntimeException {
    public RecordBException(){super(Constants.RECORD_DB_ERROR);}
    public RecordBException(String message) {
        super(message);
    }
}
