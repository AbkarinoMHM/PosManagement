package com.ebe.controllers;


import com.ebe.entities.SettingEntity;
import com.ebe.repositories.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by saado on 11/9/2016.
 */
@RestController
@RequestMapping("services/setting")
@Transactional
public class SettingController {
    @Autowired
    private SettingRepository settingRepository;

    /**
     * Get all settings
     *
     * @return All projects list
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SettingEntity>> findAll() {
        List<SettingEntity> settings = this.settingRepository.findAll();
        if (settings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(settings, HttpStatus.OK);

    }
}
