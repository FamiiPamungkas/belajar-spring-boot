package com.famipam.security.exception;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler({
            JwtException.class,
            ExpectedException.class
    })
    public ResponseEntity<?> handleExpectedException(Throwable e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(ErrorBody.builder()
                .status(status.value())
                .message(e.getMessage())
                .build(), status
        );
    }

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
    @ExceptionHandler({SignatureException.class, AccessDeniedException.class})
    public ResponseEntity<?> handleJwtSignatureException(Throwable e) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return new ResponseEntity<>(ErrorBody.builder()
                .status(status.value())
                .message(e.getMessage())
                .build(), status
        );
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Throwable e) {
        e.printStackTrace();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(ErrorBody.builder()
                .status(status.value())
                .message(e.getMessage())
                .build(), status
        );
    }

    @Override
    @ResponseBody
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode status,
            @NotNull WebRequest request
    ) {

        return new ResponseEntity<>(ValidationErrorBody.builder()
                .status(status.value())
                .message(e.getBody().getDetail())
                .errors(e.getDetailMessageArguments())
                .build(), status
        );
    }

}
