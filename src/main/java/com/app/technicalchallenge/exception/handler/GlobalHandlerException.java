package com.app.technicalchallenge.exception.handler;

import com.app.technicalchallenge.exception.ProcessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(ProcessException.class)
    public ResponseEntity<ResponseException> error404(ProcessException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseException(e.getMessage()));
    }
}

record ResponseException(
        String message
){

}
