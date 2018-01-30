package com.ebe.entities;

import lombok.*;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "sim")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SimEntity {
    @Id
    @Column(name = "sim_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sim_seq")
    @SequenceGenerator(name = "sim_seq", sequenceName = "sim_sim_id_seq", allocationSize = 1)
    private Long simId;

    @Column(name = "sim_number", nullable = false, length = 50)
    private String simNumber;

    @Column(name = "sim_vendor_id", nullable = false)
    private Integer simVendorId;

    @Column(name = "vendor_branch_id", nullable = true)
    private Long vendorBranchId;

    @Column(name = "sim_is_damage", nullable = true)
    private Byte isDamage;
}
