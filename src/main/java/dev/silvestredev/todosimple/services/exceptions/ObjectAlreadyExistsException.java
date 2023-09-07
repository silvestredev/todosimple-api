package dev.silvestredev.todosimple.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.persistence.EntityExistsException;

@ResponseStatus(HttpStatus.CONFLICT)
public class ObjectAlreadyExistsException extends EntityExistsException {

    public ObjectAlreadyExistsException(String message) {
        super(message);
    }
}