package com.aymeric.gamestore.controller;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aymeric.gamestore.dto.GameDTO;
import com.aymeric.gamestore.dto.GameDevEditorRelationshipDTO;
import com.aymeric.gamestore.entity.Developper;
import com.aymeric.gamestore.entity.Editor;
import com.aymeric.gamestore.entity.Game;
import com.aymeric.gamestore.exception.GamestoreInvalidParameterException;
import com.aymeric.gamestore.service.DevelopperService;
import com.aymeric.gamestore.service.EditorService;
import com.aymeric.gamestore.service.GameService;

import io.swagger.annotations.ApiOperation;

/**
 * Manage request about games
 * @author Aymeric NEUMANN
 *
 */
@Validated
@RestController
@RequestMapping("/games")
public class GameController {
    
    /** Logback logger reference. */
    private static final Logger logger = LoggerFactory.getLogger(GameController.class);
    
    /** Game service reference. */
    @Autowired
    private GameService gameService;
    
    /** Developper service reference. */
    @Autowired
    private DevelopperService devService;
    
    /** Editor service reference. */
    @Autowired
    private EditorService editorService;
    
    @Autowired
    private ModelMapper modelMapper;
        
    /**
     * Get all games by page
     * @param pageNumber number of the required page - 0 based count
     * @return required page
     */
    @GetMapping("")
    @ApiOperation(value = "Get all games", notes = "Getting all games - paged result", response = Game[].class)
    public Page<GameDTO> getAllGames(@RequestParam(name = "pageNumber", required = true) final Integer pageNumber) {
        
        logger.debug("Getting all games at page: {}", pageNumber);
        
        return convertToDTOPage(gameService.getAllGames(pageNumber));
    }
    
    /**
     * Get all games with a matching title
     * @param title title of the game(s) to find
     * @return a list of matching games or an empty list
     */
    @GetMapping("byTitle")
    @ApiOperation(value = "Get games by title", notes = "Getting all games with matching title", response = Game[].class)
    public List<GameDTO> getGamesByTitle(
            @RequestParam(name = "title") final String title,
            @RequestParam(name = "searchMode", required = false) final String searchMode
            ) {
        logger.debug("Getting all games titled: {}", title);
        return convertToDTOList(gameService.getGamesByTile(title, searchMode));
    }
    
    /**
     * Get the game with the matching id - Test OK
     * @param id id of the game to get
     * @return the retrieved game or ??
     */
    @GetMapping("byId")
    @ApiOperation(value = "Get game by Id", notes = "Getting the game with the matching id", response = Game.class)
    public GameDTO getGameById(@RequestParam(name = "id") final UUID id) {
        logger.debug("Getting game with the id: {}", id);
        return convertToDto(gameService.getGameById(id));
    }
    
    /**
     * Check if a game exists with this id - Test OK
     * @param id id of the game to check
     * @return true is the game exists or false otherwise
     */
    @GetMapping("existById")
    @ApiOperation(value = "Check if a game exists by id", notes = "Checking if a game exists with this id", response = Boolean.class)
    public boolean gameExistById(@RequestParam(name = "id") final UUID id) {
        logger.debug("Check if game with id: {} exists", id);
        return gameService.gameExistById(id);
    }
    
    /**
     * Save the game in the database - Test OK
     * @param game a valid game
     * @param result spring framework validation interface
     * @return the created game or a GamestoreInvalidParameterException
     */
    @PostMapping("create")
    @ApiOperation(value = "Create a new game", notes = "Creating a new game", response = Game.class)
    public GameDTO createGame(@RequestBody @Valid GameDTO gameDto, final BindingResult result) {
        
        if(result.hasErrors()) {
            logger.info("At least one field is empty or invalid: {}", result);
            throw new GamestoreInvalidParameterException("At least one field is empty or invalid", result);
        }
        logger.debug("Creating the game titled {}", gameDto.getTitle());
        
        Game game = convertToEntity(gameDto);
        
        return convertToDto(gameService.createGame(game));
    }
        
    /**
     * Save the list of valid games - Test OK
     * @param games all games to create
     * @return the created games or ??
     */
    @PostMapping("createAll")
    @ApiOperation(value = "Create multiple games", notes = "Creating a list of games", response = Game[].class)
    public List<GameDTO> createGames(@RequestBody @Valid final List<GameDTO> games) {
        logger.debug("Creating the following games {}", games);
        
        List<Game> gamesEntities = convertToEntityList(games);
        
        return convertToDTOList(gameService.createGames(gamesEntities));
    }
    
    /**
     * Update the game
     * @param game game to update
     * @return the updated game
     */
    @PutMapping("update")
    @ApiOperation(value = "Update game", notes = "Updating a game", response = Game.class)
    public GameDTO updateGame(@RequestBody @Valid GameDTO game) {
        return null;
    }
    
