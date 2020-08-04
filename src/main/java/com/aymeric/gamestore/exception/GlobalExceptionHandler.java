package com.aymeric.gamestore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Handle exceptions
 * @author Aymeric NEUMANN
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception handler for Aapi Entity Exception.
     * @param ex the thrown Aapi entity exception
     * @return an exception with a message and a httpErrorNumber 
     */
    @ExceptionHandler(GamestoreEntityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GamestoreEntityException> handleAapiEntityException(final GamestoreEntityException ex) {
        GamestoreEntityException exception = new GamestoreEntityException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<GamestoreEntityException>(exception, exception.getHttpErrorNumber());
    }
}
