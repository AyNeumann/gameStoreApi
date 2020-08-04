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
import com.aymeric.gamestore.service.DevelopperService;

/**
 * Manage request about Developper
 * @author Aymeric NEUMANN
 *
 */
@Validated
@RestController
@RequestMapping("/developpers")
public class DevelopperController {
    
    @Autowired
    private DevelopperService devService;
    
    /**
     * Get all developpers by page
     * @param pageNumber number of the required page - 0 based count
     * @return required page
     */
    @GetMapping("all")
    public Page<Developper> getAllDeveloppers(@RequestParam(name = "pageNumber", required = true) final Integer pageNumber){
        return devService.getAllDeveloppers(pageNumber);
    }
    
    /**
     * Get all developpers with a matching name
     * @param name name of the developement company to find
     * @return a list of matching developement company or an empty list
     */
    @GetMapping("byName")
    public List<Developper> getDeveloppersByName(@RequestParam(name="name") final String name) {
        return devService.getDeveloppersByName(name);
    }
    
    /**
     * Get the developper with the matching id
     * @param id id of the developper to get
     * @return the retrieved developper or ??
     */
    @GetMapping("byId")
    public Developper getDeveloppersById(@RequestParam(name="id") final Long id) {
        return devService.getDeveloppersById(id);
    }
    
    /**
     * Check if a developper exists with this id
     * @param id id of the developper to check
     * @return true is the developper exists or false otherwise
     */
    @GetMapping("existById")
    public boolean developperExistById(@RequestParam(name="id") final Long id) {
        return devService.developperExistById(id);
    }
    
    /**
     * Save the developper in the database
     * @param developper a valid developper
     * @return the created developper or ??
     */
    @PostMapping("/create")
    public Developper createDevelopper(@RequestBody @Valid final Developper developper) {
        return devService.createDevelopper(developper);
    }
    
    /**
     * Save a list of validated developpers
     * @param developpers a list of developpers
     * @return the created developpers or ??
     */
    @PostMapping("createAll")
    public List<Developper> createDeveloppers(@RequestBody @Valid final List<Developper> developpers){
        return devService.createDeveloppers(developpers);
    }
    
    /**
     * Update the developper
     * @param developper developper to update
     * @return the updated developper
     */
    @PutMapping("update")
    public Developper updateDevelopper(@RequestBody @Valid final Developper developper) {
        return null;
    }
    
    /**
     * Delete the developper
     * @param id id of the developper to delete
     * @return the deleted developper
     */
    @DeleteMapping("delete")
    public boolean deleteDevelopper(@RequestParam(name = "id") final Long id) {
        return devService.deleteDevelopper(id);
    }
    
    
}
