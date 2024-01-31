/***************************************************************************
 * File:  HomePhone.java Course materials CST 8277
 * 
 * @author Mike Norman
 * @date 2020 04
 *
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
//@Table( name = "H_PHONES")
//@PrimaryKeyJoinColumn(name = "H_PHONE_ID")
@DiscriminatorValue( "H")
public class HomePhone extends PhonePojo implements Serializable {
	/** explicit set serialVersionUID */
	private static final long serialVersionUID = 1L;

	protected String directions;

	public HomePhone() {
		super();
	}

	@Column( name = "MAP_COORDS")
	public String getDirections() {
		return directions;
	}

	public void setDirections( String directions) {
		this.directions = directions;
	}

}