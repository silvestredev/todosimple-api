package dev.silvestredev.todosimple.exceptions;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import dev.silvestredev.todosimple.services.exceptions.ObjectAlreadyExistsException;
import dev.silvestredev.todosimple.services.exceptions.ObjectNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER") //log que informa que foi lançado dessa classe  
@RestControllerAdvice  // informa que é um controller de exceções
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    //consultando na application.properties se poderá ser printado o stacktrace
    @Value("${server.error.include-exception}")
    private boolean printStackTrace;

    //exceção que captura argumentos invalidos na request
    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid (
        MethodArgumentNotValidException methodArgumentNotValidException,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse (
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            "Validation error. Check 'errors' field for details."
        );

        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();    
        
        for (FieldError fieldError : fieldErrors) {
            errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

    //exceção de erro de integridade - ex: tentar cadastrar um User com um username ja usado
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleDataIntegrityViolationException (
        DataIntegrityViolationException dataIntegrityViolationException,
        WebRequest request) {
        
        String errorMessage = dataIntegrityViolationException.getMostSpecificCause().getMessage();
        log.error("Failed to save entity. Integrity problems: " + errorMessage, dataIntegrityViolationException);

        return buildErrorResponse(dataIntegrityViolationException, errorMessage, HttpStatus.CONFLICT, request);
    }

    //exceção de erro de restrição
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleConstraintViolationException (
        ConstraintViolationException constraintViolationException,
        WebRequest request) {
    
        final String errorMessage = "Constraints error ocurred";
        log.error("Failed to validated element: " + errorMessage, constraintViolationException);
        
        return buildErrorResponse(constraintViolationException, errorMessage, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    //lança uma exceção não tratada - exceção "desconhecida"
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtExeption (
        Exception exception,
        WebRequest request) {

        final String errorMessage = "Unknown error ocurred";
        log.error(errorMessage, exception);

        return buildErrorResponse(exception, errorMessage, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    //lança a exceção de objeto nao encontrado
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleObjectNotFoundException (
        ObjectNotFoundException objectNotFoundException,
        WebRequest request) {

        final String errorMessage = "Failed to find the request element";

        log.error(errorMessage, objectNotFoundException);
        return buildErrorResponse(objectNotFoundException, errorMessage, HttpStatus.NOT_FOUND, request);
    }

    //lança a exceção de usuário já existente
    @ExceptionHandler(ObjectAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleObjectAlreadyExistsException(
        ObjectAlreadyExistsException objectAlreadyExistsException,
        WebRequest request) {
    
        final String errorMessage = "This username is already in use.";

        log.error(errorMessage, objectAlreadyExistsException);
        return buildErrorResponse(objectAlreadyExistsException, errorMessage, HttpStatus.CONFLICT, request);
    }

    //buildando o ErrorResponse
    private ResponseEntity<Object> buildErrorResponse(
        Exception exception,
        String message,
        HttpStatus httpStatus,
        WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);    
        
        if(this.printStackTrace) {
            errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
        }
    
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
