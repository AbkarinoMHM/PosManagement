package com.ebe.repositories;

import com.ebe.common.GeneralLogger;
import com.ebe.entities.UserEntity;
import com.ebe.entities.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by saado on 11/16/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void testGetUsers() throws Exception {
        repository.save(new UserEntity("user 1", "user1@com.com", "password", false));
        List<UserEntity> users = repository.findAll();
        assertTrue(users.size() >= 1);
    }

    @Test
    public void testCreateUser() throws Exception {
        UserEntity userEntity = new UserEntity("user 1", "user1@com.com", "password", false);
        repository.saveAndFlush(userEntity);
        Integer id = userEntity.getUserId();
        assertThat(id, is(notNullValue()));

        UserEntity again = repository.findOne(id);
        assertThat(again.getUserId(), is(id));
        GeneralLogger.info(this.getClass(), again.toString());
        assertThat(again.getUserFullName(), is("user 1"));
        assertThat(again.getUserEmail(), is("user1@com.com"));
        assertThat(again.getUserPassword(), is("password"));
        assertThat(again.getUserIsCustomer(), is(false));
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserEntity userEntity = new UserEntity("user 1", "user1@com.com", "password", false);
        repository.save(userEntity);
        Integer id = userEntity.getUserId();
        userEntity.setUserFullName("TestUser");
        repository.save(userEntity);
        UserEntity again = repository.findOne(id);
        assertThat(again.getUserFullName(), is("TestUser"));
    }

    @Test
    public void testFindUserByEmail() throws Exception{
        UserEntity userEntity = new UserEntity("user 1", "user1@com.com", "password", false);
        repository.save(userEntity);
        List<UserEntity> users = repository.findUserByUserEmail("user1@com.com");
        assertTrue(users.contains(userEntity));
    }
}
