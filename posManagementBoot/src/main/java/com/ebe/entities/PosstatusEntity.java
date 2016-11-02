package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "posstatus")
public class PosStatusEntity {
    @Id
    private Integer posStatusId;
    @Basic(optional = false)
    private String posStatusName;

    public PosStatusEntity() {
    }

    public PosStatusEntity(Integer posStatusId, String posStatusName) {
        this.posStatusId = posStatusId;
        this.posStatusName = posStatusName;
    }

    public Integer getPosStatusId() {
        return posStatusId;
    }

    public void setPosStatusId(Integer posStatusId) {
        this.posStatusId = posStatusId;
    }

    public String getPosStatusName() {
        return posStatusName;
    }

    public void setPosStatusName(String posStatusName) {
        this.posStatusName = posStatusName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PosStatusEntity that = (PosStatusEntity) o;

        if (posStatusId != that.posStatusId) return false;

        return true;
    }

    @Override
    public int hashCode() {

        return this.posStatusId.hashCode();
    }
}
