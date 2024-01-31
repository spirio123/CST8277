package databank.model;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-12-25T16:55:38.139-0500")
@StaticMetamodel(ProfessorPojo.class)
public class ProfessorPojo_ {
	public static volatile SingularAttribute<ProfessorPojo, Integer> id;
	public static volatile SingularAttribute<ProfessorPojo, String> lastName;
	public static volatile SingularAttribute<ProfessorPojo, String> firstName;
	public static volatile SingularAttribute<ProfessorPojo, String> email;
	public static volatile SingularAttribute<ProfessorPojo, String> phoneNumber;
	public static volatile SingularAttribute<ProfessorPojo, LocalDateTime> created;
	public static volatile SingularAttribute<ProfessorPojo, LocalDateTime> updated;
	public static volatile SingularAttribute<ProfessorPojo, Integer> version;
	public static volatile SingularAttribute<ProfessorPojo, Boolean> editable;
}
