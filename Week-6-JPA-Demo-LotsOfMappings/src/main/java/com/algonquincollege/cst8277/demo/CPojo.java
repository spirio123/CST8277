package com.algonquincollege.cst8277.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "C")
@Table(name = "TABLE_C")
public class CPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    @Column(name = "COL_1")
    protected String foo;

    //JPA likes default constructor
    public CPojo() {
    }
}