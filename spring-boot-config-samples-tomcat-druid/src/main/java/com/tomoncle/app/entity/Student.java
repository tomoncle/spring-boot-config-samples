package com.tomoncle.app.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "t_student")
public class Student {
    @Id
    private Integer id;

    private Integer no;

    private String username;

    @Column(name = "major_id")
    private Integer majorId;
}