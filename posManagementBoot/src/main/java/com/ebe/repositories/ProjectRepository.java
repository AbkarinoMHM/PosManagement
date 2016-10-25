package com.ebe.repositories;

import com.ebe.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by saado on 10/25/2016.
 */
@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
}
