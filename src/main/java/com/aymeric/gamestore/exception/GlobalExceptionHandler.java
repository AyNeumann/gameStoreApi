package com.aymeric.gamestore.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.aymeric.gamestore.dto.ErrorDTO;

/**
 * Handle exceptions
 * @author Aymeric NEUMANN
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    /** Minimal size of to display errors messages. */
    private static final int MINIMAL_ERRORS_MESSAGE_SIZE = 128;

    /**
     * Exception handler for GameStore Entity Exception.
     * @param ex the thrown GameStore entity exception
     * @return an exception with a message and a httpErrorNumber 
     */
    @ExceptionHandler(GamestoreEntityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GamestoreEntityException> handleAapiEntityException(final GamestoreEntityException ex) {
        GamestoreEntityException exception = new GamestoreEntityException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<GamestoreEntityException>(exception, exception.getHttpErrorNumber());
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GamestoreEntityException> handleConstraintViolationException(final ConstraintViolationException ex) {
        GamestoreEntityException exception = new GamestoreEntityException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<GamestoreEntityException>(exception, exception.getHttpErrorNumber());
    }
    
    @ExceptionHandler(GamestoreInvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleGamestoreInvalidParameterException(final GamestoreInvalidParameterException gsInvParamException) {
        BindingResult bindingResult = gsInvParamException.getResult();
        
        ErrorDTO errorDTO = new ErrorDTO();
        
        errorDTO.setMessage(gsInvParamException.getMessage());
        errorDTO.setStatus(HttpStatus.BAD_REQUEST);
        errorDTO.setTimestamp(ZonedDateTime.now(ZoneId.of("Z")));
        
        StringBuilder sb = new StringBuilder(MINIMAL_ERRORS_MESSAGE_SIZE);
        
        if(bindingResult != null) {
            sb.append(errorDTO.getMessage()).append(bindingResult.getErrorCount()).append(" erreur(s) : ");

            List<ObjectError> errors = bindingResult.getAllErrors();

            for (ObjectError error : errors) {
                sb.append('[').append(error.getCode()).append("] : ").append(error.getObjectName()).append(" => ")
                        .append(error.getDefaultMessage());
            }

            errorDTO.setMessage(sb.toString());
        }
        

        return errorDTO;
    }
}
