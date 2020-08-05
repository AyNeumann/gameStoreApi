package com.aymeric.gamestore.entity;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * @author Aymeric NEUMANN
 * Database entity for game
 *
 */
@Entity
public class Game {
    
    /** Id of the game. */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
    
    /** Title of the game */
    @NotNull
    @NotBlank
    private String title;
    
    /** Release date of the game */
    @NotNull
    private Date releaseDate;
    
    /** Developed by*/
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    private Set<Developper> devs;
    
    /** Edited by*/
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    private Set<Editor> editors;
        
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
    public Set<Developper> getDevs() {
        return devs;
    }

    /**
     * @param devs the devs to set
     */
    public void setDevs(Set<Developper> devs) {
        this.devs = devs;
    }

    /**
     * @return the editors
     */
    public Set<Editor> getEditors() {
        return editors;
    }

    /**
     * @param editors the editors to set
     */
    public void setEditors(Set<Editor> editors) {
        this.editors = editors;
    }

    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }
}
