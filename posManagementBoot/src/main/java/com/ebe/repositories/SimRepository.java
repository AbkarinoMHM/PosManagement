package com.ebe.repositories;

import com.ebe.entities.SimEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@PreAuthorize("hasRole('USER')")
public interface SimRepository extends JpaRepository<SimEntity, Long>, JpaSpecificationExecutor<SimEntity> {

    @Override
    SimEntity saveAndFlush(SimEntity obj);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    void delete(Long id);

}
