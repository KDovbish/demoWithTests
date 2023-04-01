package com.example.demowithtests.util.exception;

public class ResourceRemoveNotAllowedException extends RuntimeException {
    public ResourceRemoveNotAllowedException() {
        super("Resource remove is not allowed");
    }
    public ResourceRemoveNotAllowedException(String message) {
        super(message);
    }
}
