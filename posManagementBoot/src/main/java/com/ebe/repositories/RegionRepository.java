package com.ebe.repositories;

import com.ebe.entities.RegionEntity;
import org.hibernate.cache.spi.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by saado on 10/21/2016.
 */
@PreAuthorize("hasRole('USER')")
@Repository
public interface RegionRepository extends JpaRepository<RegionEntity, Integer> , JpaSpecificationExecutor<RegionEntity> {
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    RegionEntity saveAndFlush(RegionEntity obj);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    void delete(Integer id);
}
