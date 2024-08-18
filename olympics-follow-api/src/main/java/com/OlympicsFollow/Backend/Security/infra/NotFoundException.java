package com.OlymFollow.Backend.Security.infra;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {}
    public NotFoundException(String message) {
        super(message);
    }
}
