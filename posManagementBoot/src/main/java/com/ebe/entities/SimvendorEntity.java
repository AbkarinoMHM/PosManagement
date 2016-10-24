package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "simvendor")
public class SimvendorEntity {
    private Integer simVendorId;
    private String simVendorName;

    @Id
    @Column(name = "SimVendorID", nullable = false)
    public Integer getSimVendorId() {
        return simVendorId;
    }

    public void setSimVendorId(Integer simVendorId) {
        this.simVendorId = simVendorId;
    }

    @Basic
    @Column(name = "SimVendorName", nullable = false, length = 50)
    public String getSimVendorName() {
        return simVendorName;
    }

    public void setSimVendorName(String simVendorName) {
        this.simVendorName = simVendorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimvendorEntity that = (SimvendorEntity) o;

        if (simVendorId != that.simVendorId) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return this.simVendorId.hashCode();
    }
}
