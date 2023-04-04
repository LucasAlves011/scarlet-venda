package com.scarlet.venda.controller.exceptions;

import jakarta.servlet.ServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;

@ControllerAdvice
public class ResourceExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<StandardException> objectNotFoundException(RuntimeException e, ServletRequest request){
        StandardException error = new StandardException(LocalDateTime.now(ZoneId.of("UTC")), HttpStatus.INTERNAL_SERVER_ERROR.value()
                , e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
