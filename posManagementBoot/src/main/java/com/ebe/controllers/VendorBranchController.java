package com.ebe.controllers;

import com.ebe.SearchSpecifications.GenericSpecification;
import com.ebe.SearchSpecifications.GenericSpecificationsBuilder;
import com.ebe.entities.VendorBranchEntity;
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
@RequestMapping("services/vendorbranch")
@Transactional
public class VendorBranchController {
    @Autowired
    private VendorBranchRepository repository;

    /**
     * Get all branches
     *
     * @param name optional search by name
     * @return All branches list
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<VendorBranchEntity>> findAll(@RequestParam(value = "name", defaultValue = "") final String name) {
        List<VendorBranchEntity> branches;
        if (name.isEmpty()) {
            branches = this.repository.findAll();
        }else{
            branches = this.repository.findByNameIgnoreCaseContaining(name);
        }
        if (branches.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(branches, HttpStatus.OK);

    }

    /**
     * Get all branches by page
     *
     * @param page   page number
     * @param size   number of buttonSheetItems per page
     * @param search filtration string
     * @return branches page
     */
    @RequestMapping(
            value = "/get",
            params = {"page", "size", "search"},
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public ResponseEntity<Page<VendorBranchEntity>> findPaginated(@RequestParam("page") int page, @RequestParam("size") int size
            , @RequestParam("search") String search) {
        GenericSpecificationsBuilder<VendorBranchEntity> builder = new GenericSpecificationsBuilder();
        Pattern pattern = Pattern.compile(GenericSpecification.SpecificationsPattern);
        Matcher matcher = pattern.matcher(search.trim() + ","); //all searches must contain a comma
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(3), matcher.group(6).trim());
        }
        Specification<VendorBranchEntity> spec = builder.build();
        Page<VendorBranchEntity> branchesPage = this.repository.findAll(spec, new PageRequest(page - 1, size));
        if (page > branchesPage.getTotalPages()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(branchesPage, HttpStatus.OK);
    }

    /**
     * Find a branch with id = @branchId
     *
     * @param branchId The branch id to look for
     * @return The branch with id = @branchId
     */
    @RequestMapping(
            value = "/{branchId}",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public ResponseEntity<VendorBranchEntity> findOne(@PathVariable("branchId") Long branchId) {
        VendorBranchEntity branch = this.repository.findOne(branchId);
        if (branch == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(branch, HttpStatus.OK);
    }

    /**
     * Create a new newRecord
     *
     * @param newRecord The newRecord to be created
     * @param ucBuilder
     * @return
     */
    @RequestMapping(
            method = RequestMethod.POST,
            produces = {"application/json"}
    )
    public ResponseEntity<VendorBranchEntity> create(@RequestBody VendorBranchEntity newRecord, UriComponentsBuilder ucBuilder) {
        this.repository.saveAndFlush(newRecord);
        VendorBranchEntity branchEntity = this.repository.findOne(newRecord.getId());
        return new ResponseEntity<>(branchEntity, HttpStatus.CREATED);
    }

    /**
     * Update an branch
     *
     * @param branchId      The ID of the branch to be updated
     * @param updatedRecord The updated branch
     * @return The updated branch
     */
    @RequestMapping(
            value = "/{branchId}",
            method = RequestMethod.PUT,
            produces = {"application/json"}
    )
    public ResponseEntity<VendorBranchEntity> update(@PathVariable("branchId") Long branchId, @RequestBody VendorBranchEntity updatedRecord) {
        VendorBranchEntity branchEntity = this.repository.findOne(branchId);
        if (branchEntity == null) {
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
     *
     * @param branchId The ID of the branch to be deleted
     * @return HTTP NO_CONTENT
     */
    @RequestMapping(
            value = "/{branchId}",
            method = RequestMethod.DELETE,
            produces = {"application/json"}
    )
    public ResponseEntity<Void> deleteOne(@PathVariable("branchId") Long branchId) {
        if (!this.repository.exists(branchId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.repository.delete(branchId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
