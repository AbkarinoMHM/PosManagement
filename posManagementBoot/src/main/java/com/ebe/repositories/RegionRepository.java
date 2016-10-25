package com.ebe.repositories;

import com.ebe.entities.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by saado on 10/21/2016.
 */
@Repository
public interface RegionRepository extends JpaRepository<RegionEntity, Integer> {
}
