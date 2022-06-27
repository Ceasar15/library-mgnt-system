package com.capstone.library.model;

import javax.persistence.*;

@Entity
@Table(name = "catalogue")
public class Catalogue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "catalogue")
    private String catalogue;

    public Catalogue() {

    }

    public Catalogue(String catalogue) {
        this.catalogue = catalogue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(String catalogue) {
        this.catalogue = catalogue;
    }

    @Override
    public String toString() {
        return "Catalogue " + catalogue + " with id " + id + " ";
    }
}