package com.aymeric.gamestore.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        
        return devRepository.findAll(pageable);
    }
    
    /**
     * Get all developpers with a matching name
     * @param name name of the developement company to find
     * @return a list of matching developement company or an empty list
     */
    public List<Developper> getDeveloppersByName(final String name) {
        return devRepository.findByName(name);
    }
    
    /**
     * Get the developper with the matching id
     * @param id id of the developper to get
     * @return the retrieved developper or ??
     */
    public Developper getDeveloppersById(final UUID id) {
        Optional<Developper> developperOpt = devRepository.findById(id);
        
        if(!developperOpt.isPresent()) {
            StringBuilder message = new StringBuilder();
            message.append("No Developper found with this id: ");
            message.append(id);
            System.err.println(message);
            throw new GamestoreEntityException(message.toString());
        }
        
        return developperOpt.get();
    }
    
    public boolean developperExistById(final UUID id) {
        return devRepository.existsById(id);
    }
    
    /**
     * Save the developper in the database
     * @param developper a valid developper
     * @return the created developper or ??
     */
    public Developper createDevelopper(final Developper developper) {
        return devRepository.save(developper);
    }
    
    /**
     * Save a list of validated developpers
     * @param developpers a list of developpers
     * @return the created developpers or ??
     */
    public List<Developper> createDeveloppers(final List<Developper> developpers) {
        return (List<Developper>) devRepository.saveAll(developpers);
    }
    
    /**
     * Update the developper
     * @param developper developper to update
     * @return the updated developper
     */
    public Developper updateDevelopper(final Developper developper) {
        return null;
    }
    
    public Developper addOwner(final UUID devId, final Editor owner) {
        Developper devToUpdate = getDeveloppersById(devId);
        
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
            devRepository.deleteById(id);
            isDevDeleted = true;
        }
        
        return isDevDeleted;
    }
}
