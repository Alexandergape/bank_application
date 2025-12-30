package com.devsu.hackerearth.backend.client.exception;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String className, String field, String value) {
        super(className + " with " + field + " = " + value + " already exists");
    }
}
