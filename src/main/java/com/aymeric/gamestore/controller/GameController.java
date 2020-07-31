package com.aymeric.gamestore.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aymeric.gamestore.entity.Game;
import com.aymeric.gamestore.service.GameService;

/**
 * Manage request about games
 * @author Aymeric NEUMANN
 *
 */
@Validated
@RestController
@RequestMapping("/games")
public class GameController {
    
    @Autowired
    private GameService gameService;
    
    /**
     * Get all games
     * @return all games
     */
    @GetMapping("all")
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }
    
    /**
     * Get all games with a matching title
     * @param title title of the game(s) to find
     * @return a list of matching games or an empty list
     */
    @GetMapping("byTitle")
    public List<Game> getGamesByTitle(@RequestParam(name = "name") final String title) {
        return gameService.getGamesByTile(title);
    }
    
    /**
     * Get the game with the matching id
     * @param id id of the game to get
     * @return the retrieved game or ??
     */
    @GetMapping("byId")
    public Game getGameById(@RequestParam(name = "id") final Long id) {
        return gameService.getGameById(id);
    }
    
    /**
     * Save the game in the database
     * @param game a valid game
     * @return the created game or ??
     */
    @PostMapping("create")
    public Game createGame(@RequestBody @Valid Game game) {
        return gameService.createGame(game);
    }
    
    /**
     * Save the list of valid games
     * @param games all games to create
     * @return the created games or ??
     */
    @PostMapping("createAll")
    public List<Game> createGames(@RequestBody @Valid final List<Game> games) {
        return gameService.createGames(games);
    }
    
    /**
     * Update the game
     * @param game game to update
     * @return the updated game
     */
    @PutMapping("update")
    public Game updateGame(@RequestBody @Valid Game game) {
        return null;
    }
    
    /**
     * Delete the game
     * @param id id of the game to delete
     * @return the deleted game
     */
    @DeleteMapping("delete")
    public Game deleteGame(@RequestParam(name = "id") final Long id) {
        return null;
    }
}
