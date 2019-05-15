package com.tomoncle.app.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_user")
public class User {
    @Id
    private Integer id;

    private String username;

    private String password;

}