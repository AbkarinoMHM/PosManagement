package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "area")
public class AreaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "area_seq")
    @SequenceGenerator(name = "area_seq", sequenceName = "area_area_id_seq", allocationSize = 1)
    private Long areaId;
    @Basic(optional = false)
    private String areaName;
    @Basic(optional = false)
    private Integer regionId;

    public AreaEntity() {
    }

    public AreaEntity(String areaName, Integer regionId) {
        this.areaName = areaName;
        this.regionId = regionId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }


    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }


    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AreaEntity that = (AreaEntity) o;

        if (areaId != that.areaId) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return this.areaId.hashCode();
    }
}
