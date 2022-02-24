package guru.springframework.msscbeerservice.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class MvcExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<String> errors = new ArrayList<>(e.getFieldErrorCount());
        e.getFieldErrors().forEach(fieldError -> {
            errors.add(fieldError.getField()+":"+fieldError.getDefaultMessage());
        });
        return errors;
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<String> handleBindException(BindException e){
        List<String> errors = new ArrayList<>(e.getFieldErrorCount());
        e.getFieldErrors().forEach(fieldError -> {
            errors.add(fieldError.getField()+":"+fieldError.getDefaultMessage());
        });
        return errors;
    }
}
