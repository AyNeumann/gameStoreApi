package com.aymeric.gamestore.dto;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Games Data Transfer Object
 * @author Aymeric NEUMANN
 *
 */
public class GameDTO {
    
    private UUID id;
    
    @NotNull
    @NotBlank
    private String title;
    
    @NotNull
    private Date releaseDate;
    
    private Set<DeveloperDTO> devs;
    
    private Set<EditorDTO> editors;
    
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the releaseDate
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * @param releaseDate the releaseDate to set
     */
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * @return the devs
     */
    public Set<DeveloperDTO> getDevs() {
        return devs;
    }

    /**
     * @param devs the devs to set
     */
    public void setDevs(Set<DeveloperDTO> devs) {
        this.devs = devs;
    }

    /**
     * @return the editors
     */
    public Set<EditorDTO> getEditors() {
        return editors;
    }

    /**
     * @param editors the editors to set
     */
    public void setEditors(Set<EditorDTO> editors) {
        this.editors = editors;
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
