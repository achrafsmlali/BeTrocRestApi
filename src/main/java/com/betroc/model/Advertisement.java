package com.betroc.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance
public abstract class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String email;

    public Advertisement(){

    }

    public Advertisement(@NotNull String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
