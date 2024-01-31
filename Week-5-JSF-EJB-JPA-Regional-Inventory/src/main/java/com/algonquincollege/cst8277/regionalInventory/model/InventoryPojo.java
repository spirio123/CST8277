/*****************************************************************
 * File:  InventoryPojo.java Course materials CST 8277
 * 
 * @author Teddy Yap
 * @author (original) Mike Norman
 *
 */
package com.algonquincollege.cst8277.regionalInventory.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.faces.view.ViewScoped;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

/*
 * this scope is not like @SessionScoped or @RequestScoped about *how many* instances or *how long* they are within
 * scope in the app. Instead, it is about *where* the object is in scope, which is the view - essentially even though it
 * is *defined* in Java, @ViewScoped objects really belong to the XHTML part of the app.
 */
@ViewScoped
@Entity( name = "Inventory")
//@Table( name = "regional_inventory", catalog = "cst8277")
@Table( name = "regional_inventory", catalog = "databank")
@Access( AccessType.PROPERTY)
@EntityListeners( { InventoryPojoListener.class })
@NamedQuery( name = InventoryPojo.INVENTORY_FIND_ALL, query = "SELECT a FROM Inventory a")
@NamedQuery( name = InventoryPojo.INVENTORY_FIND_ID, query = "SELECT a FROM Inventory a WHERE a.id = :id")
public class InventoryPojo implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String INVENTORY_FIND_ALL = "Inventory.findAll";
	public static final String INVENTORY_FIND_ID = "Inventory.findId";

	private int id;
	private String retailerName;
	private String region;
	private int level;
	private int version = 1;
	private LocalDateTime updated;
	private LocalDateTime created;
	private boolean editable;

	@Transient
	public boolean getEditable() {
		return editable;
	}

	public void setEditable( boolean editable) {
		this.editable = editable;
	}

	@Column( name = "created")
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated( LocalDateTime created) {
		this.created = created;
	}

	@Column( name = "updated")
	public LocalDateTime getUpdated() {
		return updated;
	}
	public void setUpdated( LocalDateTime updated) {
		this.updated = updated;
	}

	@Version
	public int getVersion() {
		return version;
	}

	public void setVersion( int version) {
		this.version = version;
	}

	@Id
	// @GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column( name = "INV_ID")
	public int getId() {
		return id;
	}

	public void setId( int id) {
		this.id = id;
	}

	@Column( name = "RETAILER_NAME")
	public String getRetailerName() {
		return retailerName;
	}

	public void setRetailerName( String retailerName) {
		this.retailerName = retailerName;
	}

	@Column( name = "REGION")
	public String getRegion() {
		return region;
	}

	public void setRegion( String firstName) {
		this.region = firstName;
	}

	@Column( name = "CURR_INV_LEVEL")
	public int getInventoryLevel() {
		return level;
	}

	public void setInventoryLevel( int level) {
		this.level = level;
	}

	/**
	 * Very important: use getter's for member variables because JPA sometimes needs to intercept those calls<br/>
	 * and go to the database to retrieve the value
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		// only include member variables that really contribute to an object's identity
		// i.e. if variables like version/updated/name/etc. change throughout an object's lifecycle,
		// they shouldn't be part of the hashCode calculation
		return prime * result + Objects.hash(getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}

		/* enhanced instanceof - yeah!
		 * As of JDK 14, no need for additional 'silly' cast:
		    if (animal instanceof Cat) {
		        Cat cat = (Cat)animal;
		        cat.meow();
                // other class Cat operations ...
            }
         * Technically, 'pjBase' is a <i>pattern</i> that becomes an in-scope variable binding.
         * Note: need to watch out just-in-case there is already a 'pjBase' variable in-scope!
		 */
		if (obj instanceof InventoryPojo invPojo) {
			// see comment (above) in hashCode(): compare using only member variables that are
			// truely part of an object's identity
			return Objects.equals(this.getId(), invPojo.getId());
		}
		return false;
	}

}