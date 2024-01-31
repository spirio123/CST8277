package com.algonquincollege.cst8277.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name = "B")
@Table(name = "TABLE_B")
public class BPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    @Column(name = "COL_1")
    protected String foo;
    
    @Transient
    protected String bar;

    //JPA likes default constructor
    public BPojo() {
    }
}