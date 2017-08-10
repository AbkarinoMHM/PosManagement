package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "merchant_branch")
public class MerchantBranchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "merchant_branch_seq")
    @SequenceGenerator(name = "merchant_branch_seq", sequenceName = "merchant_branch_merchant_branch_id_seq", allocationSize = 1)
    @Column(name = "merchant_branch_id")
    private Long id;
    @Column(name = "merchant_branch_name")
    private String name;
    @Column(name = "merchant_branch_address")
    private String address;
    @Column(name = "merchant_branch_notes")
    private String notes;
    @ManyToOne(optional = false)
    @JoinColumn(name="area_id",referencedColumnName="area_id")
    private AreaEntity area;

    public MerchantBranchEntity(){

    }

    public MerchantBranchEntity(String name, AreaEntity area) {
        this.name = name;
        this.area = area;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public AreaEntity getArea() {
        return area;
    }

    public void setArea(AreaEntity area) {
        this.area = area;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MerchantBranchEntity that = (MerchantBranchEntity) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "MerchantBranchEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", notes='" + notes + '\'' +
                ", area=" + area +
                '}';
    }
}
