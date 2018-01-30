package com.ebe.controllers;

import com.ebe.SearchSpecifications.GenericSpecification;
import com.ebe.SearchSpecifications.GenericSpecificationsBuilder;
import com.ebe.entities.SimEntity;
import com.ebe.repositories.SimRepository;
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

@RestController
@RequestMapping("services/sim")
@Transactional
public class SimController {
    @Autowired
    private SimRepository repository;

    /**
     * Get all Sims
     * @param search optional search criteria (ex: simId=1)
     * @return All Sims list
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SimEntity>> findAll(@RequestParam(value="search", defaultValue = "") String search) {
        List<SimEntity> posList = null;
        if(search.isEmpty()) {
            posList =this.repository.findAll();
        }else{
            GenericSpecificationsBuilder<SimEntity> builder = new GenericSpecificationsBuilder();
            Pattern pattern = Pattern.compile(GenericSpecification.SpecificationsPattern);
            Matcher matcher = pattern.matcher(search.trim() + ","); //all searches must contain a comma
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(3), matcher.group(6).trim());
            }
            Specification<SimEntity> spec = builder.build();
            posList =this.repository.findAll(spec);
        }

        if (posList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posList, HttpStatus.OK);
    }

    /**
     * Get all sims by page
     * @param page page number
     * @param size page size
     * @param search optional search criteria (ex: simId=1)
     * @return
     */
    @RequestMapping(
            value = "/get",
            params = { "page", "size", "search"},
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public ResponseEntity<Page<SimEntity>> findPaginated(@RequestParam("page") int page, @RequestParam("size") int size
            , @RequestParam("search") String search) {
        GenericSpecificationsBuilder<SimEntity> builder = new GenericSpecificationsBuilder();
        Pattern pattern = Pattern.compile(GenericSpecification.SpecificationsPattern);
        Matcher matcher = pattern.matcher(search.trim() + ","); //all searches must contain a comma
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(3), matcher.group(6).trim());
        }
        Specification<SimEntity> spec = builder.build();
        Page<SimEntity> branchesPage = this.repository.findAll(spec, new PageRequest(page-1, size));
        if (page > branchesPage.getTotalPages()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(branchesPage, HttpStatus.OK);
    }

    /**
     * Find sim by Id
     * @param id Sim iderw6
     * @return
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public ResponseEntity<SimEntity> findOne(@PathVariable("id") Long id) {
        SimEntity branch = this.repository.findOne(id);
        if (branch == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(branch, HttpStatus.OK);
    }

    /**
     * Create a new sim
     * @param newRecord new sim data
     * @param ucBuilder
     * @return
     */
    @RequestMapping(
            method = RequestMethod.POST,
            produces = {"application/json"}
    )
    public ResponseEntity<SimEntity> create(@RequestBody SimEntity newRecord, UriComponentsBuilder ucBuilder) {
        this.repository.saveAndFlush(newRecord);
        SimEntity SimEntity = this.repository.findOne(newRecord.getSimId());
        return new ResponseEntity<>(SimEntity, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            produces = {"application/json"}
    )
    public ResponseEntity<SimEntity> update(@PathVariable("id") Long id, @RequestBody SimEntity updatedRecord){
        SimEntity SimEntity = this.repository.findOne(id);
        if(SimEntity == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        SimEntity.setSimNumber(updatedRecord.getSimNumber());
        SimEntity.setSimVendorId(updatedRecord.getSimVendorId());
        SimEntity.setVendorBranchId(updatedRecord.getVendorBranchId());
        SimEntity.setIsDamage(updatedRecord.getIsDamage());
        this.repository.saveAndFlush(SimEntity);
        return new ResponseEntity<>(SimEntity, HttpStatus.OK);
    }

    @RequestMapping(
            value="/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json"}
    )
    public ResponseEntity<Void> deleteOne(@PathVariable("id") Long id){
        if(!this.repository.exists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.repository.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
