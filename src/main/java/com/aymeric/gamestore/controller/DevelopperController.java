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

import com.aymeric.gamestore.dto.DevelopperDTO;
import com.aymeric.gamestore.entity.Developper;
import com.aymeric.gamestore.entity.Editor;
import com.aymeric.gamestore.exception.GamestoreInvalidParameterException;
import com.aymeric.gamestore.service.DevelopperService;
import com.aymeric.gamestore.service.EditorService;

/**
 * Manage request about Developper
 * @author Aymeric NEUMANN
 *
 */
@Validated
@RestController
@RequestMapping("/developpers")
public class DevelopperController {
    
    /** Logback logger reference. */
    private static final Logger logger = LoggerFactory.getLogger(DevelopperController.class);
    
    @Autowired
    private DevelopperService devService;
    
    @Autowired
    private EditorService editorService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    /**
     * Get all developpers by page
     * @param pageNumber number of the required page - 0 based count
     * @return required page
     */
    @GetMapping("")
    public Page<DevelopperDTO> getAllDeveloppers(@RequestParam(name = "pageNumber", required = true) final Integer pageNumber){
        logger.debug("Getting all developpers at page: {}", pageNumber);
        return convertToDTOPage(devService.getAllDeveloppers(pageNumber));
    }
    
    /**
     * Get all developpers with a matching name
     * @param name name of the developement company to find
     * @return a list of matching developement company or an empty list
     */
    @GetMapping("byName")
    public List<DevelopperDTO> getDeveloppersByName(
            @RequestParam(name="name") final String name,
            @RequestParam(name = "searchMode", required = false) final String searchMode
            ) {
        logger.debug("Getting all developpers named: {}", name);
        return convertToDTOList(devService.getDeveloppersByName(name, searchMode));
    }
    
    /**
     * Get the developper with the matching id
     * @param id id of the developper to get
     * @return the retrieved developper or ??
     */
    @GetMapping("byId")
    public DevelopperDTO getDeveloppersById(@RequestParam(name="id") final UUID id) {
        logger.debug("Getting the developper with the id: {}", id);
        return convertToDTO(devService.getDeveloppersById(id));
    }
    
    /**
     * Check if a developper exists with this id
     * @param id id of the developper to check
     * @return true is the developper exists or false otherwise
     */
    @GetMapping("existById")
    public boolean developperExistById(@RequestParam(name="id") final UUID id) {
        logger.debug("Checking if the developper with the id: {} exists", id);
        return devService.developperExistById(id);
    }
    
    /**
     * Save the developper in the database
     * @param developper a valid developper
     * @return the created developper or ??
     */
    @PostMapping("create")
    public DevelopperDTO createDevelopper(@RequestBody @Valid final DevelopperDTO developperDto, final BindingResult result) {
        
        if(result.hasErrors()) {
            //FIXME: Comprendre pourquoi ConstraintViolationException est prio
            logger.info("At least one field is empty or invalid: {}", result);
            throw new GamestoreInvalidParameterException("At least one field is empty or invalid", result);
        }
        logger.debug("Creating the developper titled {}", developperDto.getName());
        
        Developper dev = convertToEntity(developperDto);
        
        return convertToDTO(devService.createDevelopper(dev));
    }
    
    /**
     * Save a list of validated developpers
     * @param developpers a list of developpers
     * @return the created developpers or ??
     */
    @PostMapping("createAll")
    public List<DevelopperDTO> createDeveloppers(@RequestBody @Valid final List<DevelopperDTO> developpersDTO){
        logger.debug("Creating the following developpers {}", developpersDTO);
        
        List<Developper> devs = convertToEnityList(developpersDTO);
        
        return convertToDTOList(devService.createDeveloppers(devs));
    }
    
    /**
     * Update the developper
     * @param developper developper to update
     * @return the updated developper
     */
    @PutMapping("update")
    public DevelopperDTO updateDevelopper(@RequestBody @Valid final DevelopperDTO developper) {
        return null;
    }
    
    @PutMapping("addOwner")
    public DevelopperDTO addOwner(@RequestParam(name = "devId") final UUID devId, @RequestParam(name = "ownerId") final UUID ownerId) {
        Developper devToUpdate = null;
        boolean isDevExist = devService.developperExistById(devId);
        boolean isOwnerExist = editorService.editorExistById(ownerId);
        
        if(isDevExist && isOwnerExist) {
            Editor editor = editorService.getEditorById(ownerId);
            devToUpdate = devService.addOwner(devId, editor);
        } else {
            throw new GamestoreInvalidParameterException("At least one of the id is invalid");
        }
        
        return convertToDTO(devToUpdate);
    }
    
    /**
     * Delete the developper
     * @param id id of the developper to delete
     * @return the deleted developper
     */
    @DeleteMapping("delete")
    public boolean deleteDevelopper(@RequestParam(name = "id") final UUID id) {
        return devService.deleteDevelopper(id);
    }
    
    
    
    /**
     * Convert Developper Entity to DevelopperDTO class
     * @param dev Developper Entity to convert
     * @return a DevelopperDTO
     */
    private DevelopperDTO convertToDTO(Developper dev) {
        return modelMapper.map(dev, DevelopperDTO.class);
    }
    
    /**
     * Convert DevelopperDTO class to a Developper Entity
     * @param devDTO DevelopperDTO to convert
     * @return a Developper Entity
     */
    private Developper convertToEntity(DevelopperDTO devDTO) {
        return modelMapper.map(devDTO, Developper.class);
    }
    
    /**
     * Convert Developper Entities page to DevelopperDTO page
     * @param devs page of Developper Entities to convert
     * @return a Page of DevelopperDTO
     */
    private Page<DevelopperDTO> convertToDTOPage(Page<Developper> devs) {
        Type pageType = new TypeToken<Page<DevelopperDTO>>() {}.getType();
        
        return new ModelMapper().map(devs, pageType);
    }
    
    /**
     * Convert a list of Developper Entities to a list of DevelopperDTO
     * @param devs list of Developper Entities to convert
     * @return a List of DevelopperDTO
     */
    private List<DevelopperDTO> convertToDTOList(List<Developper> devs) {
        Type pageType = new TypeToken<List<DevelopperDTO>>() {}.getType();
        
        return new ModelMapper().map(devs, pageType);
    }
    
    /**
     * Convert a list of DevelopperDTOs to a list of Developper Entities
     * @param devs list of DevelopperDTO to convert
     * @return a List of Developper
     */
    private List<Developper> convertToEnityList(List<DevelopperDTO> devs) {
        Type pageType = new TypeToken<List<Developper>>() {}.getType();
        
        return new ModelMapper().map(devs, pageType);
    }
}
