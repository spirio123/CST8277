package com.algonquincollege.cst8277.models;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2021-12-25T14:20:16.923-0500")
@StaticMetamodel(EmployeeTask.class)
public class EmployeeTask_ {
	public static volatile SingularAttribute<EmployeeTask, String> description;
	public static volatile SingularAttribute<EmployeeTask, LocalDateTime> taskStart;
	public static volatile SingularAttribute<EmployeeTask, LocalDateTime> taskEnd;
	public static volatile SingularAttribute<EmployeeTask, Boolean> taskDone;
}
