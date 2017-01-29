package com.ebe.controllers;

import com.ebe.entities.PosConditionEntity;
import com.ebe.entities.PosStatusEntity;
import com.ebe.repositories.PosConditionRepository;
import com.ebe.repositories.PosStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by saado on 10/24/2016.
 */
@RestController
@RequestMapping("services/poscondition")
@Transactional
public class PosConditionController {
    @Autowired
    private PosConditionRepository posConditionRepository;

    /**
     * Get all pos status
     *
     * @return All pos status list
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PosConditionEntity>> findAll() {
        List<PosConditionEntity> posStatus = this.posConditionRepository.findAll();
        if (posStatus.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posStatus, HttpStatus.OK);

    }
}
