/*****************************************************************
 * File:  NewProfessorView.java Course materials (23W) CST8277
 *
 * @author Teddy Yap
 * @author Shahriar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.jsf;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;

import databank.model.ProfessorPojo;

/**
 * This class represents the scope of adding a new professor to the DB.
 * 
 * TODONE 09 - This class is a managed bean.  Use the name "newProfessor".<br>
 * TODONE 10 - Like previous assignment where ProfessorPojo was view scoped, this class is?<br>
 * TODONE 11 - Add the missing variables and their getters and setters.  Have in mind dates and version are internal
 * only.<br>
 * 
 */
@Named("newProfessor")
@SessionScoped
public class NewProfessorView implements Serializable {
	/** explicit set serialVersionUID */
	private static final long serialVersionUID = 1L;

	protected String lastName;
	protected String firstName;
	
	//Other Variables
	protected String email;
	protected String phoneNumber;
	protected LocalDateTime created;
	protected LocalDateTime updated;
	protected int version;
	protected String Major;
	protected String Degree;

	@Inject
	@ManagedProperty("#{professorController}")
	protected ProfessorController professorController;

	public NewProfessorView() {
	}

	/**
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * 
	 * @return email
	 */
	public String getEmail()
	{
		return email;
	}
	
	/**
	 * 
	 * @return phoneNumber
	 */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	
	/**
	 * 
	 * @return created
	 */
	public LocalDateTime getCreated()
	{
		return created;
	}
	
	/**
	 * 
	 * @return Updated
	 */
	public LocalDateTime getUpdated()
	{
		return updated;
	}
	
	/**
	 * 
	 * @return the version
	 */
	public int getVersion()
	{
		return version;
	}
	/**
	 * @param lastName lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	
	/**
	 * 
	 * @param email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	/**
	 * 
	 * @param phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * 
	 * @param creae to set from created
	 */
	public void setCreated(LocalDateTime creae)
	{
		this.created = creae;
	}
	/**
	 * 
	 * @param updated to set
	 */
	public void setUpdated(LocalDateTime updated)
	{
		this.updated = updated;
	}
	
	/**
	 * 
	 * @param version set the version
	 */
	public void setVersion (int version)
	{
		this.version = version;
	}
	
	/**
	 * 
	 * @return the Degree
	 */
	public String getDegree()
	{
		return Degree;
	}
	
	/**
	 * 
	 * @return the Major
	 */
	public String getMajor()
	{
		return Major;
	}
	/**
	 * 
	 * @param Degree to set
	 */
	public void setDegree(String Degree)
	{
		this.Degree = Degree;
	}
	
	/**
	 * 
	 * @param Major we set
	 */
	public void setMajor(String Major)
	{
		this.Major = Major;
	}
	
	public void addProfessor() {
		if (allNotNullOrEmpty(firstName, lastName,email,phoneNumber /* TODONE 11 - Don't forget to add the other variables that are not nullable */)) {
			ProfessorPojo theNewProfessor = new ProfessorPojo();
			theNewProfessor.setFirstName(getFirstName());
			theNewProfessor.setLastName(getLastName());
			//TODONE 12 - Call other setters
			theNewProfessor.setEmail(getEmail());
			theNewProfessor.setPhoneNumber(getPhoneNumber());
			
			//do time
			LocalDateTime Dt = LocalDateTime.now();
			setCreated(Dt);
			setVersion(1);
			//end time
			theNewProfessor.setCreated(getCreated());
			theNewProfessor.setVersion(getVersion());
			theNewProfessor.setDegree(getDegree());
			theNewProfessor.setMajor(getMajor());
			
			professorController.addNewProfessor(theNewProfessor);
			//Clean up
			professorController.toggleAdding();
			setFirstName(null);
			setLastName(null);
			//TODONE 13 - Set everything else to null
			setEmail(null);
			setPhoneNumber(null);
			setCreated(null);
			setVersion(0);
			setMajor(null);
			setDegree(null);
		}
	}

	static boolean allNotNullOrEmpty(final Object... values) {
		if (values == null) {
			return false;
		}
		for (final Object val : values) {
			if (val == null) {
				return false;
			}
			if (val instanceof String) {
				String str = (String) val;
				if (str.isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}
}
