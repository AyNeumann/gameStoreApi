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
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.cache.annotation.Cacheable;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author Aymeric NEUMANN
 * Game development company
 *
 */
@Entity
@Cacheable
public class Developer implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 7596387642430312739L;

    /** Id of the developer. */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
    
    /** Name of the company */
    private String name;
    
    /** Have developed */
    @ManyToMany (mappedBy = "developer", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JsonBackReference(value = "dev-games")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Game> games;
    
    /** Owned by */
    @ManyToOne
    private Editor owner;

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
     * @return the owner
     */
    public Editor getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(Editor owner) {
        this.owner = owner;
    }

    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }
}
