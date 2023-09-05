package dev.silvestredev.todosimple.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrorResponse {
    
    private final int status;
    private final String message;
    private String stackTrace;
    private List<ValidationError> errors;

    @Getter
    @Setter
    @RequiredArgsConstructor
    private static class ValidationError {
        
        private final String field;
        private final String message;
    }

    public void addValidationError (String field, String message) {

        //se o objeto "errors" for vázio, será criado um
        if (Objects.isNull(errors)) {
            this.errors = new ArrayList<>();
        }

        //adicionando o erro
        this.errors.add(new ValidationError(field, message));
    }
}
