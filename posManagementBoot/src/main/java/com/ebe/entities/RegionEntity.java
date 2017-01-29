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
    @Column(name="region_id")
    private Integer id;
    @Column(name="region_name", nullable = false)
    private String name;

    public RegionEntity() {
    }

    public RegionEntity(String name) {
        this.setName(name);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegionEntity that = (RegionEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public String toString() {
        return "Region ID: " + this.getId() + ", Region Name: " + this.getName();
    }
}
