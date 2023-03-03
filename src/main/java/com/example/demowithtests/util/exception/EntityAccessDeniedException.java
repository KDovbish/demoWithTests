package com.example.demowithtests.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class EntityAccessDeniedException extends RuntimeException{
    public EntityAccessDeniedException() {
        super("Access denied to entity");
    }

    public EntityAccessDeniedException(String message) {
        super(message);
    }
}
