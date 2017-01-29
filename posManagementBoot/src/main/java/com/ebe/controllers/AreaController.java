package com.ebe.controllers;

import com.ebe.SearchSpecifications.GenericSpecification;
import com.ebe.SearchSpecifications.GenericSpecificationsBuilder;
import com.ebe.entities.AreaEntity;
import com.ebe.entities.RegionEntity;
import com.ebe.repositories.AreaRepository;
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
@RequestMapping("services/area")
@Transactional
public class AreaController {
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private RegionRepository regionRepository;


    /**
     * Get all areas
     *
     * @param name optional search by name
     * @return All areas list
     */
    @RequestMapping(
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public ResponseEntity<List<AreaEntity>> findAll(@RequestParam(value = "name", defaultValue = "") final String name,
                                                    @RequestParam(value = "region", defaultValue = "0") final int regoinId) {
        List<AreaEntity> areas;
        if (name.isEmpty() && regoinId == 0) { //both area name and region id are empty -> find all
            areas = this.areaRepository.findAll();
        } else if (regoinId == 0) { // region id is empty but area name is not -> find by area name
            areas = this.areaRepository.findByNameIgnoreCaseContaining(name);
        } else {//region id is not empty
            RegionEntity region = regionRepository.findOne(regoinId);
            if (name.isEmpty()) { //region id is not empty and area name is empty -> find by region
                areas = areaRepository.findByRegion(region);
            } else { //region id is not empty and area name is not empty -> find by region and area name
                areas = areaRepository.findByRegionAndNameIgnoreCaseContaining(region, name);
            }
        }
        if (areas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(areas, HttpStatus.OK);
        }
    }

    /**
     * Get all areas by page
     *
     * @param page   page number
     * @param size   number of buttonSheetItems per page
     * @param search filtration string
     * @return areas page
     */
    @RequestMapping(
            value = "/get",
            params = {"page", "size", "search"},
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public ResponseEntity<Page<AreaEntity>> findPaginated(@RequestParam("page") int page, @RequestParam("size") int size
            , @RequestParam("search") String search) {
        GenericSpecificationsBuilder<AreaEntity> builder = new GenericSpecificationsBuilder();
        Pattern pattern = Pattern.compile(GenericSpecification.SpecificationsPattern);
        Matcher matcher = pattern.matcher(search.trim() + ","); //all searches must contain a comma
        while (matcher.find()) {
            builder.with(matcher.group(GenericSpecification.fieldNameIndex),
                    matcher.group(GenericSpecification.operatorIndex), matcher.group(GenericSpecification.valueIndex).trim());
        }
        Specification<AreaEntity> spec = builder.build();
        Page<AreaEntity> areasPage = this.areaRepository.findAll(spec, new PageRequest(page - 1, size));
        if (page > areasPage.getTotalPages()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(areasPage, HttpStatus.OK);
    }

    /**
     * Find a area with id = @areaId
     *
     * @param areaId The area id to look for
     * @return The area with id = @areaId
     */
    @RequestMapping(
            value = "/{areaId}",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public ResponseEntity<AreaEntity> findOne(@PathVariable("areaId") Long areaId) {
        AreaEntity area = this.areaRepository.findOne(areaId);
        if (area == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(area, HttpStatus.OK);
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
    public ResponseEntity<AreaEntity> create(@RequestBody AreaEntity newRecord, UriComponentsBuilder ucBuilder) {
        this.areaRepository.saveAndFlush(newRecord);
        return new ResponseEntity<>(newRecord, HttpStatus.CREATED);
    }

    /**
     * Update an area
     *
     * @param areaId        The ID of the area to be updated
     * @param updatedRecord The updated area
     * @return The updated area
     */
    @RequestMapping(
            value = "/{areaId}",
            method = RequestMethod.PUT,
            produces = {"application/json"}
    )
    public ResponseEntity<AreaEntity> update(@PathVariable("areaId") Long areaId, @RequestBody AreaEntity updatedRecord) {
        AreaEntity areaEntity = this.areaRepository.findOne(areaId);
        if (areaEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        areaEntity.setName(updatedRecord.getName());
        areaEntity.setRegion(updatedRecord.getRegion());
        this.areaRepository.saveAndFlush(areaEntity);
        return new ResponseEntity<>(areaEntity, HttpStatus.OK);
    }

    /**
     * Delete a area by area ID
     *
     * @param areaId The ID of the area to be deleted
     * @return HTTP NO_CONTENT
     */
    @RequestMapping(
            value = "/{areaId}",
            method = RequestMethod.DELETE,
            produces = {"application/json"}
    )
    public ResponseEntity<Void> deleteOne(@PathVariable("areaId") Long areaId) {
        if (!this.areaRepository.exists(areaId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.areaRepository.delete(areaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
