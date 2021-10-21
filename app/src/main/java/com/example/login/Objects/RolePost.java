package com.example.login.Objects;

public class RolePost {
    String name, description;

    public RolePost (String name, String desc) {
    this.name = name;
    this.description = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
