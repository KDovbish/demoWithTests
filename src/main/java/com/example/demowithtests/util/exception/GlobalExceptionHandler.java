package com.example.demowithtests.util.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
     /*   ErrorDetails errorDetails =
                new ErrorDetails(new Date(),
                        "Employee not found with id =" + request.getDescription(true),//getParameter("id"),
                        request.getDescription(false));*/
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceWasDeletedException.class)
    protected ResponseEntity<MyGlobalExceptionHandler> handleDeleteException() {
        return new ResponseEntity<>(new MyGlobalExceptionHandler("This user was deleted"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //  Обработчик исключения, генерируемого при отсутствии сущности в базе
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDetails> entityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails( Date.from(Instant.now()), ex.getMessage(), request.getDescription(false) ), HttpStatus.NOT_FOUND);
    }

    //  Обработчик исключения генерируемого при отсутствии доступа пользователя к сущности
    @ExceptionHandler(EntityAccessDeniedException.class)
    public ResponseEntity<ErrorDetails> entityAccessDeniedException(EntityAccessDeniedException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails( Date.from(Instant.now()), ex.getMessage(), request.getDescription(false) ), HttpStatus.FORBIDDEN);
    }

    //  Обработчик исключения, которое генерируется, в случае если не проходит валидация параметра метода контроллера,
    //  помеченного аннотацией @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> methodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails( Date.from(Instant.now()), ex.getMessage(), request.getDescription(false) ), HttpStatus.BAD_REQUEST );
    }


    @Data
    @AllArgsConstructor
    private static class MyGlobalExceptionHandler {
        private String message;
    }
}
