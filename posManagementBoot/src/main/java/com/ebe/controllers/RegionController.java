package com.ebe.controllers;

import com.ebe.SearchSpecifications.GenericSpecification;
import com.ebe.SearchSpecifications.GenericSpecificationsBuilder;
import com.ebe.entities.ProjectEntity;
import com.ebe.entities.RegionEntity;
import com.ebe.repositories.RegionRepository;
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
@RequestMapping("/services/region")
@Transactional
public class RegionController {
    @Autowired
    private RegionRepository regionRepository;

    /**
     * Get all regions
     *
     * @return All regions
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<RegionEntity>> findAll() {
        List<RegionEntity> regions = this.regionRepository.findAll();
        if (regions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(regions, HttpStatus.OK);

    }

    /**
     * Get all Regions by page
     * @param page Page number
     * @param size Number of buttonSheetItems per page
     * @return Regions page
     */
    @RequestMapping(
            value = "/get",
            params = { "page", "size", "search"},
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public ResponseEntity<Page<RegionEntity>> findPaginated(@RequestParam("page") int page, @RequestParam("size") int size
            , @RequestParam("search") String search) {
        GenericSpecificationsBuilder<RegionEntity> builder = new GenericSpecificationsBuilder();
        Pattern pattern = Pattern.compile(GenericSpecification.SpecificationsPattern);
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(5).trim());
        }
        Specification<RegionEntity> spec = builder.build();
        Page<RegionEntity> resultPage = this.regionRepository.findAll(spec, new PageRequest(page-1, size));
        if (page > resultPage.getTotalPages()) {
            //return (Page<ProjectEntity>) new Exception("Not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultPage, HttpStatus.OK);
        //return projectsPage;
    }

    /**
     * Find a region with id = @regionId
     *
     * @param regionId The region id to look for
     * @return The region with id = @regionId
     */
    @RequestMapping(value = "/{regionId}", method = RequestMethod.GET)
    public ResponseEntity<RegionEntity> findOne(@PathVariable("regionId") Integer regionId) {
        RegionEntity region = this.regionRepository.findOne(regionId);
        if (region == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(region, HttpStatus.OK);
    }

    /**
     * Create a new region
     * @param region The region to be created
     * @param ucBuilder
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RegionEntity> create(@RequestBody RegionEntity region, UriComponentsBuilder ucBuilder) {
        this.regionRepository.saveAndFlush(region);
        //HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(ucBuilder.path("/region/{regionId}").buildAndExpand(region.getRegionId()).toUri());
        return new ResponseEntity<>(region, HttpStatus.CREATED);
    }

    /**
     * Update a region
     * @param regionId The ID of the region to be updated
     * @param updatedRegion The new region name
     * @return The updated region
     */
    @RequestMapping(value = "/{regionid}", method = RequestMethod.PUT)
    public ResponseEntity<RegionEntity> update(@PathVariable("regionid") Integer regionId, @RequestBody RegionEntity updatedRegion){
        RegionEntity regionEntity = this.regionRepository.findOne(regionId);
        if(regionEntity == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        regionEntity.setRegionName(updatedRegion.getRegionName());
        this.regionRepository.saveAndFlush(regionEntity);
        return new ResponseEntity<>(regionEntity, HttpStatus.OK);
    }

    /**
     * Delete a region by region ID
     * @param regionId The ID of the region to be deleted
     * @return HTTP NO_CONTENT
     */
    @RequestMapping(value="/{regionid}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteOne(@PathVariable("regionid") Integer regionId){
        if(!this.regionRepository.exists(regionId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.regionRepository.delete(regionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
