package com.prashanth.sunvalley.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler{

    @ExceptionHandler(NegativeFeeException.class)
    public ResponseEntity<Object> handleNegativeFeeException(Exception exception){
        return new ResponseEntity<>("Payment Not Valid. " + exception.getMessage(),
                new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(Exception exception){
        return new ResponseEntity<>(exception.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

}
