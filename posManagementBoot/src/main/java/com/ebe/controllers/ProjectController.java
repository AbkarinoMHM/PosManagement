package com.ebe.controllers;

import com.ebe.SearchSpecifications.GenericSpecification;
import com.ebe.SearchSpecifications.GenericSpecificationsBuilder;
import com.ebe.entities.ProjectEntity;
import com.ebe.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * @param name optional search by name
     * @return All projects list
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ProjectEntity>> findAll(@RequestParam(value = "name", defaultValue = "") final String name) {
        List<ProjectEntity> projects;
        if(name.isEmpty()){
            projects = this.projectRepository.findAll();
        } else{
            projects = this.projectRepository.findByNameIgnoreCaseContaining(name);
        }
        if (projects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(projects, HttpStatus.OK);

    }

    /**
     * Get all projects by page
     * @param page page number
     * @param size number of buttonSheetItems per page
     * @param search filtration string
     * @return projects page
     */
    @RequestMapping(
            value = "/get",
            params = { "page", "size", "search"},
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public ResponseEntity<Page<ProjectEntity>> findPaginated(@RequestParam("page") int page, @RequestParam("size") int size
            , @RequestParam("search") String search) {
        GenericSpecificationsBuilder<ProjectEntity> builder = new GenericSpecificationsBuilder();
        Pattern pattern = Pattern.compile(GenericSpecification.SpecificationsPattern);
        Matcher matcher = pattern.matcher(search + ","); //all searches must contain a comma
        while (matcher.find()) {
            builder.with(matcher.group(GenericSpecification.fieldNameIndex),
                    matcher.group(GenericSpecification.operatorIndex), matcher.group(GenericSpecification.valueIndex).trim());
        }
        Specification<ProjectEntity> spec = builder.build();
        Page<ProjectEntity> projectsPage = this.projectRepository.findAll(spec, new PageRequest(page-1, size));
        if (page > projectsPage.getTotalPages()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projectsPage, HttpStatus.OK);
    }

    /**
     * Find a project with id = @projectId
     *
     * @param projectId The project id to look for
     * @return The project with id = @projectId
     */
    @RequestMapping(
            value = "/{projectId}",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public ResponseEntity<ProjectEntity> findOne(@PathVariable("projectId") Long projectId) {
        ProjectEntity project = this.projectRepository.findOne(projectId);
        if (project == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    /**
     * Create a new newRecord
     * @param newRecord The newRecord to be created
     * @param ucBuilder
     * @return
     */
    @RequestMapping(
            method = RequestMethod.POST,
            produces = {"application/json"}
    )
    public ResponseEntity<ProjectEntity> create(@RequestBody ProjectEntity newRecord, UriComponentsBuilder ucBuilder) {
        this.projectRepository.saveAndFlush(newRecord);
        //HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(ucBuilder.path("/newRecord/{projectId}").buildAndExpand(newRecord.getRegionId()).toUri());
        return new ResponseEntity<>(newRecord, HttpStatus.CREATED);
    }

    /**
     * Update a project
     * @param projectId The ID of the project to be updated
     * @param updatedRecord The new project name
     * @return The updated project
     */
    @RequestMapping(
            value = "/{projectId}",
            method = RequestMethod.PUT,
            produces = {"application/json"}
    )
    public ResponseEntity<ProjectEntity> update(@PathVariable("projectId") Long projectId, @RequestBody ProjectEntity updatedRecord){
        ProjectEntity projectEntity = this.projectRepository.findOne(projectId);
        if(projectEntity == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        projectEntity.setName(updatedRecord.getName());
        this.projectRepository.saveAndFlush(projectEntity);
        return new ResponseEntity<>(projectEntity, HttpStatus.OK);
    }

    /**
     * Delete a project by project ID
     * @param projectId The ID of the project to be deleted
     * @return HTTP NO_CONTENT
     */
    @RequestMapping(
            value="/{projectId}",
            method = RequestMethod.DELETE,
            produces = {"application/json"}
    )
    public ResponseEntity<Void> deleteOne(@PathVariable("projectId") Long projectId){
        if(!this.projectRepository.exists(projectId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.projectRepository.delete(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
