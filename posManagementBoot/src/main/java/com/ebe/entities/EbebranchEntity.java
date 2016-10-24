package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "ebebranch")
public class EbebranchEntity {
    private Long ebeBranchId;
    private String branchName;
    private String branchAddress;
    private String branchTel;
    private Long areaId;

    @Id
    @Column(name = "EbeBranchID", nullable = false)
    public Long getEbeBranchId() {
        return ebeBranchId;
    }

    public void setEbeBranchId(Long ebeBranchId) {
        this.ebeBranchId = ebeBranchId;
    }

    @Basic
    @Column(name = "BranchName", nullable = true, length = 50)
    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    @Basic
    @Column(name = "BranchAddress", nullable = true, length = 100)
    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    @Basic
    @Column(name = "BranchTel", nullable = true, length = 50)
    public String getBranchTel() {
        return branchTel;
    }

    public void setBranchTel(String branchTel) {
        this.branchTel = branchTel;
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

        EbebranchEntity that = (EbebranchEntity) o;

        if (ebeBranchId != that.ebeBranchId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return this.ebeBranchId.hashCode();
    }
}
