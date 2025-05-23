package edu.rafael.park_api.exception;

public class EntitiesNotFoundException extends RuntimeException {
    public EntitiesNotFoundException(String message) {
        super(message);
    }
}
