package com.aymeric.gamestore.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aymeric.gamestore.entity.Editor;

/**
 * Editor repository
 * @author Aymeric NEUMANN
 *
 */
public interface EditorRepository extends CrudRepository<Editor, UUID>, PagingAndSortingRepository<Editor, UUID> {
    
    /**
     * Find all editors and return it in a paged result
     * @param pageable page parameters
     * @return a page of result
     */
    Page<Editor> findAll(Pageable pageable);
    
    /**
     * Find all editors with a strictly matching name
     * @param name name to find
     * @return a list of Editor with matching name
     */
    List<Editor> findAllByName(final String name);
    
    /**
     * Find all editors with a name containing the string passed as parameter
     * @param name name to find
     * @return a list of Editor with matching name
     */
    List<Editor> findByNameContainingOrderByNameAsc(final String name);
}
