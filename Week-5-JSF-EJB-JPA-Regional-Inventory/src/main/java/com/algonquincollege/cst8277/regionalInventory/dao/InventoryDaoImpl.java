/*****************************************************************
 * InventoryDaoImpl.java Course materials CST 8277
 * 
 * @author Teddy Yap
 * @author Mike Norman
 *
 */
package com.algonquincollege.cst8277.regionalInventory.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.algonquincollege.cst8277.regionalInventory.ejb.InventoryService;
import com.algonquincollege.cst8277.regionalInventory.model.InventoryPojo;

@Named
@ApplicationScoped
public class InventoryDaoImpl implements InventoryDao, Serializable {
	/** explicit set serialVersionUID */
	private static final long serialVersionUID = 1L;

	@EJB
	protected InventoryService inventoryService;

	@Override
	public List< InventoryPojo> readAllInventoryForRegion( String region) {
		return inventoryService.findAllInventorysForRegion( region);
	}

	//TODO - fill in rest of C-R-U-D methods

	
	
}