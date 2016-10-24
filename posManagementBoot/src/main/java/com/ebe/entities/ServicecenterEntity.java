package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "servicecenter")
public class ServicecenterEntity {
    private Long serviceCenterId;
    private String serviceCenterName;
    private String address;
    private String tel;
    private Long areaId;

    @Id
    @Column(name = "ServiceCenterID", nullable = false)
    public Long getServiceCenterId() {
        return serviceCenterId;
    }

    public void setServiceCenterId(Long serviceCenterId) {
        this.serviceCenterId = serviceCenterId;
    }

    @Basic
    @Column(name = "ServiceCenterName", nullable = false, length = 50)
    public String getServiceCenterName() {
        return serviceCenterName;
    }

    public void setServiceCenterName(String serviceCenterName) {
        this.serviceCenterName = serviceCenterName;
    }

    @Basic
    @Column(name = "Address", nullable = true, length = -1)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "Tel", nullable = true, length = 15)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Basic
    @Column(name = "AreaID", nullable = false)
    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServicecenterEntity that = (ServicecenterEntity) o;

        if (serviceCenterId != that.serviceCenterId) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return this.serviceCenterId.hashCode();
    }
}
