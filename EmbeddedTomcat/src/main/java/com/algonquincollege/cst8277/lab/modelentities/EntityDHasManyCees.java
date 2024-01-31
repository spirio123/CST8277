/**
 * File:  EntityDHasManyCees.java
 * Course materials CST 8277
 * 
 * @author Mike Norman
 *
 */
package com.algonquincollege.cst8277.lab.modelentities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@SuppressWarnings("unused")

//TODO - Add annotation so that only non-null fields are in JSON body
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EntityDHasManyCees extends PojoBase implements Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;
    
    protected String name;
    protected Set<EntityCHasManyDees> cees = new HashSet<>();

    public EntityDHasManyCees() {
        super();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //TODO - Add annotation to use a custom Serializer for JSON body
    @JsonSerialize(using = SetOfEntityCeesSerializer.class)
    public Set<EntityCHasManyDees> getCees() {
        return cees;
    }

    public void setCees(Set<EntityCHasManyDees> cees) {
        this.cees = cees;
    }

}