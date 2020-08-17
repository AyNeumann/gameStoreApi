package com.aymeric.gamestore.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author Aymeric NEUMANN
 * Database entity for game
 *
 */
@Entity
@Cacheable
public class Game implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 2193644206718434012L;

    /** Id of the game. */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
    
    /** Title of the game */
    private String title;
    
    /** Release date of the game */
    private Date releaseDate;
    
    /** Developed by*/
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Developper> developper;
    
    /** Edited by*/
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Editor> editor;
        
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
        return developper;
    }

    /**
     * @param devs the devs to set
     */
    public void setDevs(Set<Developper> devs) {
        this.developper = devs;
    }

    /**
     * @return the editors
     */
    public Set<Editor> getEditors() {
        return editor;
    }

    /**
     * @param editors the editors to set
     */
    public void setEditors(Set<Editor> editors) {
        this.editor = editors;
    }

    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }
}
