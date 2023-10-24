package com.shadhin.importleads;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;


@ControllerAdvice
public class ExceptionHandel extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<ErrorMessage> handleException(NoSuchElementException e) {
        ErrorMessage error = new ErrorMessage(404, e.getMessage());
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler({IllegalStateException.class})
    public ResponseEntity<ErrorMessage> handleException(IllegalStateException e) {
        ErrorMessage error = new ErrorMessage(500, e.getMessage());
        return ResponseEntity.status(500).body(error);
    }
}
