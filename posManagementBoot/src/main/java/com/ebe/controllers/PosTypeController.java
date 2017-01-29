package com.ebe.controllers;

import com.ebe.SearchSpecifications.GenericSpecification;
import com.ebe.SearchSpecifications.GenericSpecificationsBuilder;
import com.ebe.entities.PosTypeEntity;
import com.ebe.repositories.PosTypeRepository;
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
@RequestMapping("services/postype")
@Transactional
public class PosTypeController {
    @Autowired
    private PosTypeRepository posTypeRepository;

    /**
     * Get all pos Types
     * @param name optional search by name
     * @return All pos Types list
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PosTypeEntity>> findAll(@RequestParam(value = "name", defaultValue = "") final String name) {
        List<PosTypeEntity> posTypes;
        if (name.isEmpty()) {
            posTypes = this.posTypeRepository.findAll();
        }else{
            posTypes = this.posTypeRepository.findByNameIgnoreCaseContaining(name);
        }
        if (posTypes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posTypes, HttpStatus.OK);

    }

    /**
     * Get all pos Types by page
     * @param page page number
     * @param size number of buttonSheetItems per page
     * @param search filtration string
     * @return posTypes page
     */
    @RequestMapping(
            value = "/get",
            params = { "page", "size", "search"},
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public ResponseEntity<Page<PosTypeEntity>> findPaginated(@RequestParam("page") int page, @RequestParam("size") int size
            , @RequestParam("search") String search) {
        GenericSpecificationsBuilder<PosTypeEntity> builder = new GenericSpecificationsBuilder();
        Pattern pattern = Pattern.compile(GenericSpecification.SpecificationsPattern);
        Matcher matcher = pattern.matcher(search + ","); //all searches must contain a comma
        while (matcher.find()) {
            builder.with(matcher.group(GenericSpecification.fieldNameIndex),
                    matcher.group(GenericSpecification.operatorIndex), matcher.group(GenericSpecification.valueIndex).trim());
        }
        Specification<PosTypeEntity> spec = builder.build();
        Page<PosTypeEntity> projectsPage = this.posTypeRepository.findAll(spec, new PageRequest(page-1, size));
        if (page > projectsPage.getTotalPages()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projectsPage, HttpStatus.OK);
    }

    /**
     * Find a project with id = @posTypeId
     *
     * @param posTypeId The project id to look for
     * @return The project with id = @posTypeId
     */
    @RequestMapping(
            value = "/{posTypeId}",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public ResponseEntity<PosTypeEntity> findOne(@PathVariable("posTypeId") Integer posTypeId) {
        PosTypeEntity project = this.posTypeRepository.findOne(posTypeId);
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
    public ResponseEntity<PosTypeEntity> create(@RequestBody PosTypeEntity newRecord, UriComponentsBuilder ucBuilder) {
        this.posTypeRepository.saveAndFlush(newRecord);
        //HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(ucBuilder.path("/newRecord/{posTypeId}").buildAndExpand(newRecord.getRegionId()).toUri());
        return new ResponseEntity<>(newRecord, HttpStatus.CREATED);
    }

    /**
     * Update a project
     * @param posTypeId The ID of the project to be updated
     * @param updatedRecord The new project name
     * @return The updated project
     */
    @RequestMapping(
            value = "/{posTypeId}",
            method = RequestMethod.PUT,
            produces = {"application/json"}
    )
    public ResponseEntity<PosTypeEntity> update(@PathVariable("posTypeId") Integer posTypeId, @RequestBody PosTypeEntity updatedRecord){
        PosTypeEntity posTypeEntity = this.posTypeRepository.findOne(posTypeId);
        if(posTypeEntity == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        posTypeEntity.setName(updatedRecord.getName());
        this.posTypeRepository.saveAndFlush(posTypeEntity);
        return new ResponseEntity<>(posTypeEntity, HttpStatus.OK);
    }

    /**
     * Delete a project by project ID
     * @param posTypeId The ID of the project to be deleted
     * @return HTTP NO_CONTENT
     */
    @RequestMapping(
            value="/{posTypeId}",
            method = RequestMethod.DELETE,
            produces = {"application/json"}
    )
    public ResponseEntity<Void> deleteOne(@PathVariable("posTypeId") Integer posTypeId){
        if(!this.posTypeRepository.exists(posTypeId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.posTypeRepository.delete(posTypeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
