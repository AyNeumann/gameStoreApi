package com.aymeric.gamestore.repository;

import org.springframework.data.repository.CrudRepository;

import com.aymeric.gamestore.entity.Editor;

/**
 * Editor repository
 * @author Aymeric NEUMANN
 *
 */
public interface EditorRepository extends CrudRepository<Editor, Long> {

}
