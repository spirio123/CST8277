package com.algonquincollege.cst8277.regionalInventory.model;

import static java.time.LocalDateTime.now;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class InventoryPojoListener {

	@PrePersist
	public void setCreatedOnDate( InventoryPojo inventory) {
		inventory.setCreated(now());
		//might as well call setUpdatedDate as well
		inventory.setUpdated(now());
	}

	@PreUpdate
	public void setUpdatedDate( InventoryPojo inventory) {
		inventory.setUpdated(now());
	}
}
