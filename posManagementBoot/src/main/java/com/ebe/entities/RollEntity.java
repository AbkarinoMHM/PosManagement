package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "roll")
public class RollEntity {
    private Long rollId;
    private Integer rollSizeId;
    private int quantity;
    private byte direction;
    private Long ebeBranchId;
    private Long merchantBranchId;

    @Id
    @Column(name = "RollID", nullable = false)
    public Long getRollId() {
        return rollId;
    }

    public void setRollId(Long rollId) {
        this.rollId = rollId;
    }

    @Basic
    @Column(name = "RollSizeID", nullable = false)
    public Integer getRollSizeId() {
        return rollSizeId;
    }

    public void setRollSizeId(Integer rollSizeId) {
        this.rollSizeId = rollSizeId;
    }

    @Basic
    @Column(name = "Quantaty", nullable = false)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantaty) {
        this.quantity = quantaty;
    }

    @Basic
    @Column(name = "Direction", nullable = false)
    public byte getDirection() {
        return direction;
    }

    public void setDirection(byte direction) {
        this.direction = direction;
    }

    @Basic
    @Column(name = "EbeBranchID", nullable = false)
    public Long getEbeBranchId() {
        return ebeBranchId;
    }

    public void setEbeBranchId(Long ebeBranchId) {
        this.ebeBranchId = ebeBranchId;
    }

    @Basic
    @Column(name = "MerchantBranchID", nullable = true)
    public Long getMerchantBranchId() {
        return merchantBranchId;
    }

    public void setMerchantBranchId(Long merchantBranchId) {
        this.merchantBranchId = merchantBranchId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RollEntity that = (RollEntity) o;

        if (rollId != that.rollId) return false;
            return true;
    }

    @Override
    public int hashCode() {
               return this.rollId.hashCode();
    }
}
