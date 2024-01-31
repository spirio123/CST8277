/*****************************************************************
 * File:  InventoryDao.java Course materials CST 8277
 * 
 * @author Teddy Yap
 * @author Mike Norman
 *
 */
package com.algonquincollege.cst8277.regionalInventory.dao;

import java.util.List;

import com.algonquincollege.cst8277.regionalInventory.model.InventoryPojo;

public interface InventoryDao {

	// TODO - add more to this interface

	// Create

	// R - read all inventory for the specified region
	List< InventoryPojo> readAllInventoryForRegion( String region);
	// R - read a specific inventory

	// Update
	
	// Delete
	
}