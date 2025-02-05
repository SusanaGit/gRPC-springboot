package com.susanafigueroa.userservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class User {

    @Id
    private Integer id;
    private String name;
    private Integer blance;
}
