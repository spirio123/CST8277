/******************************************************
 * File: PersonPojo.java
 * Course materialsCST8277
 *
 * @author Mike Norman
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * Description: model for the Person object
 */
@Entity
@Table(name = "PERSON", catalog = "databank", schema = "")
@NamedQuery(name = "Person.findAll", query = "SELECT a FROM PersonPojo a")
@NamedQuery(name = "Person.findById", query = "SELECT a FROM PersonPojo a WHERE a.id = :id")
@NamedQuery(name = "Person.findByName", query = "SELECT a FROM PersonPojo a WHERE a.firstName = :firstName AND a.lastName = :lastName")
public class PersonPojo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "phone")
	private String phoneNumber;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "created")
	private LocalDateTime epochCreated;

	@Transient
	private String fullName;

	public int getId() {
		return id;
	}

	/**
	 * @param id new value for id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the value for lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName new value for lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the value for firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName new value for firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}

	public LocalDateTime getCreated() {
		return epochCreated;
	}
	
	public void setCreated(LocalDateTime created) {
		this.epochCreated = created;
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
         * Technically, 'otherPersonPojo' is a <i>pattern</i> that becomes an in-scope variable binding.
         * Note: need to watch out just-in-case there is already a 'otherPersonPojo' variable in-scope!
		 */
		if (obj instanceof PersonPojo otherPersonPojo) {
			// see comment (above) in hashCode(): compare using only member variables that are
			// truely part of an object's identity
			return Objects.equals(this.getId(), otherPersonPojo.getId());
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Person [id=").append(id).append(", ");
		if (lastName != null) {
			builder.append("lastName=").append(lastName).append(", ");
		}
		if (firstName != null) {
			builder.append("firstName=").append(firstName).append(", ");
		}
		if (email != null) {
			builder.append("email=").append(email).append(", ");
		}
		if (phoneNumber != null) {
			builder.append("phoneNumber=").append(phoneNumber).append(", ");
		}
		if (city != null) {
			builder.append("city=").append(city).append(", ");
		}
		if (epochCreated != null) {
			builder.append("created=").append(epochCreated);
		}
		builder.append("]");
		return builder.toString();
	}

}