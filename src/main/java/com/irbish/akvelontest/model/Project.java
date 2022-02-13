package com.irbish.akvelontest.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(name = "startdate")
    private Timestamp startDate;

    @Column(name = "finishdate")
    private Timestamp finishDate;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    private int priority;

}
