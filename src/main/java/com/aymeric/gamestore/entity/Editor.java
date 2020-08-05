package com.aymeric.gamestore.entity;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author Aymeric NEUMANN
 * Game edition company
 */
@Entity
public class Editor {

    /** Id of the editor */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
    
    
    /** Name of the company*/
    @NotNull
    @NotBlank
    private String name;
    
    /** Have edited */
    @ManyToMany (mappedBy = "editors", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JsonBackReference(value = "editor-games")
    private Set<Game> games;
    
    /** Owner of */
    @OneToMany(mappedBy="owner")
    @JsonBackReference(value = "editor-dev")
    private Set<Developper> studios;

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
    public Set<Developper> getStudios() {
        return studios;
    }

    /**
     * @param studios the studios to set
     */
    public void setStudios(Set<Developper> studios) {
        this.studios = studios;
    }

    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }
}
