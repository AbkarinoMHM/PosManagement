package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "poscondition")
public class PosConditionEntity {
    @Id
    private Integer posConditionId;
    @Basic(optional = false)
    private String posConditionName;

    public PosConditionEntity(){}

    public PosConditionEntity(Integer posConditionId, String posConditionName){
        this.posConditionId = posConditionId;
        this.posConditionName = posConditionName;
    }


    public Integer getPosConditionId() {
        return posConditionId;
    }

    public void setPosConditionId(Integer posConditionId) {
        this.posConditionId = posConditionId;
    }

    public String getPosConditionName() {
        return posConditionName;
    }

    public void setPosConditionName(String posConditionName) {
        this.posConditionName = posConditionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PosConditionEntity that = (PosConditionEntity) o;

        if (posConditionId != that.posConditionId) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return this.posConditionId.hashCode();
    }
}
