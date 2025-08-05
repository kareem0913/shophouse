package com.shophouse.error;

import com.shophouse.error.exception.DuplicateResourceException;
import com.shophouse.error.exception.ResourceNotFoundException;
import com.shophouse.error.model.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static com.shophouse.util.Util.currentTimestamp;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse handleNotFoundException(ResourceNotFoundException e) {
        return new ErrorResponse(e.getCode(), e.getMessage(), e.getDescription(), currentTimestamp());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ErrorResponse handleDuplicateResourceException(DuplicateResourceException e) {
        return new ErrorResponse(e.getCode(), e.getMessage(), e.getDescription(), currentTimestamp());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        ErrorResponse response = new ErrorResponse(
                400,
                "Validation failed",
                errors,
                currentTimestamp()
        );
        return response;
    }

//    @ExceptionHandler(FileStorageException.class)
//    public ErrorResponse handleFileStorageException(FileStorageException e) {
//        return new ErrorResponse(e.getCode(), e.getMessage(), e.getDescription(), currentTimestamp());
//    }
}
