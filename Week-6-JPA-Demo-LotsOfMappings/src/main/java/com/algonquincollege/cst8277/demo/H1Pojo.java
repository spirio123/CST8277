package com.algonquincollege.cst8277.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "H1")
@Table(name = "TABLE_H1")
public class H1Pojo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Enumerated // default is ORDINAL
    @Column(name = "COL_SOMETHING", columnDefinition = "int")
    protected SomeEnum foo;

    //JPA likes default constructor
    public H1Pojo() {
    }
}