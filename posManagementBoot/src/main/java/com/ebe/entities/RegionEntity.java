package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "region")
public class RegionEntity {
    private Long regionId;
    private String regionName;

    public RegionEntity(){}

    public RegionEntity(String name){
        this.setRegionName(name);
    }

    @Id
    @Column(name = "RegionID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    @Basic
    @Column(name = "RegionName", nullable = true, length = 50)
    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegionEntity that = (RegionEntity) o;

        if (regionId != that.regionId) return false;
        if (regionName != null ? !regionName.equals(that.regionName) : that.regionName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
          return this.regionId.hashCode();
    }

    @Override
    public String toString(){
        return "Region ID: " + this.getRegionId() + ", Region Name: " + this.getRegionName();
    }
}
