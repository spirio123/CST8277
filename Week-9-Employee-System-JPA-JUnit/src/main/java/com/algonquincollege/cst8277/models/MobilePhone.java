/***************************************************************************
 * File:  MobilePhone.java Course materials CST 8277
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
//@Table(name = "M_PHONES")
//@PrimaryKeyJoinColumn(name = "M_PHONE_ID")
@DiscriminatorValue( "M")
public class MobilePhone extends PhonePojo implements Serializable {
	/** explicit set serialVersionUID */
	private static final long serialVersionUID = 1L;

	protected String provider;

	public MobilePhone() {
		super();
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider( String provider) {
		this.provider = provider;
	}
}