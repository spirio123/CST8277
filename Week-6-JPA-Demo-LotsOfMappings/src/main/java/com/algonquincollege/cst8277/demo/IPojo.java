package com.algonquincollege.cst8277.demo;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity(name = "I")
@Table(name = "TABLE_I")
public class IPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    protected String description;
    
    @ElementCollection
    @CollectionTable(
          name="TODOS",
          joinColumns=@JoinColumn(name="OWNER_ID")
    )
    @Column(name="REMINDERS")
    protected List<String> reminders;

}