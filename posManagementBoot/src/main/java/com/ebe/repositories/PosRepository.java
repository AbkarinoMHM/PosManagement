package com.ebe.repositories;

import com.ebe.entities.PosEntity;
import com.ebe.entities.PosTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

/**
 * Created by saado on 10/25/2016.
 */
@Repository
@PreAuthorize("hasRole('USER')")
public interface PosRepository extends JpaRepository<PosEntity, Long>, JpaSpecificationExecutor<PosEntity> {
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    PosEntity saveAndFlush(PosEntity obj);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    void delete(Long id);
}
