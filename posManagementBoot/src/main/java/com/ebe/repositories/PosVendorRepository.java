package com.ebe.repositories;

import com.ebe.entities.PosVendorEntity;
import com.ebe.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by saado on 10/25/2016.
 */
@Repository
@PreAuthorize("hasRole('USER')")
public interface PosVendorRepository extends JpaRepository<PosVendorEntity, Integer>, JpaSpecificationExecutor<PosVendorEntity> {

    List<PosVendorEntity> findByNameIgnoreCaseContaining(String name);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    PosVendorEntity saveAndFlush(PosVendorEntity obj);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    void delete(Integer id);
}
