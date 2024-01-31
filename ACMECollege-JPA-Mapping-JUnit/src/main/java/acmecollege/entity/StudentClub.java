/***************************************************************************
 * File:  StudentClub.java Course materials (23W) CST 8277
 * 
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @date August 28, 2022
 * 
 */
package acmecollege.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the student_club database table.
 */
//TODO SC01 - Add the missing annotations.
//TODO SC02 - StudentClub has subclasses AcademicStudentClub and NonAcademicStudentClub.  Look at week 9 slides for InheritanceType.
//TODO SC03 - Do we need a mapped super class?  If so, which one?
@Entity
@Table(name = "student_club")
@AttributeOverride(name="id", column=@Column(name = "club_id"))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "academic", columnDefinition = "bit(1)", discriminatorType = DiscriminatorType.INTEGER)
@NamedQuery(name = StudentClub.ALL_STUDENT_CLUBS_QUERY_NAME, query = "SELECT DISTINCT sc FROM StudentClub sc LEFT JOIN FETCH sc.clubMemberships")
@NamedQuery(name = StudentClub.SPECIFIC_STUDENT_CLUB_QUERY_NAME, query = "SELECT DISTINCT sc FROM StudentClub sc LEFT JOIN FETCH sc.clubMemberships WHERE sc.id = :param1")
@NamedQuery(name = StudentClub.IS_DUPLICATE_QUERY_NAME, query = "SELECT COUNT(sc) FROM StudentClub sc WHERE sc.name = :param1")
public abstract class StudentClub extends PojoBase implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String ALL_STUDENT_CLUBS_QUERY_NAME = "StudentClub.findAll";
	public static final String SPECIFIC_STUDENT_CLUB_QUERY_NAME = "StudentClub.findByName";
	public static final String IS_DUPLICATE_QUERY_NAME = "StudentClub.isDuplicate";

	// TODO SC04 - Add the missing annotations.
	@Basic(optional = false)
	@Column(name = "name", nullable = false, length = 100)
	private String name;

	// TODO SC05 - Add the 1:M annotation.  This list should be effected by changes to this object (cascade).
	@OneToMany(mappedBy = "club", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<ClubMembership> clubMemberships = new HashSet<>();

	public StudentClub() {
	}

	public Set<ClubMembership> getClubMemberships() {
		return clubMemberships;
	}

	public void setClubMembership(Set<ClubMembership> clubMemberships) {
		this.clubMemberships = clubMemberships;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	//Inherited hashCode/equals is NOT sufficient for this entity class
	
	/**
	 * Very important:  Use getter's for member variables because JPA sometimes needs to intercept those calls<br/>
	 * and go to the database to retrieve the value
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		// Only include member variables that really contribute to an object's identity
		// i.e. if variables like version/updated/name/etc. change throughout an object's lifecycle,
		// they shouldn't be part of the hashCode calculation
		
		// The database schema for the STUDENT_CLUB table has a UNIQUE constraint for the NAME column,
		// so we should include that in the hash/equals calculations
		
		return prime * result + Objects.hash(getId(), getName());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		
		if (obj instanceof StudentClub otherStudentClub) {
			// See comment (above) in hashCode():  Compare using only member variables that are
			// truly part of an object's identity
			return Objects.equals(this.getId(), otherStudentClub.getId()) &&
				Objects.equals(this.getName(), otherStudentClub.getName());
		}
		return false;
	}
}
