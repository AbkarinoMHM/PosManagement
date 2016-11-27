package com.ebe.repositories;

import com.ebe.entities.AreaEntity;
import com.ebe.entities.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by saado on 10/27/2016.
 */
@Repository
@PreAuthorize("hasRole('USER')")
public interface AreaRepository extends JpaRepository<AreaEntity, Long> {
    //List<AreaEntity> findAreaByRegion(RegionEntity region);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    AreaEntity saveAndFlush(AreaEntity obj);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    void delete(Long id);
}
