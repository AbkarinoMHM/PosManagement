package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "poscondition")
public class PosconditionEntity {
    private Integer conditionId;
    private String conditionName;

    @Id
    @Column(name = "ConditionID", nullable = false)
    public int getConditionId() {
        return conditionId;
    }

    public void setConditionId(int conditionId) {
        this.conditionId = conditionId;
    }

    @Basic
    @Column(name = "ConditionName", nullable = true, length = 50)
    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PosconditionEntity that = (PosconditionEntity) o;

        if (conditionId != that.conditionId) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return this.conditionId.hashCode();
    }
}
