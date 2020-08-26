package com.aymeric.gamestore.dto;

import java.util.UUID;

/**
 * Games Developers Editor relationship Data Transfer Object.
 * Why this DTO: @see: https://dzone.com/articles/rest-api-path-vs-request-body-parameters
 * @author Aymeric NEUMANN
 *
 */
public class GameDevEditorRelationshipDTO {
    
    private UUID gameId;
    
    private UUID devId;
    
    private UUID editorId;
    
    /**
     * @return the gameId
     */
    public UUID getGameId() {
        return gameId;
    }

    /**
     * @param gameId the gameId to set
     */
    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    /**
     * @return the devId
     */
    public UUID getDevId() {
        return devId;
    }

    /**
     * @param devId the devId to set
     */
    public void setDevId(UUID devId) {
        this.devId = devId;
    }

    /**
     * @return the editorId
     */
    public UUID getEditorId() {
        return editorId;
    }

    /**
     * @param editorId the editorId to set
     */
    public void setEditorId(UUID editorId) {
        this.editorId = editorId;
    }
}
