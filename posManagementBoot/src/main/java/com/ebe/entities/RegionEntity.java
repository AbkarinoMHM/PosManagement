package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "region")
public class RegionEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "region_seq")
    @SequenceGenerator(name="region_seq", sequenceName="region_region_id_seq", allocationSize=1)
    @Id
    //@Column(name="region_id")
    private Integer regionId;
    @Basic(optional = false)
    private String regionName;

    public RegionEntity() {
    }

    public RegionEntity(String name) {
        this.setRegionName(name);
    }


    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }


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
    public String toString() {
        return "Region ID: " + this.getRegionId() + ", Region Name: " + this.getRegionName();
    }
}
