package com.ebe.controllers;

import com.ebe.entities.UserEntity;
import com.ebe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
     *
     * @return All users
     */
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity<List<UserEntity>> findAll() {
        List<UserEntity> users = this.userRepository.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<List<UserEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<UserEntity>>(users, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/get",
            params = { "page", "size" },
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public Page<UserEntity> findPaginated(@RequestParam("page") int page, @RequestParam("size") int size) {
        Page<UserEntity> usersPage = this.userRepository.findAll(new PageRequest(page-1, size));
        if (page > usersPage.getTotalPages()) {
            return (Page<UserEntity>) new Exception("Not found");
        }
        return usersPage;
    }


}
