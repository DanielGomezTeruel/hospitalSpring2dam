package org.hospitalspring2dam.ui.error;


import org.hospitalspring2dam.domain.exceptions.DuplicatedUsernameException;
import org.hospitalspring2dam.domain.exceptions.ForeignKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DuplicatedUsernameException.class)
    public ResponseEntity<String> handleForeignKetException(DuplicatedUsernameException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    @ExceptionHandler(ForeignKeyException.class)
    public ResponseEntity<String> handleForeignKetException(ForeignKeyException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
