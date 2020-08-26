package com.aymeric.gamestore.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aymeric.gamestore.entity.Developer;

/**
 * Developper repository
 * @author Aymeric NEUMANN
 *
 */
public interface DeveloperRepository extends CrudRepository<Developer, UUID>, PagingAndSortingRepository<Developer, UUID>  {
    
    /**
     * Find all developpers and return it in a paged result
     * @param pageable page parameters
     * @return a page of result
     */
    Page<Developer> findAll(Pageable pageable);

    /**
     * Find all developpers with the matching title
     * @param name of the developpers to find
     * @return list of developpers
     */
    List<Developer> findByName(String name);
    
    /**
     * Find all developpers with a name containing the parameter
     * @param name name/part of name of the games to find
     * @return a list of developpers
     */
    List<Developer> findByNameContainingOrderByNameAsc(String name);
}
