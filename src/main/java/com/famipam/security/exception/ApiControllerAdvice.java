package com.famipam.security.exception;

import com.famipam.security.model.BaseResponse;
import com.famipam.security.model.ValidationResponse;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler({
            ExpectedException.class
    })
    public ResponseEntity<?> handleExpectedException(Throwable e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(BaseResponse.builder()
                .status(status.value())
                .message(e.getMessage())
                .build(), status
        );
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(Throwable e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(BaseResponse.builder()
                .status(status.value())
                .message(e.getMessage())
                .build(), status
        );
    }

    @ResponseBody
    @ExceptionHandler({SignatureException.class, AccessDeniedException.class, BadCredentialsException.class, JwtException.class})
    public ResponseEntity<?> handleJwtSignatureException(Throwable e) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return new ResponseEntity<>(BaseResponse.builder()
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
        return new ResponseEntity<>(BaseResponse.builder()
                .status(status.value())
                .message(e.getMessage())
                .build(), status
        );
    }

    @Override
    @ResponseBody
    @SuppressWarnings("unchecked")
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode status,
            @NotNull WebRequest request
    ) {

        Map<String, String> errors = new LinkedHashMap<>();
        Object[] messageArguments = e.getDetailMessageArguments() != null ? e.getDetailMessageArguments() : new Object[0];
        for (Object error : messageArguments) {
            ArrayList<String> array = (ArrayList<String>) error;
            if (array.size() == 0) continue;
            for (String message : array) {
                String[] split = message.split(":");
                if (split[0] != null && split[1] != null) {
                    errors.put(split[0], (split[1]).trim().replace("'",""));
                }
            }
        }

        return new ResponseEntity<>(ValidationResponse.builder()
                .status(status.value())
                .message(e.getBody().getDetail())
                .errors(errors)
                .build(), status
        );
    }

}
