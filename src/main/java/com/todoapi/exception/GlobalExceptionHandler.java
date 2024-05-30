package com.todoapi.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                        WebRequest webRequest) {

                ErrorDetails errorDetails = new ErrorDetails(
                                LocalDateTime.now(),
                                exception.getMessage(),
                                webRequest.getDescription(false),
                                "NOT_FOUND");

                return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(EmailAlreadyExistsException.class)
        public ResponseEntity<ErrorDetails> handleEmailAlreadyExistsException(EmailAlreadyExistsException exception,
                        WebRequest webRequest) {

                ErrorDetails errorDetails = new ErrorDetails(
                                LocalDateTime.now(),
                                exception.getMessage(),
                                webRequest.getDescription(false),
                                "EMAIL_ALREADY_EXISTS");

                return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                        WebRequest webRequest) {

                ErrorDetails errorDetails = new ErrorDetails(
                                LocalDateTime.now(),
                                exception.getMessage(),
                                webRequest.getDescription(false),
                                "INTERNAL SERVER ERROR");

                return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                        @NonNull HttpHeaders headers,
                        HttpStatusCode status,
                        WebRequest request) {
                Map<String, String> errors = new HashMap<>();
                List<ObjectError> errorList = ex.getBindingResult().getAllErrors();

                errorList.forEach((error) -> {
                        String fieldName = ((FieldError) error).getField();
                        String message = error.getDefaultMessage();
                        errors.put(fieldName, message);
                });

                ValidationErrorDetails errorDetails = new ValidationErrorDetails(
                                LocalDateTime.now(),
                                "Validation Failed",
                                request.getDescription(false),
                                "VALIDATION_ERROR",
                                errors);

                return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }
}
