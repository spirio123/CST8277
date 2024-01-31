package com.algonquincollege.cst8277.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "G")
@Table(name = "TABLE_G")
public class GPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(name = "COL_2", columnDefinition = "TINYINT")
    protected short bar;

    //JPA likes default constructor
    public GPojo() {
    }
}