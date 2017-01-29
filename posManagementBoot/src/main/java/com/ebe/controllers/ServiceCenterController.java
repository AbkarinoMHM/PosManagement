package com.ebe.controllers;

import com.ebe.SearchSpecifications.GenericSpecification;
import com.ebe.SearchSpecifications.GenericSpecificationsBuilder;
import com.ebe.entities.ServiceCenterEntity;
import com.ebe.repositories.ServiceCenterRepository;
import com.ebe.repositories.VendorBranchRepository;
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
@RequestMapping("services/servicecenter")
@Transactional
public class ServiceCenterController {
    @Autowired
    private ServiceCenterRepository repository;

    /**
     * Get all service centers
     *
     * @return All service centers list
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ServiceCenterEntity>> findAll() {
        List<ServiceCenterEntity> serviceCenters = this.repository.findAll();
        if (serviceCenters.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(serviceCenters, HttpStatus.OK);

    }

    /**
     * Get all service centers by page
     * @param page page number
     * @param size number of buttonSheetItems per page
     * @param search filtration string
     * @return service centers page
     */
    @RequestMapping(
            value = "/get",
            params = { "page", "size", "search"},
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public ResponseEntity<Page<ServiceCenterEntity>> findPaginated(@RequestParam("page") int page, @RequestParam("size") int size
            , @RequestParam("search") String search) {
        GenericSpecificationsBuilder<ServiceCenterEntity> builder = new GenericSpecificationsBuilder();
        Pattern pattern = Pattern.compile(GenericSpecification.SpecificationsPattern);
        Matcher matcher = pattern.matcher(search.trim() + ","); //all searches must contain a comma
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(3), matcher.group(6).trim());
        }
        Specification<ServiceCenterEntity> spec = builder.build();
        Page<ServiceCenterEntity> branchesPage = this.repository.findAll(spec, new PageRequest(page-1, size));
        if (page > branchesPage.getTotalPages()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(branchesPage, HttpStatus.OK);
    }

    /**
     * Find a branch with id = @serviceCenterId
     *
     * @param serviceCenterId The branch id to look for
     * @return The branch with id = @serviceCenterId
     */
    @RequestMapping(
            value = "/{serviceCenterId}",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public ResponseEntity<ServiceCenterEntity> findOne(@PathVariable("serviceCenterId") Long serviceCenterId) {
        ServiceCenterEntity branch = this.repository.findOne(serviceCenterId);
        if (branch == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(branch, HttpStatus.OK);
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
    public ResponseEntity<ServiceCenterEntity> create(@RequestBody ServiceCenterEntity newRecord, UriComponentsBuilder ucBuilder) {
        this.repository.saveAndFlush(newRecord);
        ServiceCenterEntity branchEntity = this.repository.findOne(newRecord.getId());
        return new ResponseEntity<>(branchEntity, HttpStatus.CREATED);
    }

    /**
     * Update an branch
     * @param serviceCenterId The ID of the branch to be updated
     * @param updatedRecord The updated branch
     * @return The updated branch
     */
    @RequestMapping(
            value = "/{serviceCenterId}",
            method = RequestMethod.PUT,
            produces = {"application/json"}
    )
    public ResponseEntity<ServiceCenterEntity> update(@PathVariable("serviceCenterId") Long serviceCenterId, @RequestBody ServiceCenterEntity updatedRecord){
        ServiceCenterEntity branchEntity = this.repository.findOne(serviceCenterId);
        if(branchEntity == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        branchEntity.setName(updatedRecord.getName());
        branchEntity.setAddress(updatedRecord.getAddress());
        branchEntity.setTel(updatedRecord.getTel());
        branchEntity.setArea(updatedRecord.getArea());
        this.repository.saveAndFlush(branchEntity);
        return new ResponseEntity<>(branchEntity, HttpStatus.OK);
    }

    /**
     * Delete a branch by branch ID
     * @param serviceCenterId The ID of the branch to be deleted
     * @return HTTP NO_CONTENT
     */
    @RequestMapping(
            value="/{serviceCenterId}",
            method = RequestMethod.DELETE,
            produces = {"application/json"}
    )
    public ResponseEntity<Void> deleteOne(@PathVariable("serviceCenterId") Long serviceCenterId){
        if(!this.repository.exists(serviceCenterId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.repository.delete(serviceCenterId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
