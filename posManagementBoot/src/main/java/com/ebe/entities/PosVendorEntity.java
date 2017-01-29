package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "pos_vendor")
public class PosVendorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pos_vendor_seq")
    @SequenceGenerator(name = "pos_vendor_seq", sequenceName = "pos_vendor_pos_vendor_id_seq", allocationSize = 1)
    @Column(name = "pos_vendor_id", nullable = false)
    private Integer id;
    @Column(name = "pos_vendor_name", nullable = false)
    private String name;

    public PosVendorEntity() {

    }

    public PosVendorEntity(String name) {
        this.name = name;
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

        PosVendorEntity that = (PosVendorEntity) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
