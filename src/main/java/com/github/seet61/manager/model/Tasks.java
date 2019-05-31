package com.github.seet61.manager.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="Tasks")
@Data
public class Tasks {
    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false)
    private Long id;
    @Column(name="name", unique=true)
    private String name;
    @Column(name="status")
    private String status;
}
