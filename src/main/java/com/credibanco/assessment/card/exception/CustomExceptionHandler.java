package com.credibanco.assessment.card.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

//@ControllerAdvice
public class CustomExceptionHandler {

    /*@ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleCustomException(CustomException ex) {
        System.out.println(ex.getMessage());
        return new ResponseEntity<>("Hello world, exception tested!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleCardNotFoundException() {
        return new Thr("01", "Tarjeta no existe");
    }

    @ExceptionHandler(InvalidValidationCodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleInvalidValidationCodeException(InvalidValidationCodeException ex) {
        return new ErrorResponse("02", "Número de validación inválido");
    }

    // agregar más manejadores de excepciones aquí para otros tipos de excepciones

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleAllOtherExceptions(Exception ex) {
        return new ErrorResponse("99", "Error interno del servidor");
    }
     */
}

