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

import lombok.Data;

/**
 * @author Aymeric NEUMANN
 * Game edition company
 */
@Entity
@Data
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
    @ManyToMany (mappedBy = "editors", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Game> games;
    
    /** Owner of */
    @OneToMany(mappedBy="owner")
    private Set<Developper> studios;
}
