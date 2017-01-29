package com.ebe.repositories;

import com.ebe.entities.VendorBranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by saado on 10/27/2016.
 */
@Repository
@PreAuthorize("hasRole('USER')")
public interface VendorBranchRepository extends JpaRepository<VendorBranchEntity, Long>, JpaSpecificationExecutor<VendorBranchEntity> {

    List<VendorBranchEntity> findByNameIgnoreCaseContaining(String name);
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    VendorBranchEntity saveAndFlush(VendorBranchEntity obj);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    void delete(Long id);
}
