package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "technician")
public class TechnicianEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "technician_seq")
    @SequenceGenerator(name = "technician_seq", sequenceName = "technician_technician_id_seq", allocationSize = 1)
    @Column(name = "technician_id")
    private Long id;
    @Column(name = "technician_name", nullable = false)
    private String name;
    @Column(name = "technician_job_title")
    private String title;
    @Column(name = "technician_rate")
    private Integer rate;
    @Column(name = "technician_mobile")
    private String mobile;
    @Column(name = "technician_address")
    private String address;
    @Column(name = "technician_tel")
    private String tel;

    public TechnicianEntity() {
    }

    public TechnicianEntity(String name) {
        this.name = name;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TechnicianEntity that = (TechnicianEntity) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
