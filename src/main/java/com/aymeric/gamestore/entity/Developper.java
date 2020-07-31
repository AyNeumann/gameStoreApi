package com.aymeric.gamestore.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author Aymeric NEUMANN
 * Game developpement company
 *
 */
@Entity
@Data
public class Developper {
    
    /** Id of the developper. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /** Name of the company */
    @NotNull
    @NotBlank
    private String name;
    
    /** Have developed */
    @ManyToMany (mappedBy = "devs", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Game> games;
    
    /** Owned by */
    @ManyToOne
    private Editor owner;
    
    
}
