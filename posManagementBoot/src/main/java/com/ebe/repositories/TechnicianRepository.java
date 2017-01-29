package com.ebe.repositories;

import com.ebe.entities.ServiceCenterEntity;
import com.ebe.entities.TechnicianEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

/**
 * Created by saado on 10/27/2016.
 */
@Repository
@PreAuthorize("hasRole('USER')")
public interface TechnicianRepository extends JpaRepository<TechnicianEntity, Long>, JpaSpecificationExecutor<TechnicianEntity> {

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    TechnicianEntity saveAndFlush(TechnicianEntity obj);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    void delete(Long id);
}
