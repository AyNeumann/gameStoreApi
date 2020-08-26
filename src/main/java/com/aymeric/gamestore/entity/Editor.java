package com.aymeric.gamestore.entity;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.cache.annotation.Cacheable;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author Aymeric NEUMANN
 * Game edition company
 */
@Entity
@Cacheable
public class Editor implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4262306156462288554L;


    /** Id of the editor */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
    
    
    /** Name of the company*/
    private String name;
    
    /** Have edited */
    @ManyToMany (mappedBy = "editor", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JsonBackReference(value = "editor-games")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Game> games;
    
    /** Owner of */
    @OneToMany(mappedBy="owner")
    @JsonBackReference(value = "editor-dev")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Developer> developper;

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
    public Set<Game> getGames() {
        return games;
    }

    /**
     * @param games the games to set
     */
    public void setGames(Set<Game> games) {
        this.games = games;
    }

    /**
     * @return the studios
     */
    public Set<Developer> getStudios() {
        return developper;
    }

    /**
     * @param studios the studios to set
     */
    public void setStudios(Set<Developer> studios) {
        this.developper = studios;
    }

    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }
}
