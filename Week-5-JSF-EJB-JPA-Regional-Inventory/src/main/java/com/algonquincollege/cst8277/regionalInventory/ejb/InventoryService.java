/*****************************************************************
 * File: InventoryService.java Course materials CST 8277
 *
 * @author Teddy Yap
 * @author (original) Mike Norman
 *
 */
package com.algonquincollege.cst8277.regionalInventory.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import com.algonquincollege.cst8277.regionalInventory.model.InventoryPojo;

/**
 * Stateless Session Bean - InventoryService
 */
//@Stateless
@Singleton
public class InventoryService implements Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext( name = "regionalInventory-PU")
	protected EntityManager em;

	/**
	 * Default constructor.
	 */
	public InventoryService() {
	}

	public List< InventoryPojo> findAllInventorys() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery< InventoryPojo> cq = cb.createQuery( InventoryPojo.class);
		cq.select( cq.from( InventoryPojo.class));
		return em.createQuery( cq).getResultList();
	}

	public List< InventoryPojo> findAllInventorysForRegion( String region) {
		TypedQuery< InventoryPojo> tq = em.createQuery( "select i from Inventory i where i.region = :param1",
				InventoryPojo.class);
		tq.setParameter( "param1", region);
		return tq.getResultList();
	}

	@Transactional
	public InventoryPojo persistInventory( InventoryPojo inventory) {
		em.persist( inventory);
		return inventory;
	}

	public InventoryPojo findInventoryByPrimaryKey( int empPK) {
		return em.find( InventoryPojo.class, empPK);
	}

	@Transactional
	//also used for updating
	public void mergeInventory( InventoryPojo inventoryWithUpdates) {
		InventoryPojo inventoryToBeUpdated = findInventoryByPrimaryKey( inventoryWithUpdates.getId());
		if ( inventoryToBeUpdated != null) {
			em.merge( inventoryWithUpdates);
		}
	}

	@Transactional
	public void removeInventory( int inventoryId) {
		InventoryPojo inventory = findInventoryByPrimaryKey( inventoryId);
		if ( inventory != null) {
			em.refresh( inventory);
			em.remove( inventory);
		}
	}

}