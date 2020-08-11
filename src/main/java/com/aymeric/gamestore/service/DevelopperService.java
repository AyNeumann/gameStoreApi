package com.aymeric.gamestore.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.aymeric.gamestore.entity.Developper;
import com.aymeric.gamestore.entity.Editor;
import com.aymeric.gamestore.exception.GamestoreEntityException;
import com.aymeric.gamestore.repository.DevelopperRepository;

@Service
public class DevelopperService {
    
    /** Logback logger reference. */
    private static final Logger logger = LoggerFactory.getLogger(DevelopperService.class);
    
    /** Number of user return per page. */
    private static final int NUM_OF_USER_PER_PAGE = 50;
    
    @Autowired
    DevelopperRepository devRepository;
    
    /**
     * Get all developpers by page of 50 result each in title alphabetical order
     * @param pageNumber number of the required page
     * @return required page
     */
    public Page<Developper> getAllDeveloppers(final Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, NUM_OF_USER_PER_PAGE, Sort.by("name"));
         
        Page<Developper> developpers = devRepository.findAll(pageable);
        
        if(developpers.isEmpty()) {
            logger.warn("No developpers found on the page number {}", pageNumber);
        }
        
        return developpers;
    }
    
    /**
     * Get all developpers with a matching name
     * @param name name of the developement company to find
     * @return a list of matching developement company or an empty list
     */
    public List<Developper> getDeveloppersByName(final String name, final String searchMode) {
        List<Developper> developpers = null;
        
        if(searchMode != null && searchMode.equals("strict")) {
            logger.debug("Getting developpers by name with strict search");
            developpers = devRepository.findByName(name);
        } else {
            developpers = devRepository.findByNameContainingOrderByNameAsc(name);
        }
        
        
        if(developpers.isEmpty()) {
            logger.info("No developpers found with the name {}", name);
        }
        
        return developpers;
    }
    
    /**
     * Get the developper with the matching id
     * @param id id of the developper to get
     * @return the retrieved developper or ??
     */
    public Developper getDeveloppersById(final UUID id) {
        Optional<Developper> developperOpt = devRepository.findById(id);
        
        if(!developperOpt.isPresent()) {
            String message = String.format("Cannot found a developper with this id: %s", id);
            logger.info(message);
            throw new GamestoreEntityException(message);
        }
        
        return developperOpt.get();
    }
    
    /**
     * Check if a developper exists with this id
     * @param id id of the game to check
     * @return true is the game exists or false otherwise
     */
    public boolean developperExistById(final UUID id) {
        boolean isDevExist = devRepository.existsById(id);
        
        if(!isDevExist) {
            String message = String.format("Cannot found a developper with this id: %s", id);
            logger.info(message);
            throw new GamestoreEntityException(message);
        }
        
        return isDevExist;
    }
    
    /**
     * Save the developper in the database
     * @param developper a valid developper
     * @return the created developper or ??
     */
    public Developper createDevelopper(final Developper developper) {
        Developper createdDev = devRepository.save(developper);
        
        if(createdDev.getId() == null) {
            String message = String.format("The following developper has not been created: %s", developper);
            logger.error(message);
            throw new GamestoreEntityException(message);
        }
        
        return createdDev;
    }
    
    /**
     * Save a list of validated developpers
     * @param developpers a list of developpers
     * @return the created developpers or ??
     */
    public List<Developper> createDeveloppers(final List<Developper> developpers) {
        StringBuilder errorMsg = new StringBuilder();
        boolean isADevOnError = false;
        List<Developper> savedDevs = (List<Developper>) devRepository.saveAll(developpers);
        
        for(Developper dev : savedDevs) {
            if(dev.getId() == null) {
                String message = String.format("The developper named: %s has not been created", dev.getName());
                errorMsg.append(message);
                logger.warn(message);
                isADevOnError = true;
            }
        }
        
        if(isADevOnError) {
            throw new GamestoreEntityException(errorMsg.toString());
        }
        
        return developpers;
    }
    
    /**
     * Update the developper
     * @param developper developper to update
     * @return the updated developper
     */
    public Developper updateDevelopper(final Developper developper) {
        return null;
    }
    
    /**
     * Add an owner to a developper
     * @param devId id of the developper to update
     * @param owner owner to add to the developper
     * @return updated developper
     */
    public Developper addOwner(final UUID devId, final Editor owner) {
        Developper devToUpdate = getDeveloppersById(devId);
        
        logger.debug("Adding the owner {} to the dev {}", owner.getName(), devToUpdate.getName());
        devToUpdate.setOwner(owner);
        
        devRepository.save(devToUpdate);
        
        return devToUpdate;
    }
    
    /**
     * Delete the developper
     * @param id id of the developper to delete
     * @return the deleted developper
     */
    public boolean deleteDevelopper(final UUID id) {
        boolean isDevDeleted = false;
        boolean isDevExists = devRepository.existsById(id);
        
        if(isDevExists) {
            logger.debug("Deleting developper with the id: {}", id);
            devRepository.deleteById(id);
            isDevDeleted = true;
        } else {
            logger.info("No developper found with the id: {}", id);
        }
        
        return isDevDeleted;
    }
}
