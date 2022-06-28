package com.capstone.library.model;


import javax.persistence.*;

@Entity
@Table(name = "user_type")
public class RoleType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Actors name;


    public RoleType(Actors name) {
        this.name = name;
    }

    public RoleType() {
    }

    public Integer getId() {
        return id;
    }

    public Actors getName() {
        return name;
    }

    public void setName(Actors name) {
        this.name = name;
    }


}
