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

import com.aymeric.gamestore.entity.Editor;
import com.aymeric.gamestore.exception.GamestoreEntityException;
import com.aymeric.gamestore.repository.EditorRepository;

@Service
public class EditorService {
    
    /** Number of user return per page. */
    private static final int NUM_OF_USER_PER_PAGE = 50;

    @Autowired
    EditorRepository editorRepository;
    
    /**
     * Get all editors by page of 50 result each and in name alphabetical order
     * @param pageNumber number of the required page
     * @return required page
     */
    public Page<Editor> getAllEditors(final Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, NUM_OF_USER_PER_PAGE, Sort.by("name"));
        
        return editorRepository.findAll(pageable);
    }
    
    /**
     * Get all editors with a matching name
     * @param name name of the editor(s) to find
     * @return a list of matching editors or an empty list
     */
    public List<Editor> getEditorsByName(final String name) {
        return editorRepository.findAllByName(name);
    }
    
    public boolean editorExistById(final UUID id) {
        return editorRepository.existsById(id);
    }
    
    /**
     * Get the editor with the matching id
     * @param id id of the editor to get
     * @return the retrieved editor or ??
     */
    public Editor getEditorById(final UUID id) {
        Optional<Editor> editorOpt =  editorRepository.findById(id);
        
        if(!editorOpt.isPresent()) {
            StringBuilder message = new StringBuilder();
            message.append("No Editor found with this id: ");
            message.append(id);
            System.err.println(message);
            throw new GamestoreEntityException(message.toString());
        }
        
        return editorOpt.get();
    }
    
    /**
     * Save the editor in the database
     * @param editor a valid editor
     * @return the created editor or ??
     */
    public Editor createEditor(final Editor editor) {
        return editorRepository.save(editor);
    }
    
    /**
     * Save the list of valid editors
     * @param editors all editors to create
     * @return the created editors or ??
     */
    public List<Editor> createAll(final List<Editor> editors) {
        return (List<Editor>) editorRepository.saveAll(editors);
    }
    
    /**
     * Update the editor
     * @param editor editor to update
     * @return the updated editor
     */
    public Editor updateEditor(final Editor editor) {
        return null;
    }
    
    /**
     * Delete the editor
     * @param id id of the editor to delete
     * @return true is the editor is deleted or false otherwise
     */
    public boolean deleteEditor(final UUID id) {
        boolean isEditorDeleted = false;
        boolean isEditorExists = editorRepository.existsById(id);
        
        if(isEditorExists) {
            editorRepository.deleteById(id);
            isEditorDeleted = true;
        }
        
        return isEditorDeleted;
    }
    
    
}
