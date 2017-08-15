package com.ebe.controllers;

import com.ebe.SearchSpecifications.GenericSpecification;
import com.ebe.SearchSpecifications.GenericSpecificationsBuilder;
import com.ebe.entities.PosStatusEntity;
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
@RequestMapping("services/posstatus")
@Transactional
public class PosStatusController {
    @Autowired
    private PosStatusRepository posStatusRepository;

    /**
     * Get all pos status
     *
     * @return All pos status list
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PosStatusEntity>> findAll() {
        List<PosStatusEntity> posStatus = this.posStatusRepository.findAll();
        if (posStatus.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posStatus, HttpStatus.OK);

    }

    /**
     * Find the default pos status
     * @return The Pos status with status_isDefault = @isDefault
     */
    @RequestMapping(
            value = "/Default",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public ResponseEntity<PosStatusEntity> findByIsDefault() {
        List<PosStatusEntity> posStatus = this.posStatusRepository.findByIsDefault(true);
        if (posStatus == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(posStatus.get(0), HttpStatus.OK);
    }
}
