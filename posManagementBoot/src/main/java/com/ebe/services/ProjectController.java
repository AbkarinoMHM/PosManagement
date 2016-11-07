package com.ebe.services;

import com.ebe.entities.ProjectEntity;
import com.ebe.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Created by saado on 10/24/2016.
 */
@RestController
@RequestMapping("services/project")
@Transactional
public class ProjectController {
    @Autowired
    private ProjectRepository projectRepository;

    /**
     * Get all projects
     *
     * @return All projects list
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ProjectEntity>> findAll() {
        List<ProjectEntity> projects = this.projectRepository.findAll();
        if (projects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(projects, HttpStatus.OK);

    }

    /**
     * Find a project with id = @projectId
     *
     * @param projectId The project id to look for
     * @return The project with id = @projectId
     */
    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public ResponseEntity<ProjectEntity> findOne(@PathVariable("projectId") Long projectId) {
        ProjectEntity project = this.projectRepository.findOne(projectId);
        if (project == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    /**
     * Create a new project
     * @param project The project to be created
     * @param ucBuilder
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ProjectEntity> create(@RequestBody ProjectEntity project, UriComponentsBuilder ucBuilder) {
        this.projectRepository.saveAndFlush(project);
        //HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(ucBuilder.path("/project/{projectId}").buildAndExpand(project.getRegionId()).toUri());
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    /**
     * Update a project
     * @param projectId The ID of the project to be updated
     * @param updatedRegion The new project name
     * @return The updated project
     */
    @RequestMapping(value = "/{projectId}", method = RequestMethod.PUT)
    public ResponseEntity<ProjectEntity> update(@PathVariable("projectId") Long projectId, @RequestBody ProjectEntity updatedRegion){
        ProjectEntity projectEntity = this.projectRepository.findOne(projectId);
        if(projectEntity == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        projectEntity.setProjectName(updatedRegion.getProjectName());
        this.projectRepository.saveAndFlush(projectEntity);
        return new ResponseEntity<>(projectEntity, HttpStatus.OK);
    }

    /**
     * Delete a project by project ID
     * @param projectId The ID of the project to be deleted
     * @return HTTP NO_CONTENT
     */
    @RequestMapping(value="/{projectId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteOne(@PathVariable("projectId") Long projectId){
        if(!this.projectRepository.exists(projectId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.projectRepository.delete(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
