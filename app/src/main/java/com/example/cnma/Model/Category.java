package com.example.cnma.Model;

import java.io.Serializable;

public class Category implements Serializable {
    Integer id;
    String name;

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
