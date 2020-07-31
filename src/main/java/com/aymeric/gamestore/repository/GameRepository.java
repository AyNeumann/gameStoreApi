package com.aymeric.gamestore.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.aymeric.gamestore.entity.Game;

/**
 * Game repository
 * @author Aymeric NEUMANN
 *
 */
public interface GameRepository extends CrudRepository<Game, Long> {

    /**
     * Find all games with the matching title
     * @param Title
     * @return list of games
     */
    List<Game> findByTitle(String Title);
}
