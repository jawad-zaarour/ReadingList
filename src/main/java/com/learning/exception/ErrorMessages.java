package com.learning.exception;

public enum ErrorMessages  {
    NO_RECORD_FOUND("Record with provided id not found");

    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage=errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
