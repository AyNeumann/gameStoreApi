package com.aymeric.gamestore.exception;

import org.springframework.http.HttpStatus;

/**
 * Game Store entity exception class
 * @author Aymeric NEUMANN
 *
 */
public class GamestoreEntityException extends RuntimeException {

    /** serialVersionUID */
    private static final long serialVersionUID = 176460510235582952L;
    
    private HttpStatus httpErrorNumber;
    
    public GamestoreEntityException(final String message) {
        super(message);
    }
    
    public GamestoreEntityException(final String message, final HttpStatus badRequest) {
        super(message);
        this.httpErrorNumber = badRequest;
    }
    
    /**
     * @return the httpErrorNumber
     */
    public HttpStatus getHttpErrorNumber() {
        return httpErrorNumber;
    }

    /**
     * @param httpErrorNumber the httpErrorNumber to set
     */
    public void setHttpErrorNumber(HttpStatus httpErrorNumber) {
        this.httpErrorNumber = httpErrorNumber;
    }

}
