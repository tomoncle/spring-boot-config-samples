package com.tomoncle.app.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "t_user")
public class User {
    @Id
    private Integer id;

    private String username;

    private String password;

}