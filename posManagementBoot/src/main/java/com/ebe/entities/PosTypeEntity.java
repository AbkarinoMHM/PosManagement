package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "pos_type")
public class PosTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pos_type_seq")
    @SequenceGenerator(name = "pos_type_seq", sequenceName = "pos_type_pos_type_id_seq", allocationSize = 1)
    @Column(name = "pos_type_id", nullable = false)
    private Integer id;
    @Column(name = "pos_type_name", nullable = false)
    private String name;

    public PosTypeEntity() {

    }

    public PosTypeEntity(String name) {
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

        PosTypeEntity that = (PosTypeEntity) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
