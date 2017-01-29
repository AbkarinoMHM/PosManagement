package com.ebe.controllers;

import com.ebe.SearchSpecifications.GenericSpecification;
import com.ebe.SearchSpecifications.GenericSpecificationsBuilder;
import com.ebe.entities.TechnicianEntity;
import com.ebe.repositories.TechnicianRepository;
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
@RequestMapping("services/technician")
@Transactional
public class TechnicianController {
    @Autowired
    private TechnicianRepository technicianRepository;

    /**
     * Get all technicians
     *
     * @return All technicians list
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<TechnicianEntity>> findAll() {
        List<TechnicianEntity> technicians = this.technicianRepository.findAll();
        if (technicians.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(technicians, HttpStatus.OK);

    }

    /**
     * Get all technicians by page
     * @param page page number
     * @param size number of buttonSheetItems per page
     * @param search filtration string
     * @return technicians page
     */
    @RequestMapping(
            value = "/get",
            params = { "page", "size", "search"},
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public ResponseEntity<Page<TechnicianEntity>> findPaginated(@RequestParam("page") int page, @RequestParam("size") int size
            , @RequestParam("search") String search) {
        GenericSpecificationsBuilder<TechnicianEntity> builder = new GenericSpecificationsBuilder();
        Pattern pattern = Pattern.compile(GenericSpecification.SpecificationsPattern);
        Matcher matcher = pattern.matcher(search + ","); //all searches must contain a comma
        while (matcher.find()) {
            builder.with(matcher.group(GenericSpecification.fieldNameIndex),
                    matcher.group(GenericSpecification.operatorIndex), matcher.group(GenericSpecification.valueIndex).trim());
        }
        Specification<TechnicianEntity> spec = builder.build();
        Page<TechnicianEntity> projectsPage = this.technicianRepository.findAll(spec, new PageRequest(page-1, size));
        if (page > projectsPage.getTotalPages()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projectsPage, HttpStatus.OK);
    }

    /**
     * Find a technician with id = @technicianId
     *
     * @param technicianId The technician id to look for
     * @return The technician with id = @technicianId
     */
    @RequestMapping(
            value = "/{technicianId}",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public ResponseEntity<TechnicianEntity> findOne(@PathVariable("technicianId") Long technicianId) {
        TechnicianEntity technician = this.technicianRepository.findOne(technicianId);
        if (technician == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(technician, HttpStatus.OK);
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
    public ResponseEntity<TechnicianEntity> create(@RequestBody TechnicianEntity newRecord, UriComponentsBuilder ucBuilder) {
        this.technicianRepository.saveAndFlush(newRecord);
        //HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(ucBuilder.path("/newRecord/{technicianId}").buildAndExpand(newRecord.getRegionId()).toUri());
        return new ResponseEntity<>(newRecord, HttpStatus.CREATED);
    }

    /**
     * Update a technician
     * @param technicianId The ID of the technician to be updated
     * @param updatedRecord The new technician name
     * @return The updated technician
     */
    @RequestMapping(
            value = "/{technicianId}",
            method = RequestMethod.PUT,
            produces = {"application/json"}
    )
    public ResponseEntity<TechnicianEntity> update(@PathVariable("technicianId") Long technicianId, @RequestBody TechnicianEntity updatedRecord){
        TechnicianEntity technicianEntity = this.technicianRepository.findOne(technicianId);
        if(technicianEntity == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        technicianEntity.setName(updatedRecord.getName());
        technicianEntity.setTitle(updatedRecord.getTitle());
        technicianEntity.setRate(updatedRecord.getRate());
        technicianEntity.setMobile(updatedRecord.getMobile());
        technicianEntity.setAddress(updatedRecord.getAddress());
        technicianEntity.setTel(updatedRecord.getTel());
        this.technicianRepository.saveAndFlush(technicianEntity);
        return new ResponseEntity<>(technicianEntity, HttpStatus.OK);
    }

    /**
     * Delete a technician by technician ID
     * @param technicianId The ID of the technician to be deleted
     * @return HTTP NO_CONTENT
     */
    @RequestMapping(
            value="/{technicianId}",
            method = RequestMethod.DELETE,
            produces = {"application/json"}
    )
    public ResponseEntity<Void> deleteOne(@PathVariable("technicianId") Long technicianId){
        if(!this.technicianRepository.exists(technicianId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.technicianRepository.delete(technicianId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
