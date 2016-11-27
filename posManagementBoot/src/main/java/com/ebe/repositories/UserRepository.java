package com.ebe.repositories;

import com.ebe.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by saado on 11/16/2016.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    List<UserEntity> findUserByUserEmail(String email);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    UserEntity saveAndFlush(UserEntity obj);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    void delete(Integer id);

}
