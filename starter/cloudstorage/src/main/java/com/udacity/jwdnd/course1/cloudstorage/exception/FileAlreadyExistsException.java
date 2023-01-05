package com.udacity.jwdnd.course1.cloudstorage.exception;

public class FileAlreadyExistsException extends Exception {
    public FileAlreadyExistsException() {
        super();
    }

    public FileAlreadyExistsException(String message) {
        super(message);
    }
}
