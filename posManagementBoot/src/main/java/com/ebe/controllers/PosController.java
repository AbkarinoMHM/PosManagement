package com.ebe.controllers;

import com.ebe.SearchSpecifications.GenericSpecification;
import com.ebe.SearchSpecifications.GenericSpecificationsBuilder;
import com.ebe.entities.PosEntity;
import com.ebe.entities.PosStatusEntity;
import com.ebe.repositories.MerchantBranchRepository;
import com.ebe.repositories.PosRepository;
import com.ebe.repositories.PosStatusRepository;
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
@RequestMapping("services/pos")
@Transactional
public class PosController {
    @Autowired
    private PosRepository repository;
    @Autowired
    private PosStatusRepository posStatusRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PosEntity>> findAll(@RequestParam(value="search", defaultValue = "") String search) {
        List<PosEntity> posList = null;
        if(search.isEmpty()) {
            posList =this.repository.findAll();
        }else{
            GenericSpecificationsBuilder<PosEntity> builder = new GenericSpecificationsBuilder();
            Pattern pattern = Pattern.compile(GenericSpecification.SpecificationsPattern);
            Matcher matcher = pattern.matcher(search.trim() + ","); //all searches must contain a comma
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(3), matcher.group(6).trim());
            }
            Specification<PosEntity> spec = builder.build();
            posList =this.repository.findAll(spec);
        }

        if (posList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posList, HttpStatus.OK);

    }

    @RequestMapping(
            value = "/get",
            params = { "page", "size", "search"},
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public ResponseEntity<Page<PosEntity>> findPaginated(@RequestParam("page") int page, @RequestParam("size") int size
            , @RequestParam("search") String search) {
        GenericSpecificationsBuilder<PosEntity> builder = new GenericSpecificationsBuilder();
        Pattern pattern = Pattern.compile(GenericSpecification.SpecificationsPattern);
        Matcher matcher = pattern.matcher(search.trim() + ","); //all searches must contain a comma
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(3), matcher.group(6).trim());
        }
        Specification<PosEntity> spec = builder.build();
        Page<PosEntity> branchesPage = this.repository.findAll(spec, new PageRequest(page-1, size));
        if (page > branchesPage.getTotalPages()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(branchesPage, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{posId}",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public ResponseEntity<PosEntity> findOne(@PathVariable("posId") Long posId) {
        PosEntity branch = this.repository.findOne(posId);
        if (branch == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(branch, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            produces = {"application/json"}
    )
    public ResponseEntity<PosEntity> create(@RequestBody PosEntity newRecord, UriComponentsBuilder ucBuilder) {
        //add the default pos status
        List<PosStatusEntity> posStatus = this.posStatusRepository.findByIsDefault(true);
        newRecord.setStatus(posStatus.get(0));
        this.repository.saveAndFlush(newRecord);
        PosEntity posEntity = this.repository.findOne(newRecord.getId());
        return new ResponseEntity<>(posEntity, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/{posId}",
            method = RequestMethod.PUT,
            produces = {"application/json"}
    )
    public ResponseEntity<PosEntity> update(@PathVariable("posId") Long posId, @RequestBody PosEntity updatedRecord){
        PosEntity posEntity = this.repository.findOne(posId);
        if(posEntity == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        posEntity.setSerialNumber(updatedRecord.getSerialNumber());
        posEntity.setVendor(updatedRecord.getVendor());
        posEntity.setModel(updatedRecord.getModel());
        posEntity.setType(updatedRecord.getType());
        posEntity.setMade((updatedRecord.getMade()));
        posEntity.setPartNumber((updatedRecord.getPartNumber()));
        posEntity.setOtherFeatures((updatedRecord.getOtherFeatures()));
        posEntity.setBatchNumber((updatedRecord.getBatchNumber()));
        posEntity.setCondition((updatedRecord.getCondition()));
        posEntity.setRemarks((updatedRecord.getRemarks()));
        posEntity.setStatus((updatedRecord.getStatus()));
        posEntity.setFile((updatedRecord.getFile()));
        posEntity.setTerminal((updatedRecord.getTerminal()));
        posEntity.setNode((updatedRecord.getNode()));
        posEntity.setTender((updatedRecord.getTender()));
        posEntity.setProject((updatedRecord.getProject()));
        posEntity.setVendorBranch((updatedRecord.getVendorBranch()));
        this.repository.saveAndFlush(posEntity);
        return new ResponseEntity<>(posEntity, HttpStatus.OK);
    }

    @RequestMapping(
            value="/{posId}",
            method = RequestMethod.DELETE,
            produces = {"application/json"}
    )
    public ResponseEntity<Void> deleteOne(@PathVariable("posId") Long posId){
        if(!this.repository.exists(posId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.repository.delete(posId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
