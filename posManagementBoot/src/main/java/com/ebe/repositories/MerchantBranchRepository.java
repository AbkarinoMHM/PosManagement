package com.ebe.repositories;

import com.ebe.entities.MerchantBranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

/**
 * Created by saado on 10/27/2016.
 */
@Repository
@PreAuthorize("hasRole('USER')")
public interface MerchantBranchRepository extends JpaRepository<MerchantBranchEntity, Long>, JpaSpecificationExecutor<MerchantBranchEntity> {

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    MerchantBranchEntity saveAndFlush(MerchantBranchEntity obj);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    void delete(Long id);
}
