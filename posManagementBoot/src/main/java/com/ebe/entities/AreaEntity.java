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
    @Column(name="area_id")
    private Long id;
    @Column(name="area_name", nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name="region_id",referencedColumnName="region_id")
    private RegionEntity region;


    public AreaEntity() {
    }

    public AreaEntity(String name, RegionEntity region) {
        this.name = name;
        this.region = region;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RegionEntity getRegion() {
        return region;
    }

    public void setRegion(RegionEntity region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AreaEntity that = (AreaEntity) o;

        if (id != that.id) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
