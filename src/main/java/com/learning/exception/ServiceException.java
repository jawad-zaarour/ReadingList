package com.learning.exception;

public class ReadingListServiceException extends RuntimeException {
    public ReadingListServiceException(String message) {
        super(message);
    }
}
