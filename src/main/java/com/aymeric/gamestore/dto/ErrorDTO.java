package com.aymeric.gamestore.dto;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

/**
 * Data Transfer Object for basic CRUD infos display.
 * Containing:
 * an error message
 * @author Aymeric NEUMANN
 *
 */
public class ErrorDTO {
    
    /** The Error message.*/
    private String message;
    
    /** The HTTP status. */
    private HttpStatus status;
    
    private ZonedDateTime timestamp;
    
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param newMessage the message to set
     */
    public void setMessage(final String newMessage) {
        this.message = newMessage;
    }
    
    /**
     * @return the status
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }
    
    /**
     * @return the timestamp
     */
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
