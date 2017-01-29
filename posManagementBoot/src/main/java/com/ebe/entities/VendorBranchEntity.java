package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "vendor_branch")
public class VendorBranchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendor_branch_seq")
    @SequenceGenerator(name = "vendor_branch_seq", sequenceName = "vendor_branch_vendor_branch_id_seq", allocationSize = 1)
    @Column(name = "vendor_branch_id")
    private Long id;
    @Column(name = "vendor_branch_name")
    private String name;
    @Column(name = "vendor_branch_address")
    private String address;
    @Column(name = "vendor_branch_tel")
    private String tel;
    @ManyToOne(optional = false)
    @JoinColumn(name="area_id",referencedColumnName="area_id")
    private AreaEntity area;

    public VendorBranchEntity() {

    }

    public VendorBranchEntity(String name, AreaEntity area) {
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

        VendorBranchEntity that = (VendorBranchEntity) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
