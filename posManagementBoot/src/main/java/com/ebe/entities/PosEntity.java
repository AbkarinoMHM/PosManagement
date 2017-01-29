package com.ebe.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "pos")
public class PosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pos_seq")
    @SequenceGenerator(name = "pos_seq", sequenceName = "pos_pos_id_seq", allocationSize = 1)
    @Column(name = "pos_id", nullable = false)
    private Long id;
    @Column(name = "pos_serial_number", nullable = false)
    private String serialNumber;
    @ManyToOne(optional = false)
    @JoinColumn(name = "pos_vendor_id", referencedColumnName = "pos_vendor_id")
    private PosVendorEntity vendor;
    @Column(name = "pos_model")
    private String model;
    @ManyToOne(optional = false)
    @JoinColumn(name = "pos_type_id", referencedColumnName = "pos_type_id")
    private PosTypeEntity type;
    @Column(name = "pos_made")
    private String made;
    @Column(name = "pos_part_number")
    private String partNumber;
    @Column(name = "pos_other_features")
    private String otherFeatures;
    @Column(name = "pos_batch_number")
    private String batchNumber;
    @ManyToOne(optional = false)
    @JoinColumn(name = "pos_condition_id", referencedColumnName = "pos_condition_id")
    private PosConditionEntity condition;
    @Column(name = "pos_remarks")
    private String remarks;
    @ManyToOne(optional = false)
    @JoinColumn(name = "pos_status_id", referencedColumnName = "pos_status_id")
    private PosStatusEntity status;
    @Column(name = "pos_file_id")
    private Long file;
    @Column(name = "pos_terminal_id")
    private String terminal;
    @Column(name = "pos_node_id")
    private String node;
    @Column(name = "pos_tender_num")
    private String tender;
    @ManyToOne(optional = false)
    @JoinColumn(name = "pos_project_id", referencedColumnName = "project_id")
    private ProjectEntity project;
    @ManyToOne(optional = false)
    @JoinColumn(name = "pos_vendor_branch_id", referencedColumnName = "vendor_branch_id")
    private VendorBranchEntity vendorBranch;
    @Column(name = "pos_timestamp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date timestamp;

    @PrePersist
    protected void onCreate() {
        this.timestamp = new Date();
    }
    public PosEntity() {
    }

    public PosEntity(String serialNumber, PosVendorEntity vendor, PosTypeEntity type, PosConditionEntity condition,
                     PosStatusEntity status, MerchantBranchEntity merchantBranch, ProjectEntity project,
                     VendorBranchEntity vendorBranch) {
        this.serialNumber = serialNumber;
        this.vendor = vendor;
        this.type = type;
        this.condition = condition;
        this.status = status;
        this.project = project;
        this.vendorBranch = vendorBranch;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public PosVendorEntity getVendor() {
        return vendor;
    }

    public void setVendor(PosVendorEntity vendor) {
        this.vendor = vendor;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public PosTypeEntity getType() {
        return type;
    }

    public void setType(PosTypeEntity type) {
        this.type = type;
    }

    public String getMade() {
        return made;
    }

    public void setMade(String made) {
        this.made = made;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getOtherFeatures() {
        return otherFeatures;
    }

    public void setOtherFeatures(String otherFeatures) {
        this.otherFeatures = otherFeatures;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public PosConditionEntity getCondition() {
        return condition;
    }

    public void setCondition(PosConditionEntity condition) {
        this.condition = condition;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public PosStatusEntity getStatus() {
        return status;
    }

    public void setStatus(PosStatusEntity status) {
        this.status = status;
    }

    public Long getFile() {
        return file;
    }

    public void setFile(Long file) {
        this.file = file;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }


    public String getTender() {
        return tender;
    }

    public void setTender(String tender) {
        this.tender = tender;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public VendorBranchEntity getVendorBranch() {
        return vendorBranch;
    }

    public void setVendorBranch(VendorBranchEntity vendorBranch) {
        this.vendorBranch = vendorBranch;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PosEntity posEntity = (PosEntity) o;

        return id.equals(posEntity.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
