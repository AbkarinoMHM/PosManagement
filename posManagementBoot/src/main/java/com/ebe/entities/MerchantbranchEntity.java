package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "merchantbranch")
public class MerchantbranchEntity {
    private Long merchantBranchId;
    private Long merchantId;
    private String locationName;
    private String locationAddress;
    private Long areaId;
    private String notes;

    @Id
    @Column(name = "MerchantBranchID", nullable = false)
    public Long getMerchantBranchId() {
        return merchantBranchId;
    }

    public void setMerchantBranchId(Long merchantBranchId) {
        this.merchantBranchId = merchantBranchId;
    }

    @Basic
    @Column(name = "MerchantID", nullable = false)
    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    @Basic
    @Column(name = "LocationName", nullable = true, length = 50)
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    @Basic
    @Column(name = "LocationAddress", nullable = true, length = 100)
    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    @Basic
    @Column(name = "AreaID", nullable = false)
    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    @Basic
    @Column(name = "Notes", nullable = true, length = 100)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MerchantbranchEntity that = (MerchantbranchEntity) o;

        if (merchantBranchId != that.merchantBranchId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return this.merchantBranchId.hashCode();
    }
}
