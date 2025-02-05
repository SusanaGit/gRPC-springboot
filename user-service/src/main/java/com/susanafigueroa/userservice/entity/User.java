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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBlance() {
        return blance;
    }

    public void setBlance(Integer blance) {
        this.blance = blance;
    }
}
