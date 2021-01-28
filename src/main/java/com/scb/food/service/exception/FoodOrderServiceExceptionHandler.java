package com.scb.food.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@ControllerAdvice
public class FoodOrderServiceExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        AtomicReference<String> errorMsg= new AtomicReference<>();
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorMsg.set(errorMessage);
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errorMsg.get(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionFailureException.class)
    public ResponseEntity<Object> transactionFailureCustomException(TransactionFailureException ex) {
        return new ResponseEntity<>("Fund Transfer has been failed", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>("User not available", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<Object> outOfStockException(OutOfStockException ex) {
        return new ResponseEntity<>("Requested Food is out of stock", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidAccountNumberException.class)
    public ResponseEntity<Object> invalidAccountNumberException(InvalidAccountNumberException ex) {
        return new ResponseEntity<>("Account number is invalid", HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HistoryNotFoundException.class)
    public ResponseEntity<Object> invalidAccountNumberException(HistoryNotFoundException ex) {
        return new ResponseEntity<>("User has no order history", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidFoodNameException.class)
    public ResponseEntity<Object> invalidFoodNameException(InvalidFoodNameException ex) {
        return new ResponseEntity<>("Sorry, Requested food not available", HttpStatus.BAD_REQUEST);
    }



}
