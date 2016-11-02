package com.ebe.repositories;

import com.ebe.entities.PosStatusEntity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by saado on 10/27/2016.
 */
public interface PosStatusRepository extends JpaRepository <PosStatusEntity, Integer> {
}
