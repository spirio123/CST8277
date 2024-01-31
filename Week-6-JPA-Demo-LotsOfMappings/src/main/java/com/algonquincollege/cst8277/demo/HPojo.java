package com.algonquincollege.cst8277.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "H")
@Table(name = "TABLE_H")
public class HPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "COL_SOMETHING", columnDefinition = "VARCHAR(20)")
    protected SomeEnum foo;

    //JPA likes default constructor
    public HPojo() {
    }
}