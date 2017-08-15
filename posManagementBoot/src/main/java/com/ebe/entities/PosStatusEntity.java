package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "pos_status")
public class PosStatusEntity {
    @Id
    @Column(name = "pos_status_id", nullable = false)
    private Integer id;
    @Column(name = "pos_status_name", nullable = false)
    private String name;
    @Column(name = "pos_status_is_default", nullable = false)
    private boolean isDefault;

    public PosStatusEntity() {
    }

    public PosStatusEntity(Integer id, String name) {
        this.id = id;
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

        PosStatusEntity that = (PosStatusEntity) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "PosStatusEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
