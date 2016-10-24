package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "postype")
public class PostypeEntity {
    private Integer typeId;
    private String typeName;

    @Id
    @Column(name = "TypeID", nullable = false)
    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Basic
    @Column(name = "TypeName", nullable = true, length = 100)
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostypeEntity that = (PostypeEntity) o;

        if (typeId != that.typeId) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return this.typeId.hashCode();
    }
}
