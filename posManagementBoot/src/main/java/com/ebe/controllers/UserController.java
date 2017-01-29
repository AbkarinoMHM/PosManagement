package com.ebe.controllers;

import com.ebe.SearchSpecifications.GenericSpecification;
import com.ebe.SearchSpecifications.GenericSpecificationsBuilder;
import com.ebe.entities.UserEntity;
import com.ebe.repositories.UserRepository;
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
 * Created by saado on 11/16/2016.
 */
@RestController
@RequestMapping("services/user")
@Transactional
public class UserController {
    @Autowired
    private UserRepository userRepository;

    /**
     * Get all users
     * @return All users
     */
    @RequestMapping(
            method = RequestMethod.GET, 
            produces = {"application/json"}
    )
    public ResponseEntity<List<UserEntity>> findAll() {
        List<UserEntity> users = this.userRepository.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Get all users by page
     * @param page page number
     * @param size number of buttonSheetItems per page
     * @param search filtration string
     * @return projects page
     */
    @RequestMapping(
            value = "/get",
            params = { "page", "size", "search"},
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public ResponseEntity<Page<UserEntity>> findPaginated(@RequestParam("page") int page, @RequestParam("size") int size
            , @RequestParam("search") String search) {
        GenericSpecificationsBuilder<UserEntity> builder = new GenericSpecificationsBuilder();
        Pattern pattern = Pattern.compile(GenericSpecification.SpecificationsPattern);
        Matcher matcher = pattern.matcher(search + ","); //all searches must contain a comma
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(5).trim());
        }
        Specification<UserEntity> spec = builder.build();
        Page<UserEntity> usersPage = this.userRepository.findAll(spec, new PageRequest(page-1, size));
        if (page > usersPage.getTotalPages()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usersPage, HttpStatus.OK);
    }

    /**
     * Find a user with id = @userId
     *
     * @param userId The user id to look for
     * @return The user with id = @userId
     */
    @RequestMapping(
            value = "/{userId}", 
            method = RequestMethod.GET,  
            produces = {"application/json"}
    )
    public ResponseEntity<UserEntity> findOne(@PathVariable("userId") Integer userId) {
        UserEntity user = this.userRepository.findOne(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Create a new project
     * @param user The user to be created
     * @param ucBuilder
     * @return
     */
    @RequestMapping(
            method = RequestMethod.POST,
            produces = {"application/json"}
    )
    public ResponseEntity<UserEntity> create(@RequestBody UserEntity user, UriComponentsBuilder ucBuilder) {
        this.userRepository.saveAndFlush(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * Update a user
     * @param userId The ID of the project to be updated
     * @param updatedRecord The new project name
     * @return The updated project
     */
    @RequestMapping(
            value = "/{userId}",
            method = RequestMethod.PUT,
            produces = {"application/json"}
    )
    public ResponseEntity<UserEntity> update(@PathVariable("userId") Integer userId, @RequestBody UserEntity updatedRecord){
        UserEntity userEntity = this.userRepository.findOne(userId);
        if(userEntity == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userEntity.setUserFullName(updatedRecord.getUserFullName());
        userEntity.setUserAddress(updatedRecord.getUserAddress());
        userEntity.setUserMobile(updatedRecord.getUserMobile());
        userEntity.setUserIsCustomer(updatedRecord.getUserIsCustomer());
        this.userRepository.saveAndFlush(userEntity);
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    /**
     * Delete a user by user ID
     * @param recordId The ID of the user to be deleted
     * @return HTTP NO_CONTENT
     */
    @RequestMapping(
            value="/{recordId}",
            method = RequestMethod.DELETE,
            produces = {"application/json"}
    )
    public ResponseEntity<Void> deleteOne(@PathVariable("recordId") Integer recordId){
        if(!this.userRepository.exists(recordId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.userRepository.delete(recordId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
