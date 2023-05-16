package com.famipam.security.exception;

import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(Throwable e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(ErrorBody.builder()
                .status(status.value())
                .message(e.getMessage())
                .build(), status
        );
    }

    @ResponseBody
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<?> handleJwtSignatureException(Throwable e) {
        System.out.println("-> handle JWT Signature Exception");
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return new ResponseEntity<>(ErrorBody.builder()
                .status(status.value())
                .message(e.getMessage())
                .build(), status
        );
    }

}
