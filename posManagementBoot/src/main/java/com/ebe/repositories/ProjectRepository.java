package com.ebe.repositories;

import com.ebe.entities.ProjectEntity;
import com.ebe.entities.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

/**
 * Created by saado on 10/25/2016.
 */
@Repository
@PreAuthorize("hasRole('USER')")
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long>, JpaSpecificationExecutor<ProjectEntity> {
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    ProjectEntity saveAndFlush(ProjectEntity obj);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    void delete(Long id);
}
