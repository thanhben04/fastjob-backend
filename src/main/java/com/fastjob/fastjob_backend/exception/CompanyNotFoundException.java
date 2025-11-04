package com.fastjob.fastjob_backend.exception;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException() {
        super("Company not found");
    }

    public CompanyNotFoundException(String message) {
        super(message);
    }
}

