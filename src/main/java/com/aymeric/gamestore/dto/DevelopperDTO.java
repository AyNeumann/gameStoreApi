package com.aymeric.gamestore.dto;

import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Developper Data Tranfert Object
 * @author Aymeric NEUMANN
 *
 */
public class DevelopperDTO {
    
    private UUID id;
    
    @NotNull
    @NotBlank
    private String name;
    
    @JsonBackReference(value = "dev-games")
    private Set<GameDTO> games;
    
    private EditorDTO owner;
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the games
     */
    public Set<GameDTO> getGames() {
        return games;
    }

    /**
     * @param games the games to set
     */
    public void setGames(Set<GameDTO> games) {
        this.games = games;
    }

    /**
     * @return the owner
     */
    public EditorDTO getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(EditorDTO owner) {
        this.owner = owner;
    }

    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(UUID id) {
        this.id = id;
    }
}
