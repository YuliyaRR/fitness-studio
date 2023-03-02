package by.it_academy.fitnessstudio.web.controllers;

import by.it_academy.fitnessstudio.core.dto.error.LocalError;
import by.it_academy.fitnessstudio.core.dto.error.ResponseMultiError;
import by.it_academy.fitnessstudio.core.dto.error.ResponseSingleError;
import by.it_academy.fitnessstudio.core.exception.InvalidInputServiceMultiException;
import by.it_academy.fitnessstudio.core.exception.InvalidInputServiceSingleException;
import by.it_academy.fitnessstudio.core.exception.NotFoundDataBaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {
    }

    @ExceptionHandler(InvalidInputServiceMultiException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMultiError handle(InvalidInputServiceMultiException e) {
        Throwable[] suppressed = e.getSuppressed();
        List<LocalError> localErrors = Arrays.stream(suppressed)
                .map(thr -> new LocalError(thr.getLocalizedMessage(), thr.getMessage()))
                .collect(Collectors.toList());
        return new ResponseMultiError(localErrors);
    }

//TODO: единичные ошибки тоже должны возвращаться как массив/лист, а не отдельный объект
    @ExceptionHandler(InvalidInputServiceSingleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseSingleError handle(InvalidInputServiceSingleException e){
        return new ResponseSingleError("Invalid input", e.getMessage());
    }


    @ExceptionHandler(NotFoundDataBaseException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseSingleError handle(NotFoundDataBaseException e){
        return new ResponseSingleError("error", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseSingleError handle(Exception e) {
        return new ResponseSingleError("error", e.getMessage());
    }

}
