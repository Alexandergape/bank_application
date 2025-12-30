package com.devsu.hackerearth.backend.account.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String className, Long id) {
        super(className + " with id " + id + " not found");
    }
}
