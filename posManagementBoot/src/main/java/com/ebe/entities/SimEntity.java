package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "sim")
public class SimEntity {
    private Long simId;
    private String simNumber;
    private Integer simVendorId;
    private Long ebeBranchId;
    private Byte isDamage;

    @Id
    @Column(name = "SimID", nullable = false)
    public Long getSimId() {
        return simId;
    }

    public void setSimId(Long simId) {
        this.simId = simId;
    }

    @Basic
    @Column(name = "SimNumber", nullable = false, length = 50)
    public String getSimNumber() {
        return simNumber;
    }

    public void setSimNumber(String simNumber) {
        this.simNumber = simNumber;
    }

    @Basic
    @Column(name = "SimVendorID", nullable = false)
    public Integer getSimVendorId() {
        return simVendorId;
    }

    public void setSimVendorId(Integer simVendorId) {
        this.simVendorId = simVendorId;
    }

    @Basic
    @Column(name = "EbeBranchID", nullable = true)
    public Long getEbeBranchId() {
        return ebeBranchId;
    }

    public void setEbeBranchId(Long ebeBranchId) {
        this.ebeBranchId = ebeBranchId;
    }

    @Basic
    @Column(name = "IsDamage", nullable = true)
    public Byte getIsDamage() {
        return isDamage;
    }

    public void setIsDamage(Byte isDamage) {
        this.isDamage = isDamage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimEntity simEntity = (SimEntity) o;

        if (simId != simEntity.simId) return false;
        return true;
    }

    @Override
    public int hashCode() {
              return this.simId.hashCode();
    }
}
