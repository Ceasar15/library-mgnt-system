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
    private Actors role;


    public RoleType(Actors role) {
        this.role = role;
    }

    public RoleType() {
    }

    public Integer getId() {
        return id;
    }

    public Actors getRole() {
        return role;
    }

    public void setRole(Actors role) {
        this.role = role;
    }


}
