package com.ebe.repositories;

import com.ebe.entities.PosTypeEntity;
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
public interface PosTypeRepository extends JpaRepository<PosTypeEntity, Integer>, JpaSpecificationExecutor<PosTypeEntity> {

    List<PosTypeEntity> findByNameIgnoreCaseContaining(String name);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    PosTypeEntity saveAndFlush(PosTypeEntity obj);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    void delete(Integer id);
}
