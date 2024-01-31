/***************************************************************************
 * File:  ProjectPojo.java
 * Course materials CST 8277
 * @author Mike Norman
 * (Modified) @date 2020 04
 *
 * Copyright (c) 1998, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Original @authors dclarke, mbraeuer
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Project class
 *
 */
@Entity(name = "Project")
@Table(name = "PROJECT")
@AttributeOverride(name = "id", column = @Column(name="PROJ_ID"))
public class ProjectPojo extends PojoBase implements Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;

    protected String description;
    protected String name;
    protected Set<EmployeePojo> employees = new HashSet<>();

    // JPA requires each @Entity class have a default constructor
    public ProjectPojo() {
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "projects")  // Note: can be List<EmployeePojo>. but JPA generates more efficient SQL for Set
    public Set<EmployeePojo> getEmployees() {
        return employees;
    }
    public void setEmployees(Set<EmployeePojo> employees) {
        this.employees = employees;
    }
    public void addEmployee(EmployeePojo employee) {
        if (employee != null) {
            getEmployees().add(employee);
            employee.getProjects().add(this);
        }
    }

}