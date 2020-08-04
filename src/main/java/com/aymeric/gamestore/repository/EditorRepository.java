package com.aymeric.gamestore.repository;

import java.util.List;

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
public interface EditorRepository extends CrudRepository<Editor, Long>, PagingAndSortingRepository<Editor, Long> {
    
    /**
     * Find all editors and return it in a paged result
     * @param pageable page parameters
     * @return a page of result
     */
    Page<Editor> findAll(Pageable pageable);
    
    List<Editor> findAllByName(final String name);
}
