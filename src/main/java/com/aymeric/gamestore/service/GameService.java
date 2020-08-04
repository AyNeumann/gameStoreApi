package com.aymeric.gamestore.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
    
    /** Number of user return per page. */
    private static final int NUM_OF_USER_PER_PAGE = 50;
    
    @Autowired
    private GameRepository gameRepository;
    
    /**
     * Get all games by page of 50 result each in title alphabetical order
     * @param pageNumber number of the required page
     * @return required page
     */
    public Page<Game> getAllGames(final Integer pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, NUM_OF_USER_PER_PAGE, Sort.by("title"));
        
        return gameRepository.findAll(pageable);
    }
    
    /**
     * Get all games with a matching title
     * @param title title of the game(s) to find
     * @return a list of matching games or an empty list
     */
    public List<Game> getGamesByTile(String title) {
        return gameRepository.findByTitle(title);
    }
    
    /**
     * Get the game with the matching id
     * @param id id of the game to get
     * @return the retrieved game or ??
     */
    public Game getGameById(final Long id) {
        
        Optional<Game> gameOpt = gameRepository.findById(id);
        
        if(!gameOpt.isPresent()) {
            StringBuilder message = new StringBuilder();
            message.append("No Game found with this id: ");
            message.append(id);
            System.err.println(message);
            throw new GamestoreEntityException(message.toString());
        }
        
        return gameOpt.get();
    }
    
    /**
     * Check if a game exists with this id
     * @param id id of the game to check
     * @return true is the game exists or false otherwise
     */
    public boolean gameExistById(final Long id) {
        return gameRepository.existsById(id);
    }
    
    /**
     * Save the game in the database
     * @param game a valid game
     * @return the created game or ??
     */
    public Game createGame(final Game gameToCreate) {
        return gameRepository.save(gameToCreate);
    }
    
    /**
     * Save the list of valid games
     * @param games all games to create
     * @return the created games or ??
     */
    public List<Game> createGames(List<Game> gamesToCreate) {
        return (List<Game>) gameRepository.saveAll(gamesToCreate);
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
    public Game addDevToGame(final Long gameId, final Developper dev) {
        Game gameToUpdate = getGameById(gameId);
        
        Set<Developper> devs = gameToUpdate.getDevs();
        devs.add(dev);
        
        return createGame(gameToUpdate);
    }
    
    /**
     * Add an editor to a game
     * @param gameId id of the game to update
     * @param editor editor to add to th game
     * @return updated game
     */
    public Game addEditorToGame(final Long gameId, final Editor editor) {
        Game gameToUpdate = getGameById(gameId);
        
        Set<Editor> editors = gameToUpdate.getEditors();
        editors.add(editor);
        
        return createGame(gameToUpdate);
    }
    
    /**
     * Delete the game
     * @param id id of the game to delete
     * @return true is the game is deleted or false otherwise
     */
    public boolean deleteGame(final Long id) {
        boolean isGameDeleted = false;
        boolean isGameExists = gameRepository.existsById(id);
        
        if(isGameExists) {
            gameRepository.deleteById(id);
            isGameDeleted = true;
        }
        
        return isGameDeleted;
    }
}
