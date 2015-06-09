package br.com.imaginativo.itcm.web;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DataValidationExceptionHandler {
    @ExceptionHandler(value = { ConstraintViolationException.class })
    public ResponseEntity handleBadInput(ConstraintViolationException ex) {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
