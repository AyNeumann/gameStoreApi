package com.aymeric.gamestore.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aymeric.gamestore.entity.Editor;
import com.aymeric.gamestore.service.EditorService;

@RestController
@RequestMapping("/editors")
public class EditorController {
    
    @Autowired
    EditorService editorService;
    
    /**
     * Get all editors by page
     * @param pageNumber number of the required page - 0 based count
     * @return required page
     */
    @GetMapping("")
    public Page<Editor> getAllEditors(@RequestParam(name = "pageNumber", required = true) final Integer pageNumber){
        return editorService.getAllEditors(pageNumber);
    }
    
    /**
     * Get all editors with a matching name
     * @param name name of the editor(s) to find
     * @return a list of matching editors or an empty list
     */
    @GetMapping("byName")
    public List<Editor> getEditorsByName(@RequestParam(name = "name") final String name){
        return editorService.getEditorsByName(name);
    }
    
    /**
     * Get the editor with the matching id
     * @param id id of the editor to get
     * @return the retrieved editor or ??
     */
    @GetMapping("byId")
    public Editor getEditorById(@RequestParam(name = "id") final UUID id) {
        return editorService.getEditorById(id);
    }
    
    /**
     * Save the editor in the database
     * @param editor a valid editor
     * @return the created editor or ??
     */
    @PostMapping("create")
    public Editor createEditor(@RequestBody @Valid final Editor editor) {
        return editorService.createEditor(editor);
    }
    
    /**
     * Save the list of valid editors
     * @param editors all editors to create
     * @return the created editors or ??
     */
    @PostMapping("createAll")
    public List<Editor> createAll(@RequestBody @Valid final List<Editor> editors) {
        return editorService.createAll(editors);
    }
    
    /**
     * Update the editor
     * @param editor editor to update
     * @return the updated editor
     */
    @PutMapping("update")
    public Editor updateEditor(@RequestBody @Valid final Editor editor) {
        return editorService.updateEditor(editor);
    }
    
    /**
     * Delete the editor
     * @param id id of the editor to delete
     * @return true is the editor is deleted or false otherwise
     */
    @DeleteMapping("delete")
    public boolean deleteEditor(@RequestParam(name = "id") final UUID id) {
        return editorService.deleteEditor(id);
    }
}
