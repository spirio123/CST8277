package com.algonquincollege.cst8277.regionalInventory.model;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-01-10T17:48:14.248-0500")
@StaticMetamodel(InventoryPojo.class)
public class InventoryPojo_ {
	public static volatile SingularAttribute<InventoryPojo, LocalDateTime> created;
	public static volatile SingularAttribute<InventoryPojo, LocalDateTime> updated;
	public static volatile SingularAttribute<InventoryPojo, Integer> version;
	public static volatile SingularAttribute<InventoryPojo, Integer> id;
	public static volatile SingularAttribute<InventoryPojo, String> retailerName;
	public static volatile SingularAttribute<InventoryPojo, String> region;
	public static volatile SingularAttribute<InventoryPojo, Integer> inventoryLevel;
}
