package com.example.demowithtests.util.exception;

public class EntityAccessDeniedException extends RuntimeException{
    public EntityAccessDeniedException() {
        super("Access denied to entity");
    }

    public EntityAccessDeniedException(String message) {
        super(message);
    }
}
