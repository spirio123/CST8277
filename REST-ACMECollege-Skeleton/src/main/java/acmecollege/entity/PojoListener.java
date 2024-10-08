/***************************************************************************
 * File:  PojoListener.java Course materials (23W) CST 8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author (original) Mike Norman
 *
 */
package acmecollege.entity;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@SuppressWarnings("unused")

public class PojoListener {

	// TODO PL01 - What annotation is used when we want to do something just before object is INSERT'd in the database?
	@PrePersist
	public void setCreatedOnDate(PojoBase pojoBase) {
		// TODO PL02 - What member field(s) do we wish to alter just before object is INSERT'd in the database?
		LocalDateTime now = LocalDateTime.now();
		pojoBase.setCreated(now);
	}

	// TODO PL03 - What annotation is used when we want to do something just before object is UPDATE'd in the database?
	@PreUpdate
	public void setUpdatedDate(PojoBase pojoBase) {
		// TODO PL04 - What member field(s) do we wish to alter just before object is UPDATE'd in the database?
		LocalDateTime now = LocalDateTime.now();
		pojoBase.setUpdated(now);
	}

}
