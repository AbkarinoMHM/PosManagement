package com.ebe.repositories;

import com.ebe.entities.PosConditionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

/**
 * Created by saado on 10/27/2016.
 */
@Repository
@PreAuthorize("hasRole('USER')")
public interface PosConditionRepository extends JpaRepository<PosConditionEntity, Integer> {
}
