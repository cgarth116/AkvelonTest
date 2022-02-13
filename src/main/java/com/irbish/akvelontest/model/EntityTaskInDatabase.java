package com.irbish.akvelontest.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class EntityTaskInDatabase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long    id;

    @Column(name = "projectid")
    private long    projectId;

    @Column(columnDefinition = "json")
    private String  task;

}
