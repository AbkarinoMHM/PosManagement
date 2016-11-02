package com.ebe.services;

import com.ebe.entities.RegionEntity;
import com.ebe.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

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
