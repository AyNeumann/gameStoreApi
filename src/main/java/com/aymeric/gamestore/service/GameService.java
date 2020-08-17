package com.aymeric.gamestore.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.aymeric.gamestore.entity.Developper;
import com.aymeric.gamestore.entity.Editor;
import com.aymeric.gamestore.entity.Game;
import com.aymeric.gamestore.exception.GamestoreEntityException;
import com.aymeric.gamestore.repository.GameRepository;

/**
 * Service for games
 * @author Aymeric NEUMANN
 *
 */
@Service
public class GameService {
    
    /** Logback logger reference. */
    private static final Logger logger = LoggerFactory.getLogger(GameService.class);
    
    /** Number of user return per page. */
    private static final int NUM_OF_USER_PER_PAGE = 50;
    
    @Autowired
    private GameRepository gameRepository;
    
    /**
     * Get all games by page of 50 result each in title alphabetical order
     * @param pageNumber number of the required page
     * @return required page
     */
    @Cacheable(cacheNames = "gameCache")
    public Page<Game> getAllGames(final Integer pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, NUM_OF_USER_PER_PAGE, Sort.by("title"));
        
        Page<Game> games = gameRepository.findAll(pageable);
        
        if(games.isEmpty()) {
            logger.warn("No games found on the page number {}", pageNumber);
        }
        
        return games;
    }
    
    /**
     * Get all games with a matching title
     * @param title title of the game(s) to find
     * @return a list of matching games or an empty list
     */
    public List<Game> getGamesByTile(String title, String searchMode) {
        List<Game> games = null;
        
        if(searchMode != null && searchMode.equals("strict")) {
            logger.debug("Getting games by title with strict search");
            games = gameRepository.findByTitle(title);
        } else {
            logger.debug("Getting games by title without strict search");
            games = gameRepository.findByTitleContainingOrderByTitleAsc(title);
        }
        
        if(games.isEmpty()) {
            logger.info("No games found with the title {}", title);
        }
        
        return games;
    }
    
    /**
     * Get the game with the matching id
     * @param id id of the game to get
     * @return the retrieved game or ??
     */
    public Game getGameById(final UUID id) {
        
        Optional<Game> gameOpt = gameRepository.findById(id);
        
        if(!gameOpt.isPresent()) {
            String message = String.format("Cannot found a game with this id: %s", id);
            logger.info(message);
            throw new GamestoreEntityException(message);
        }
        
        return gameOpt.get();
    }
    
    /**
     * Check if a game exists with this id
     * @param id id of the game to check
     * @return true is the game exists or false otherwise
     */
    public boolean gameExistById(final UUID id) {
        boolean isGameExist = gameRepository.existsById(id);
        
        if(!isGameExist) {
            logger.info("Cannot found a game with the id:{}", id);
        }
        
        return isGameExist;
    }
    
    /**
     * Save the game in the database
     * @param game a valid game
     * @return the created game or GamestoreEntityException
     */
    @CacheEvict
    public Game createGame(final Game gameToCreate) {
        Game createdGame = gameRepository.save(gameToCreate);
        
        if(createdGame.getId() == null) {
            String message = String.format("The following game has not been created: %s", gameToCreate);
            logger.error(message);
            throw new GamestoreEntityException(message);
        }
        
        return createdGame;
    }
    
    /**
     * Save the list of valid games - TO TEST WITH ERRORS
     * @param games all games to create
     * @return the created games or ??
     */
    public List<Game> createGames(List<Game> gamesToCreate) {
        StringBuilder errorMsg = new StringBuilder();
        boolean isAGameOnError = false;
        List<Game> games = (List<Game>) gameRepository.saveAll(gamesToCreate);
        
        
        for (Game game : games) {
            if(game.getId() == null) {
                String message = String.format("The game titled: %s has not been created", game.getTitle());
                errorMsg.append(message);
                logger.warn(message);
                isAGameOnError = true;
            }
        }
        
        if(isAGameOnError) {
            throw new GamestoreEntityException(errorMsg.toString());
        }
        
        return games;
    }
    
    /**
     * Update the game
     * @param game game to update
     * @return the updated game
     */
    public Game updateGame(final Game gameToUpdate) {
        return null;
    }
    
    /**
     * Add a developper to a game
     * @param gameId id of the game to update
     * @param dev developper to add to the game
     * @return updated game
     */
    public Game addDevToGame(final UUID gameId, final Developper dev) {
        Game gameToUpdate = getGameById(gameId);
        
        logger.debug("Adding the developper {} to the game {}", dev.getName(), gameToUpdate.getTitle());
        Set<Developper> devs = gameToUpdate.getDevs();
        devs.add(dev);
        
        return createGame(gameToUpdate);
    }
    
    /**
     * Add an editor to a game
     * @param gameId id of the game to update
     * @param editor editor to add to the game
     * @return updated game
     */
    public Game addEditorToGame(final UUID gameId, final Editor editor) {
        Game gameToUpdate = getGameById(gameId);
        
        logger.debug("Adding the editor {} to the game {}", editor.getName(), gameToUpdate.getTitle());
        Set<Editor> editors = gameToUpdate.getEditors();
        editors.add(editor);
        
        return createGame(gameToUpdate);
    }
    
    /**
     * Delete the game
     * @param id id of the game to delete
     * @return true is the game is deleted or false otherwise
     */
    @CacheEvict(key = "#id")
    public boolean deleteGame(final UUID id) {
        boolean isGameDeleted = false;
        boolean isGameExists = gameRepository.existsById(id);
        
        if(isGameExists) {
            logger.debug("Deleting game with the id: {}", id);
            gameRepository.deleteById(id);
            isGameDeleted = true;
        } else {
            logger.info("No game found with the id: {}", id);
        }
        
        return isGameDeleted;
    }
}
