package com.ebe.entities;


import javax.persistence.*;

import lombok.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "project")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
    @SequenceGenerator(name = "project_seq", sequenceName = "project_project_id_seq", allocationSize = 1)
    @Column(name = "project_id")
    private Long id;

    @Column(name = "project_name", nullable = false)
    @NonNull
    private String name;
}
