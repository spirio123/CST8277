/***************************************************************************
 * File:  WorkPhone.java Course materials CST 8277
 * 
 * @author Mike Norman
 * @date 2020 04
 *
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
//@Table( name = "W_PHONES")
//@PrimaryKeyJoinColumn(name = "W_PHONE_ID")
@DiscriminatorValue( "W")
public class WorkPhone extends PhonePojo implements Serializable {
	/** explicit set serialVersionUID */
	private static final long serialVersionUID = 1L;

	protected String department;

	public WorkPhone() {
		super();
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment( String department) {
		this.department = department;
	}
}