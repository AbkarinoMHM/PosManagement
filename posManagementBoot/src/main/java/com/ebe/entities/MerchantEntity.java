package com.ebe.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "merchant")
public class MerchantEntity {
    private Long merchantId;
    private String merchantKey;
    private String merchantName;
    private String notes;
    private String contactPerson1;
    private String contactTel1;
    private String contactPerson2;
    private String contactTel2;
    private String contactPerson3;
    private String contactTel3;
    private String clazz;
    private String mcc;
    private Timestamp timestamp;

    @Id
    @Column(name = "MerchantID", nullable = false)
    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    @Basic
    @Column(name = "MerchantKey", nullable = false, length = 50)
    public String getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        this.merchantKey = merchantKey;
    }

    @Basic
    @Column(name = "MerchantName", nullable = true, length = 100)
    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @Basic
    @Column(name = "Notes", nullable = true, length = 100)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Basic
    @Column(name = "ContactPerson1", nullable = true, length = 255)
    public String getContactPerson1() {
        return contactPerson1;
    }

    public void setContactPerson1(String contactPerson1) {
        this.contactPerson1 = contactPerson1;
    }

    @Basic
    @Column(name = "ContactTel1", nullable = true, length = 50)
    public String getContactTel1() {
        return contactTel1;
    }

    public void setContactTel1(String contactTel1) {
        this.contactTel1 = contactTel1;
    }

    @Basic
    @Column(name = "ContactPerson2", nullable = true, length = 255)
    public String getContactPerson2() {
        return contactPerson2;
    }

    public void setContactPerson2(String contactPerson2) {
        this.contactPerson2 = contactPerson2;
    }

    @Basic
    @Column(name = "ContactTel2", nullable = true, length = 50)
    public String getContactTel2() {
        return contactTel2;
    }

    public void setContactTel2(String contactTel2) {
        this.contactTel2 = contactTel2;
    }

    @Basic
    @Column(name = "ContactPerson3", nullable = true, length = 255)
    public String getContactPerson3() {
        return contactPerson3;
    }

    public void setContactPerson3(String contactPerson3) {
        this.contactPerson3 = contactPerson3;
    }

    @Basic
    @Column(name = "ContactTel3", nullable = true, length = 50)
    public String getContactTel3() {
        return contactTel3;
    }

    public void setContactTel3(String contactTel3) {
        this.contactTel3 = contactTel3;
    }

    @Basic
    @Column(name = "Class", nullable = true, length = 10)
    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @Basic
    @Column(name = "MCC", nullable = true, length = 50)
    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    @Basic
    @Column(name = "Timestamp", nullable = true)
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MerchantEntity that = (MerchantEntity) o;

        if (merchantId != that.merchantId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return this.merchantId.hashCode();
    }
}
