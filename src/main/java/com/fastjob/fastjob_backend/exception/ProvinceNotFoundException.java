package com.fastjob.fastjob_backend.exception;

public class ProvinceNotFoundException extends RuntimeException {
    public ProvinceNotFoundException() {
        super("Province not found");
    }

    public ProvinceNotFoundException(String message) {
        super(message);
    }
}

