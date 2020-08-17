package com.aymeric.gamestore.dto;

import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Editor Data Transfert Object
 * @author Aymeric NEUMANN
 *
 */
public class EditorDTO {
    
    private UUID id;
    
    @NotNull
    @NotBlank
    private String name;
    
    @JsonBackReference(value = "editor-games")
    private Set<GameDTO> games;
    
    @JsonBackReference(value = "editor-dev")
    private Set<DevelopperDTO> studios;

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
     * @return the studios
     */
    public Set<DevelopperDTO> getStudios() {
        return studios;
    }

    /**
     * @param studios the studios to set
     */
    public void setStudios(Set<DevelopperDTO> studios) {
        this.studios = studios;
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
