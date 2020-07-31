package com.aymeric.gamestore.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * @author Aymeric NEUMANN
 * Database entity for game
 *
 */
@Entity
@Data
public class Game {
    
    /** Id of the game. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /** Title of the game */
    @NotNull
    @NotBlank
    private String title;
    
    /** Release date of the game */
    @NotNull
    private Date releaseDate;
    
    /** Developed by*/
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    private Set<Developper> devs;
    
    /** Edited by*/
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    private Set<Editor> editors;
}
