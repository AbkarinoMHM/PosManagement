package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "rollsize")
public class RollsizeEntity {
    private Integer rollSizeId;
    private String rollSizeName;

    @Id
    @Column(name = "RollSizeID", nullable = false)
    public Integer getRollSizeId() {
        return rollSizeId;
    }

    public void setRollSizeId(Integer rollSizeId) {
        this.rollSizeId = rollSizeId;
    }

    @Basic
    @Column(name = "RollSizeName", nullable = true, length = 50)
    public String getRollSizeName() {
        return rollSizeName;
    }

    public void setRollSizeName(String rollSizeName) {
        this.rollSizeName = rollSizeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RollsizeEntity that = (RollsizeEntity) o;

        if (rollSizeId != that.rollSizeId) return false;

        return true;
    }

    @Override
    public int hashCode() {
             return this.rollSizeId.hashCode();
    }
}
