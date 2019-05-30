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
    private String name;
    private String status;
}
