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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aymeric.gamestore.dto.DeveloperDTO;
import com.aymeric.gamestore.dto.GameDevEditorRelationshipDTO;
import com.aymeric.gamestore.entity.Developer;
import com.aymeric.gamestore.entity.Editor;
import com.aymeric.gamestore.exception.GamestoreInvalidParameterException;
import com.aymeric.gamestore.service.DeveloperService;
import com.aymeric.gamestore.service.EditorService;

/**
 * Manage request about Developer
 * @author Aymeric NEUMANN
 *
 */
@Validated
@RestController
@RequestMapping("/developers")
public class DeveloperController {
    
    /** Logback logger reference. */
    private static final Logger logger = LoggerFactory.getLogger(DeveloperController.class);
    
    @Autowired
    private DeveloperService devService;
    
    @Autowired
    private EditorService editorService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    /**
     * Get all developers by page
     * @param pageNumber number of the required page - 0 based count
     * @return required page
     */
    @GetMapping(value = "/{pageNumber}")
    public Page<DeveloperDTO> getAllDevelopers(@PathVariable("pageNumber") final Integer pageNumber){
        logger.debug("Getting all developers at page: {}", pageNumber);
        return convertToDTOPage(devService.getAllDevelopers(pageNumber));
    }
    
    /**
     * Get all developers with a matching name
     * @param name name of the development company to find
     * @return a list of matching development company or an empty list
     */
    @GetMapping(value = "name/{name}/{searchMode}")
    public List<DeveloperDTO> getDevelopersByName(
            @PathVariable("name") final String name,
            @PathVariable("searchMode") final String searchMode
            ) {
        logger.debug("Getting all developpers named: {}", name);
        return convertToDTOList(devService.getDeveloppersByName(name, searchMode));
    }
    
    /**
     * Get the developer with the matching id
     * @param id id of the developer to get
     * @return the retrieved developer or ??
     */
    @GetMapping(value = "id/{id}")
    public DeveloperDTO getDevelopersById(@PathVariable("id") final UUID id) {
        logger.debug("Getting the developper with the id: {}", id);
        return convertToDTO(devService.getDeveloppersById(id));
    }
    
    /**
     * Check if a developer exists with this id
     * @param id id of the developer to check
     * @return true is the developer exists or false otherwise
     */
    @GetMapping(value = "id/{id}/exist")
    public boolean developerExistById(@PathVariable("id") final UUID id) {
        logger.debug("Checking if the developper with the id: {} exists", id);
        return devService.developperExistById(id);
    }
    
    /**
     * Save the developer in the database
     * @param developper a valid developer
     * @return the created developer or ??
     */
    @PostMapping("")
    public List<DeveloperDTO> createDeveloper(@RequestBody @Valid final List<DeveloperDTO> developperDto, final BindingResult result) {
        
        if(result.hasErrors()) {
            //FIXME: Comprendre pourquoi ConstraintViolationException est prio
            logger.info("At least one field is empty or invalid: {}", result);
            throw new GamestoreInvalidParameterException("At least one field is empty or invalid", result);
        }
        logger.debug("Creating the developper titled {}", developperDto);
        
        List<Developer> devs = convertToEnityList(developperDto);
        
        return convertToDTOList(devService.createDeveloppers(devs));
    }
    
    /**
     * Update the developer
     * @param developper developer to update
     * @return the updated developer
     */
    @PutMapping("/{id}")
    public DeveloperDTO updateDeveloper(@PathVariable("id") final UUID id, @RequestBody @Valid final DeveloperDTO developer) {
        return null;
    }
    
    /**
     * Add an owner to a developer
     * @param devId id of the developer to add the owner to
     * @param infos GameDevEditorRelationshipDTO containing the developer id and the editor id
     * @return
     */
    @PutMapping("/{id}/owner")
    public DeveloperDTO addOwner(@PathVariable("id") final UUID id, @RequestBody @Valid GameDevEditorRelationshipDTO infos) {
        if(!id.equals(infos.getDevId())) {
            throw new GamestoreInvalidParameterException("URL parameter doesn't not match with the request body");
        }
        
        Developer devToUpdate = null;
        StringBuilder errorMsg = new StringBuilder();
        boolean isDevExist = devService.developperExistById(infos.getDevId());
        boolean isOwnerExist = editorService.editorExistById(infos.getEditorId());
        
        if(isDevExist && isOwnerExist) {
            Editor editor = editorService.getEditorById(infos.getEditorId());
            devToUpdate = devService.addOwner(id, editor);
        }
        
        if(!isDevExist) {
            String message = String.format("Cannot found a developper with this id: %s", infos.getDevId());
            errorMsg.append(message);
            logger.info(message);
        }
        if(!isOwnerExist) {
            String message = String.format("Cannot found an editor with this id: %s", infos.getEditorId());
            errorMsg.append(message);
            logger.info(message);
        }
        
        return convertToDTO(devToUpdate);
    }
    
    /**
     * Delete the developer
     * @param id id of the developer to delete
     * @return the deleted developer
     */
    @DeleteMapping("")
    public boolean deleteDeveloper(@PathVariable("id") final UUID id) {
        return devService.deleteDevelopper(id);
    }
    
    
    
    /**
     * Convert Developer Entity to DevelopperDTO class
     * @param dev Developer Entity to convert
     * @return a DevelopperDTO
     */
    private DeveloperDTO convertToDTO(Developer dev) {
        return modelMapper.map(dev, DeveloperDTO.class);
    }
    
    /**
     * Convert DeveloperDTO class to a Developer Entity
     * @param devDTO DeveloperDTO to convert
     * @return a Developer Entity
     */
    private Developer convertToEntity(DeveloperDTO devDTO) {
        return modelMapper.map(devDTO, Developer.class);
    }
    
    /**
     * Convert Developer Entities page to DeveloperDTO page
     * @param devs page of Developer Entities to convert
     * @return a Page of DeveloperDTO
     */
    private Page<DeveloperDTO> convertToDTOPage(Page<Developer> devs) {
        Type pageType = new TypeToken<Page<DeveloperDTO>>() {}.getType();
        
        return new ModelMapper().map(devs, pageType);
    }
    
    /**
     * Convert a list of Developer Entities to a list of DeveloperDTO
     * @param devs list of Developer Entities to convert
     * @return a List of DeveloperDTO
     */
    private List<DeveloperDTO> convertToDTOList(List<Developer> devs) {
        Type pageType = new TypeToken<List<DeveloperDTO>>() {}.getType();
        
        return new ModelMapper().map(devs, pageType);
    }
    
    /**
     * Convert a list of DeveloperDTOs to a list of Developer Entities
     * @param devs list of DeveloperDTO to convert
     * @return a List of Developer
     */
    private List<Developer> convertToEnityList(List<DeveloperDTO> devs) {
        Type pageType = new TypeToken<List<Developer>>() {}.getType();
        
        return new ModelMapper().map(devs, pageType);
    }
}
