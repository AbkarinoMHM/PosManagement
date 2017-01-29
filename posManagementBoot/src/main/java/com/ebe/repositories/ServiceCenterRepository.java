package com.ebe.repositories;

import com.ebe.entities.MerchantBranchEntity;
import com.ebe.entities.ServiceCenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

/**
 * Created by saado on 10/27/2016.
 */
@Repository
@PreAuthorize("hasRole('USER')")
public interface ServiceCenterRepository extends JpaRepository<ServiceCenterEntity, Long>, JpaSpecificationExecutor<ServiceCenterEntity> {

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    ServiceCenterEntity saveAndFlush(ServiceCenterEntity obj);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    void delete(Long id);
}
