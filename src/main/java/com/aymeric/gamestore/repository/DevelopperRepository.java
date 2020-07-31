package com.aymeric.gamestore.repository;

import org.springframework.data.repository.CrudRepository;

import com.aymeric.gamestore.entity.Developper;

/**
 * Developper repository
 * @author Aymeric NEUMANN
 *
 */
public interface DevelopperRepository extends CrudRepository<Developper, Long> {

}
