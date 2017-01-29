package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "service_center")
public class ServiceCenterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_center_seq")
    @SequenceGenerator(name = "service_center_seq", sequenceName = "service_center_service_center_id_seq", allocationSize = 1)
    @Column(name = "service_center_id", nullable = false)
    private Long id;
    @Column(name = "service_center_name", nullable = false, length = 50)
    private String name;
    @Column(name = "service_center_address", nullable = true, length = -1)
    private String address;
    @Column(name = "service_center_tel", nullable = true, length = -1)
    private String tel;
    @ManyToOne(optional = false)
    @JoinColumn(name="area_id",referencedColumnName="area_id")
    private AreaEntity area;


    public ServiceCenterEntity() {
    }

    public ServiceCenterEntity(String name, AreaEntity area) {
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

        ServiceCenterEntity that = (ServiceCenterEntity) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
