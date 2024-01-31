package com.algonquincollege.cst8277.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "F")
@Table(name = "TABLE_F")
public class FPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE) // same as AUTO
    protected int id;

    @Column(name = "COL_1")
    protected String foo;

    //JPA likes default constructor
    public FPojo() {
    }
}