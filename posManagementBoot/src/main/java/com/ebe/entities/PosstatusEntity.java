package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "posstatus")
public class PosstatusEntity {
    private Integer statusId;
    private String statusName;

    @Id
    @Column(name = "StatusID", nullable = false)
    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    @Basic
    @Column(name = "StatusName", nullable = true, length = 100)
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PosstatusEntity that = (PosstatusEntity) o;

        if (statusId != that.statusId) return false;

        return true;
    }

    @Override
    public int hashCode() {

        return this.statusId.hashCode();
    }
}
