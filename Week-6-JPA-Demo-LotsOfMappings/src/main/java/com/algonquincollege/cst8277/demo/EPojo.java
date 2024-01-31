package com.algonquincollege.cst8277.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "E")
@Table(name = "TABLE_E")
public class EPojo {

    @Id
    @SequenceGenerator(name = "seq4e", sequenceName = "seq4e", initialValue = 14, allocationSize = 3)
    @GeneratedValue(generator = "seq4e")
    protected int id;

    @Column(name = "COL_1")
    protected String foo;

    //JPA likes default constructor
    public EPojo() {
    }
}