package com.ebe.repositories;

import com.ebe.entities.AreaEntity;
import com.ebe.entities.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by saado on 10/27/2016.
 */
public interface AreaRepository extends JpaRepository<AreaEntity, Long> {
    List<AreaEntity> findAreaByRegion(RegionEntity region);
}
