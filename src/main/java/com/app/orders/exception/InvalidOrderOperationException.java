package com.app.orders.exception;

public class InvalidOrderOperationException extends RuntimeException {
    public InvalidOrderOperationException(String message) {
        super(message);
    }
}
