package com.tomoncle.app.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "t_major")
public class Major {
    @Id
    private Integer id;

    private String name;

    private String description;

}