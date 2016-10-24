package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "itemtype")
public class ItemtypeEntity {
    private Integer itemTypeId;
    private String itemTypeName;

    @Id
    @Column(name = "ItemTypeID", nullable = false)
    public Integer getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(Integer itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    @Basic
    @Column(name = "ItemTypeName", nullable = true, length = 50)
    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemtypeEntity that = (ItemtypeEntity) o;

        if (itemTypeId != that.itemTypeId) return false;
        if (itemTypeName != null ? !itemTypeName.equals(that.itemTypeName) : that.itemTypeName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return this.itemTypeId.hashCode();
    }
}
