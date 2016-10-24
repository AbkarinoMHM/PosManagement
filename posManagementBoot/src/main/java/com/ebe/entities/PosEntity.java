package com.ebe.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "pos")
public class PosEntity {
    private Long posid;
    private String serialNumber;
    private String vendor;
    private String model;
    private Integer typeId;
    private String made;
    private String partNumber;
    private String otherFeatures;
    private String batchNumber;
    private Integer conditionId;
    private String remarks;
    private Integer statusId;
    private Long merchantBranch;
    private Long fileId;
    private String terminalId;
    private String nodeId;
    private String notes;
    private String tenderNum;
    private Long projectId;
    private Long ebeBranchId;
    private Timestamp timestamp;

    @Id
    @Column(name = "POSID", nullable = false)
    public Long getPosid() {
        return posid;
    }

    public void setPosid(Long posid) {
        this.posid = posid;
    }

    @Basic
    @Column(name = "SerialNumber", nullable = false, length = 50)
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Basic
    @Column(name = "Vendor", nullable = true, length = 100)
    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    @Basic
    @Column(name = "Model", nullable = true, length = 100)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "TypeID", nullable = true)
    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Basic
    @Column(name = "Made", nullable = true, length = 100)
    public String getMade() {
        return made;
    }

    public void setMade(String made) {
        this.made = made;
    }

    @Basic
    @Column(name = "PartNumber", nullable = true, length = 100)
    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    @Basic
    @Column(name = "OtherFeatures", nullable = true, length = 100)
    public String getOtherFeatures() {
        return otherFeatures;
    }

    public void setOtherFeatures(String otherFeatures) {
        this.otherFeatures = otherFeatures;
    }

    @Basic
    @Column(name = "BatchNumber", nullable = true, length = 100)
    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    @Basic
    @Column(name = "ConditionID", nullable = true)
    public Integer getConditionId() {
        return conditionId;
    }

    public void setConditionId(Integer conditionId) {
        this.conditionId = conditionId;
    }

    @Basic
    @Column(name = "Remarks", nullable = true, length = 100)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Basic
    @Column(name = "StatusID", nullable = true)
    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    @Basic
    @Column(name = "MerchantBranch", nullable = true)
    public Long getMerchantBranch() {
        return merchantBranch;
    }

    public void setMerchantBranch(Long merchantBranch) {
        this.merchantBranch = merchantBranch;
    }

    @Basic
    @Column(name = "FileID", nullable = true)
    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    @Basic
    @Column(name = "TerminalID", nullable = true, length = 50)
    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    @Basic
    @Column(name = "NodeID", nullable = true, length = 50)
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    @Basic
    @Column(name = "Notes", nullable = true, length = 255)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Basic
    @Column(name = "TenderNum", nullable = true, length = 50)
    public String getTenderNum() {
        return tenderNum;
    }

    public void setTenderNum(String tenderNum) {
        this.tenderNum = tenderNum;
    }

    @Basic
    @Column(name = "ProjectID", nullable = true)
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "EbeBranchID", nullable = true)
    public Long getEbeBranchId() {
        return ebeBranchId;
    }

    public void setEbeBranchId(Long ebeBranchId) {
        this.ebeBranchId = ebeBranchId;
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

        PosEntity posEntity = (PosEntity) o;

        if (posid != posEntity.posid) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return this.posid.hashCode();
    }
}
