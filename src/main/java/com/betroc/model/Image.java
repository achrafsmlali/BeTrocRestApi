package com.betroc.model;

import com.betroc.listener.EnityImageListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@EntityListeners(EnityImageListener.class)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
