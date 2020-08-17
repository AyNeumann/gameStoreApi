package com.aymeric.gamestore.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aymeric.gamestore.entity.Game;

/**
 * Game repository
 * @author Aymeric NEUMANN
 *
 */
@CacheConfig(cacheNames={"games"})
public interface GameRepository extends CrudRepository<Game, UUID>, PagingAndSortingRepository<Game, UUID> {

    /**
     * Find all games and return it in a paged result
     * @param pageable page parameters
     * @return a page of result
     */
    Page<Game> findAll(Pageable pageable);

    /**
     * Find all games with the matching title
     * @param Title title of the game to find
     * @return a list of games
     */
    List<Game> findByTitle(String title);
    
    /**
     * Find all games with a title containing the parameter
     * @param title title/part of title of the games to find
     * @return a list of games
     */
    List<Game> findByTitleContainingOrderByTitleAsc(String title);
}
