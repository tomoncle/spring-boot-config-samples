package com.tomoncle.app.entity;

import lombok.Data;

import javax.persistence.*;

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