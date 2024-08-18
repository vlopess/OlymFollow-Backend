package com.OlymFollow.Backend.Security.infra.ExceptionHandler;

import com.OlymFollow.Backend.Security.infra.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({EntityNotFoundException.class, NotFoundException.class})
    public ResponseEntity handleEntityNotFoundException(RuntimeException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException500(Exception e) {
        return ResponseEntity.internalServerError().body(e.getLocalizedMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity handleErrorBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleExceptionFollower(Exception ex) {
        if (ex.getCause() != null && ex.getCause().getMessage().contains("constraint")) {
            var field = ex.getCause() != null && ex.getCause().getMessage().contains("Key (email)") ? "E-mail já está em uso por outra conta!" : ex.getCause() != null && ex.getCause().getMessage().contains("Key (username)") ? "Username já está em uso por outra conta!" : "Chave duplicada encontrada";
            return new ResponseEntity<>(field, HttpStatus.CONFLICT);
        }
        return ResponseEntity.badRequest().body("You are already subscribed!");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleError400(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(DataErrorValidation::new).toList());
    }


    private record DataErrorValidation(String key, String message) {
        public DataErrorValidation(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
