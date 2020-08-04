package com.aymeric.gamestore.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aymeric.gamestore.entity.Developper;
import com.aymeric.gamestore.entity.Editor;
import com.aymeric.gamestore.entity.Game;
import com.aymeric.gamestore.service.DevelopperService;
import com.aymeric.gamestore.service.EditorService;
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
    
    @Autowired
    private DevelopperService devService;
    
    @Autowired
    private EditorService editorService;
    
    /**
     * Get all games by page
     * @param pageNumber number of the required page - 0 based count
     * @return required page
     */
    @GetMapping("all")
    public Page<Game> getAllGames(@RequestParam(name = "pageNumber", required = true) final Integer pageNumber) {
        return gameService.getAllGames(pageNumber);
    }
    
    /**
     * Get all games with a matching title
     * @param title title of the game(s) to find
     * @return a list of matching games or an empty list
     */
    @GetMapping("byTitle")
    public List<Game> getGamesByTitle(@RequestParam(name = "title") final String title) {
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
     * Check if a game exists with this id
     * @param id id of the game to check
     * @return true is the game exists or false otherwise
     */
    @GetMapping("existById")
    public boolean gameExistById(@RequestParam(name = "id") final Long id) {
        return gameService.gameExistById(id);
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
     * Add a developper for a game
     * @param gameId id of the game to add the developper to
     * @param devId id of the developper to add
     * @return updated game
     */
    @PutMapping("addDevelopper")
    public Game addDevToGame(@RequestParam(name = "gameId") final Long gameId, @RequestParam(name = "devId") final Long devId) {
        Game updatedGame = null;
        boolean isGameExist = gameService.gameExistById(gameId);
        boolean isDevExist = devService.developperExistById(devId);
                
        if(isGameExist && isDevExist) {
            Developper dev = devService.getDeveloppersById(devId);
            updatedGame = gameService.addDevToGame(gameId, dev);
        } else {
            System.out.println("Error while retrieving game or developper: invalid id");
        }
        
        return updatedGame;
    }
    
    /**
     * Add a editor for a game
     * @param gameId id of the game to add the editor to
     * @param devId id of the editor to add
     * @return updated game
     */
    @PutMapping("addEditor")
    public Game addEditorToGame(@RequestParam(name = "gameId") final Long gameId, @RequestParam(name = "editorId") final Long editorId) {
        Game updatedGame = null;
        boolean isGameExist = gameService.gameExistById(gameId);
        boolean isEditorExist = editorService.editorExistById(editorId);
                        
        if(isGameExist && isEditorExist) {
            Editor editor = editorService.getEditorById(editorId);
            updatedGame = gameService.addEditorToGame(gameId, editor);
        } else {
            System.out.println("Error while retrieving game or editor: invalid id");
        }
        
        return updatedGame;
    }
    
    /**
     * Delete the game
     * @param id id of the game to delete
     * @return true is the game is deleted or false otherwise
     */
    @DeleteMapping("delete")
    public boolean deleteGame(@RequestParam(name = "id") final Long id) {
        return gameService.deleteGame(id);
    }
}
