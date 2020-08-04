package com.aymeric.gamestore.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author Aymeric NEUMANN
 * Game edition company
 */
@Entity
public class Editor {

    /** Id of the editor */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
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
    public Long getId() {
        return id;
    }
}