    /**
     * Add a developper for a game
     * @param gameId id of the game to add the developper to
     * @param infos GameDevEditorRelationshipDTO containing id of the game and of the developper
     * @return updated game
     */
    @PutMapping("addDevelopper")
    @ApiOperation(value = "Add a developper to a game", notes = "Adding a developper to a game", response = Game.class)
    public GameDTO addDevToGame(@RequestParam(name = "gameId") final UUID gameId, @RequestBody @Valid GameDevEditorRelationshipDTO infos) {
        if(!gameId.equals(infos.getGameId())) {
            throw new GamestoreInvalidParameterException("URL parameter doesn't not match with the request body");
        }
        checkIdEquality(gameId, infos);
        
        Game updatedGame = null;
        StringBuilder errorMsg = new StringBuilder();
        boolean isGameExist = gameService.gameExistById(infos.getGameId());
        boolean isDevExist = devService.developperExistById(infos.getDevId());
                
        if(isGameExist && isDevExist) {
            Developper dev = devService.getDeveloppersById(infos.getDevId());
            updatedGame = gameService.addDevToGame(infos.getGameId(), dev);
        } 
        
        if(!isGameExist) {
            String message = String.format("Cannot found a game with this id: %s", infos.getGameId());
            errorMsg.append(message);
            logger.info(message);
        }
        if(!isDevExist) {
            String message = String.format("Cannot found a developper with this id: %s", infos.getDevId());
            errorMsg.append(message);
            logger.info(message);
        }
        
        if(!isGameExist || !isDevExist) {
            throw new GamestoreInvalidParameterException(errorMsg.toString());
        }
        
        return convertToDto(updatedGame);
    }
    
    /**
     * Add a editor for a game
     * @param gameId id of the game to add the editor to
     * @param devId id of the editor to add
     * @return updated game
     */
    @PutMapping("addEditor")
    @ApiOperation(value = "Add an editor to a game", notes = "Adding an editor to a game", response = Game.class)
    public GameDTO addEditorToGame(
            @RequestParam(name = "gameId") final UUID gameId, 
            @RequestBody @Valid GameDevEditorRelationshipDTO infos
            ) {
        checkIdEquality(gameId, infos);
        
        Game updatedGame = null;
        StringBuilder errorMsg = new StringBuilder();
        boolean isGameExist = gameService.gameExistById(infos.getGameId());
        boolean isEditorExist = editorService.editorExistById(infos.getEditorId());
                        
        if(isGameExist && isEditorExist) {
            Editor editor = editorService.getEditorById(infos.getEditorId());
            updatedGame = gameService.addEditorToGame(infos.getGameId(), editor);
        }
        
        if(!isGameExist) {
            String message = String.format("Cannot found a game with this id: %s", infos.getGameId());
            errorMsg.append(message);
            logger.info(message);
        }
        if(!isEditorExist) {
            String message = String.format("Cannot found a editor with this id: %s", infos.getEditorId());
            errorMsg.append(message);
            logger.info(message);
        }
        
        if(!isGameExist || !isEditorExist) {
            throw new GamestoreInvalidParameterException(errorMsg.toString());
        }
        
        return convertToDto(updatedGame);
    }
    
    /**
     * Delete the game
     * @param id id of the game to delete
     * @return true is the game is deleted or false otherwise
     */
    @DeleteMapping("delete")
    @ApiOperation(value = "Delete a game", notes = "Deleting the game with the matching id", response = Boolean.class)
    public boolean deleteGame(@RequestParam(name = "id") final UUID id) {
        return gameService.deleteGame(id);
    }
        
    /**
     * Check if the game id passed in the URL parameters and in the request body are equals
     * @param gameId id of the game passed in the URL parameters
     * @param infos GameDevEditorRelationshipDTO containing the id of the game to update
     */
    private void checkIdEquality(final UUID gameId, final GameDevEditorRelationshipDTO infos) {
        if(!gameId.equals(infos.getGameId())) {
            throw new GamestoreInvalidParameterException("URL parameter doesn't not match with the request body");
        }
    }
    
    /**
     * Convert Game Entity to GameDTO class
     * @param game Game Entity to convert
     * @return a GameDTO
     */
    private GameDTO convertToDto(final Game game) {
        return modelMapper.map(game, GameDTO.class);
    }
    
    /**
     * Convert GameDTO class to a Game Entity
     * @param gameDTO GameDTO to convert
     * @return a Game Entity
     */
    private Game convertToEntity(final GameDTO gameDTO) {
        return modelMapper.map(gameDTO, Game.class);
    }
    
    /**
     * Convert Game Entities page to GameDTO page
     * @param games page of Game Entities to convert
     * @return a Page of GameDTO
     */
    private Page<GameDTO> convertToDTOPage(final Page<Game> games) {
        Type pageType = new TypeToken<Page<GameDTO>>() {}.getType();
        
        return new ModelMapper().map(games, pageType);
    }
    
    /**
     * Convert a list of Game Entities to a list of GameDTO
     * @param games list of Game Entities to convert
     * @return a List of GameDTO
     */
    private List<GameDTO> convertToDTOList(final List<Game> games) {
        Type pageType = new TypeToken<List<GameDTO>>() {}.getType();
        
        return new ModelMapper().map(games, pageType);
    }
    
    /**
     * Convert a list of GameDTO to a list of Game Entities
     * @param games list of GameDTO to convert
     * @return a List of GameEntities
     */
    private List<Game> convertToEntityList(final List<GameDTO> games) {
        Type pageType = new TypeToken<List<Game>>() {}.getType();
        
        return new ModelMapper().map(games, pageType);
    }
}
