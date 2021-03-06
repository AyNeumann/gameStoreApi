package com.aymeric.gamestore.controller;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aymeric.gamestore.dto.EditorDTO;
import com.aymeric.gamestore.entity.Editor;
import com.aymeric.gamestore.service.EditorService;

@RestController
@RequestMapping("/editors")
public class EditorController {
    
    @Autowired
    EditorService editorService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    /**
     * Get all editors by page - TEST OK
     * @param pageNumber number of the required page - 0 based count
     * @return required page
     */
    @GetMapping(value = "/{pageNumber}")
    public Page<EditorDTO> getAllEditors(@PathVariable("pageNumber") final Integer pageNumber) {
        return convertToDTOPage(editorService.getAllEditors(pageNumber));
    }
    
    /**
     * Get all editors with a matching name - TEST OK
     * @param name name of the editor(s) to find
     * @return a list of matching editors or an empty list
     */
    @GetMapping("")
    public List<EditorDTO> getEditorsByName(
            @RequestParam(name = "name") final String name,
            @RequestParam(name = "search-mode", required = false) final String searchMode
            ){
        return convertToDTOList(editorService.getEditorsByName(name, searchMode));
    }
    
    /**
     * Get the editor with the matching id - TEST OK
     * @param id id of the editor to get
     * @return the retrieved editor or ??
     */
    @GetMapping(value = "id/{id}")
    public EditorDTO getEditorById(@PathVariable("id") final UUID id) {
        return convertToDto(editorService.getEditorById(id));
    }
    
    /**
     * Save the list of valid editors - TEST OK
     * @param editors all editors to create
     * @return the created editors or ??
     */
    @PostMapping("")
    public List<EditorDTO> createEditor(@RequestBody @Valid final List<EditorDTO> editors) {
        
        List<Editor> createdEditors = convertToEntityList(editors);
        
        return convertToDTOList(editorService.createAll(createdEditors));
    }
        
    /**
     * Update the editor
     * @param editor editor to update
     * @return the updated editor
     */
    @PutMapping("/{id}")
    public EditorDTO updateEditor(@PathVariable("id") final UUID id, @RequestBody @Valid final EditorDTO editorDTO) {
        
        Editor updatedEditor = convertToEntity(editorDTO);
        
        return convertToDto(editorService.updateEditor(updatedEditor));
    }
        
    /**
     * Delete the editor
     * @param id id of the editor to delete
     * @return true is the editor is deleted or false otherwise
     */
    @DeleteMapping("")
    public boolean deleteEditor(@PathVariable("id") final UUID id) {
        return editorService.deleteEditor(id);
    }
    
    
    
    /**
     * Convert Editor Entity to EditorDTO class
     * @param editor Editor Entity to convert
     * @return an EditorDTO
     */
    private EditorDTO convertToDto(Editor editor) {
        return modelMapper.map(editor, EditorDTO.class);
    }
    
    /**
     * Convert EditorDTO class to a Editor Entity
     * @param editorDTO EditorDTO to convert
     * @return a Editor Entity
     */
    private Editor convertToEntity(EditorDTO editorDTO) {
        return modelMapper.map(editorDTO, Editor.class);
    }
    
    /**
     * Convert Editor Entities page to EditorDTO page
     * @param editors page of Editor Entities to convert
     * @return a Page of EditorDTO
     */
    private Page<EditorDTO> convertToDTOPage(Page<Editor> editors) {
        Type pageType = new TypeToken<Page<EditorDTO>>() {}.getType();
        
        return new ModelMapper().map(editors, pageType);
    }
    
    /**
     * Convert a list of Editor Entities to a list of EditorDTO
     * @param editors list of Editor Entities to convert
     * @return a List of EditorDTO
     */
    private List<EditorDTO> convertToDTOList(List<Editor> editors) {
        Type pageType = new TypeToken<List<EditorDTO>>() {}.getType();
        
        return new ModelMapper().map(editors, pageType);
    }
    
    /**
     * Convert a list of EditorDTO to a list of Editor Entities
     * @param editors list of EditorDTO to convert
     * @return a List of GameEntities
     */
    private List<Editor> convertToEntityList(List<EditorDTO> editors) {
        Type pageType = new TypeToken<List<Editor>>() {}.getType();
        
        return new ModelMapper().map(editors, pageType);
    }
}
