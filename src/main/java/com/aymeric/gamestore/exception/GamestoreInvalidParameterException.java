package com.aymeric.gamestore.exception;

import org.springframework.validation.BindingResult;

/**
 * Custom Exception when some client provided parameters are invalids.
 * @author Aymeric NEUMANN
 *
 */
public class GamestoreInvalidParameterException extends RuntimeException {

    /** serialVersionUID */
    private static final long serialVersionUID = 3976019798654171255L;
    
    /** Optional : spring validation interface result when available */
    private BindingResult result;
    
    /**
     * Create exception with default message to display to user.
     * @param bindingResult The Spring interface BindingResult result
     */
    public GamestoreInvalidParameterException(final BindingResult bindingresult) {
        super();
        this.result = bindingresult;
    }
    
    /**
     * Create exception with default message to display to user.
     * @param message Message to display to user
     * @param bindingResult The Spring interface BindingResult result
     */
    public GamestoreInvalidParameterException(final String message, final BindingResult bindingResult) {
        super(message);
        this.result = bindingResult;
    }
    
    /**
     * Create exception with default message to display to user.
     * @param message Message to display to user
     */
    public GamestoreInvalidParameterException(final String message) {
        super(message);
        this.result = null;
    }
    
    /**
     * @return the result
     */
    public BindingResult getResult() {
        return result;
    }

}
