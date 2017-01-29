package com.ebe.controllers;

import com.ebe.SearchSpecifications.GenericSpecification;
import com.ebe.SearchSpecifications.GenericSpecificationsBuilder;
import com.ebe.entities.PosVendorEntity;
import com.ebe.repositories.PosVendorRepository;
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
@RequestMapping("services/posvendor")
@Transactional
public class PosVendorController {
    @Autowired
    private PosVendorRepository posVendorRepository;

    /**
     * Get all pos vendors
     * @param name optional search by name
     * @return All pos vendors list
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PosVendorEntity>> findAll(@RequestParam(value = "name", defaultValue = "") final String name) {
        List<PosVendorEntity> posTypes;
        if(name.isEmpty()){
            posTypes = this.posVendorRepository.findAll();
        }else{
            posTypes = this.posVendorRepository.findByNameIgnoreCaseContaining(name);
        }
        if (posTypes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posTypes, HttpStatus.OK);

    }

    /**
     * Get all pos vendors by page
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
    public ResponseEntity<Page<PosVendorEntity>> findPaginated(@RequestParam("page") int page, @RequestParam("size") int size
            , @RequestParam("search") String search) {
        GenericSpecificationsBuilder<PosVendorEntity> builder = new GenericSpecificationsBuilder();
        Pattern pattern = Pattern.compile(GenericSpecification.SpecificationsPattern);
        Matcher matcher = pattern.matcher(search + ","); //all searches must contain a comma
        while (matcher.find()) {
            builder.with(matcher.group(GenericSpecification.fieldNameIndex),
                    matcher.group(GenericSpecification.operatorIndex), matcher.group(GenericSpecification.valueIndex).trim());
        }
        Specification<PosVendorEntity> spec = builder.build();
        Page<PosVendorEntity> projectsPage = this.posVendorRepository.findAll(spec, new PageRequest(page-1, size));
        if (page > projectsPage.getTotalPages()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projectsPage, HttpStatus.OK);
    }

    /**
     * Find a pos vendor with id = @posVendorId
     *
     * @param posVendorId The pos vendor id to look for
     * @return The pos vendor with id = @posVendorId
     */
    @RequestMapping(
            value = "/{posVendorId}",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public ResponseEntity<PosVendorEntity> findOne(@PathVariable("posVendorId") Integer posVendorId) {
        PosVendorEntity posVendorEntityendor = this.posVendorRepository.findOne(posVendorId);
        if (posVendorEntityendor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(posVendorEntityendor, HttpStatus.OK);
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
    public ResponseEntity<PosVendorEntity> create(@RequestBody PosVendorEntity newRecord, UriComponentsBuilder ucBuilder) {
        this.posVendorRepository.saveAndFlush(newRecord);
        //HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(ucBuilder.path("/newRecord/{posVendorId}").buildAndExpand(newRecord.getRegionId()).toUri());
        return new ResponseEntity<>(newRecord, HttpStatus.CREATED);
    }

    /**
     * Update a pos vendor
     * @param posVendorId The ID of the pos vendor to be updated
     * @param updatedRecord The new pos vendor name
     * @return The updated pos vendor
     */
    @RequestMapping(
            value = "/{posVendorId}",
            method = RequestMethod.PUT,
            produces = {"application/json"}
    )
    public ResponseEntity<PosVendorEntity> update(@PathVariable("posVendorId") Integer posVendorId, @RequestBody PosVendorEntity updatedRecord){
        PosVendorEntity posVendorEntity = this.posVendorRepository.findOne(posVendorId);
        if(posVendorEntity == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        posVendorEntity.setName(updatedRecord.getName());
        this.posVendorRepository.saveAndFlush(posVendorEntity);
        return new ResponseEntity<>(posVendorEntity, HttpStatus.OK);
    }

    /**
     * Delete a pos vendor by pos vendor ID
     * @param posVendorId The ID of the pos vendor to be deleted
     * @return HTTP NO_CONTENT
     */
    @RequestMapping(
            value="/{posVendorId}",
            method = RequestMethod.DELETE,
            produces = {"application/json"}
    )
    public ResponseEntity<Void> deleteOne(@PathVariable("posVendorId") Integer posVendorId){
        if(!this.posVendorRepository.exists(posVendorId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.posVendorRepository.delete(posVendorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
