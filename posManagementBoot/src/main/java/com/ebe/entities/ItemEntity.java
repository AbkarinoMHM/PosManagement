package com.ebe.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "item")
public class ItemEntity {
    private Long itemId;
    private int itemTypeId;
    private String serialNumber;
    private String description;
    private Long ebeBranchId;
    private Timestamp timeStamp;
    private String made;
    private String model;
    private Long projectId;

    @Id
    @Column(name = "ItemID", nullable = false)
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "ItemTypeID", nullable = false)
    public int getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(int itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    @Basic
    @Column(name = "SerialNumber", nullable = true, length = 50)
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Basic
    @Column(name = "Description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "EbeBranchID", nullable = false)
    public Long getEbeBranchId() {
        return ebeBranchId;
    }

    public void setEbeBranchId(Long ebeBranchId) {
        this.ebeBranchId = ebeBranchId;
    }

    @Basic
    @Column(name = "TimeStamp", nullable = true)
    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Basic
    @Column(name = "made", nullable = true, length = 50)
    public String getMade() {
        return made;
    }

    public void setMade(String made) {
        this.made = made;
    }

    @Basic
    @Column(name = "model", nullable = true, length = 50)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "ProjectID", nullable = true)
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemEntity that = (ItemEntity) o;

        if (itemId != that.itemId) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return this.itemId.hashCode();
    }
}
