package com.aymeric.gamestore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aymeric.gamestore.entity.Game;
import com.aymeric.gamestore.repository.GameRepository;

/**
 * Service for games
 * @author Aymeric NEUMANN
 *
 */
@Service
public class GameService {
    
    @Autowired
    private GameRepository gameRepository;
    
    public List<Game> getAllGames(){
        return (List<Game>) gameRepository.findAll();
    }
    
    public List<Game> getGamesByTile(String title) {
        return gameRepository.findByTitle(title);
    }
    
    public Game getGameById(Long id) {
        
        Optional<Game> gameOpt = gameRepository.findById(id);
        
        if(!gameOpt.isPresent()) {
            System.out.println("No game found with this id");
        }
        
        return gameOpt.get();
    }
    
    public Game createGame(Game gameToCreate) {
        return gameRepository.save(gameToCreate);
    }
    
    public List<Game> createGames(List<Game> gamesToCreate) {
        return (List<Game>) gameRepository.saveAll(gamesToCreate);
    }
    
    public Game updateGame(Game gameToUpdate) {
        return null;
    }
    
    
}
