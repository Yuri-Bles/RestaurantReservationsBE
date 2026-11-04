package nl.fontys.restaurantreservations.bll.exceptions;

import nl.fontys.restaurantreservations.api.dtos.ApiMessage;
import nl.fontys.restaurantreservations.bll.services.ResponseEntityCreator;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        return ResponseEntityCreator.returnResponseEntity(
                500,
                "Something went wrong internally, please try again later",
                ex.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(
            MethodArgumentNotValidException exception)
    {
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return ResponseEntityCreator.returnResponseEntity(400, "Validation failed.", errors);
    }

    @ExceptionHandler(TableNotFoundException.class)
    public ResponseEntity<?> handleNotFound(TableNotFoundException ex) {
        return ResponseEntityCreator.returnResponseEntity(404, ex.getMessage());
    }
}
