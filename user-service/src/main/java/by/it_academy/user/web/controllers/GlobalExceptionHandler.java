package by.it_academy.user.web.controllers;

import by.it_academy.user.core.dto.error.ErrorCode;
import by.it_academy.user.core.dto.error.LocalError;
import by.it_academy.user.core.dto.error.ResponseMultiError;
import by.it_academy.user.core.dto.error.ResponseSingleError;
import by.it_academy.user.core.exception.InvalidInputServiceMultiException;
import by.it_academy.user.core.exception.InvalidInputServiceSingleException;
import by.it_academy.user.core.exception.InvalidLoginException;
import by.it_academy.user.core.exception.VerificationException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {
    }

    @ExceptionHandler
    public ResponseEntity<ResponseMultiError> handle(InvalidInputServiceMultiException e) {
        Throwable[] suppressed = e.getSuppressed();
        List<LocalError> localErrors = Arrays.stream(suppressed)
                .map(thr -> new LocalError(thr.getLocalizedMessage(), thr.getMessage()))
                .toList();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseMultiError(e.getErrorCode(), localErrors));

    }

    @ExceptionHandler
    public ResponseEntity<ResponseMultiError> handle(MethodArgumentNotValidException e) {
        List<LocalError> localErrors = e.getBindingResult().getAllErrors()
                .stream().map(error -> new LocalError(((FieldError) error).getField(), error.getDefaultMessage())).toList();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseMultiError(ErrorCode.STRUCTURED_ERROR, localErrors));

    }

    @ExceptionHandler
    public ResponseEntity<List<ResponseSingleError>> handle(ConstraintViolationException e) {
        List<ResponseSingleError> localErrors = e.getConstraintViolations().stream()
                .map(ex -> new ResponseSingleError(ErrorCode.ERROR, ex.getMessage()))
                .collect(Collectors.toList());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(localErrors);

    }
    @ExceptionHandler
    public ResponseEntity<List<ResponseSingleError>> handle(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(List.of(new ResponseSingleError(ErrorCode.ERROR, e.getMessage())));
    }

    @ExceptionHandler
    public ResponseEntity<List<ResponseSingleError>> handle(InvalidInputServiceSingleException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(List.of(new ResponseSingleError(e.getErrorCode(), e.getMessage())));
    }

    @ExceptionHandler
    public ResponseEntity<List<ResponseSingleError>> handle(VerificationException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(List.of(new ResponseSingleError(e.getErrorCode(), e.getMessage())));
    }

    @ExceptionHandler
    public ResponseEntity<List<ResponseSingleError>> handle(InvalidLoginException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(List.of(new ResponseSingleError(e.getErrorCode(), e.getMessage())));
    }

    @ExceptionHandler
    public ResponseEntity<List<ResponseSingleError>> handle(AccessDeniedException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(List.of(new ResponseSingleError(ErrorCode.ERROR, e.getMessage())));
    }

    @ExceptionHandler
    public ResponseEntity<List<ResponseSingleError>> handle(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(List.of(new ResponseSingleError(ErrorCode.ERROR, e.getMessage())));
    }

}
