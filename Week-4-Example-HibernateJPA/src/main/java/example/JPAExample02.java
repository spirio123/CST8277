package example;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entity.PersonPojo;

public class JPAExample02 {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public static void main( String[] args) {
		
		logger.info("building EntityManagerFactory (similar to Hibernate ServiceRegistry)");
		logger.info("Note: no hibernate.cfg.xml used, replaced by JPA Standard META-INF/persistence.xml");
		// Get factory with the unit name defined in persistence.xml
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Example");

		// Get an entity manager from the factory
		logger.info("building EntityManager (similar to Hibernate SessionFactory)");
		EntityManager em = emFactory.createEntityManager();
		
		/*
		 *  More complicated than JPAExample's simple read-all query
		 *  We intend to make changes to the DB, need a Transaction for that
		 *  Similar to Hibernate's Session beginTransaction()
		 */
		em.getTransaction().begin();
		
		// Create a new PersonPojo
		PersonPojo p1 = new PersonPojo();
		p1.setFirstName("Mike");
		p1.setLastName("Norman");
		p1.setEmail("normanm@algonquincollege.com");
		p1.setCreated(LocalDateTime.now());
		p1.setCity("Ottawa");
		p1.setPhoneNumber("6131964543");
		 
		// Get a JPQL builder, a sql in OOP manner
		CriteriaBuilder builder = em.getCriteriaBuilder();
		
		// Create a query condition for given class
		CriteriaQuery< PersonPojo> criteria = builder.createQuery(PersonPojo.class);
		
		// Get the root table with entity class that has the data type
		Root< PersonPojo> root = criteria.from(PersonPojo.class);
		
		// Select everything where the email column is same as p1.getEmail()
		criteria.select(root).where(builder.equal(root.get( "email"), p1.getEmail()));
		
		// Get all matching rows
		List<PersonPojo> exists = em.createQuery(criteria).getResultList();
		
		if (exists.isEmpty()) {
			logger.info("Adding {}:", p1.toString());
			// Add person
			em.persist(p1);
		} else {
			logger.info("Deleting already existing {}: ", p1);
			// Save the person to manager
			p1.setId(exists.get(0).getId());
			PersonPojo merge = em.merge(p1);
			// Remove the managed person
			em.remove(merge);
		}
		
		// Commit changes to DB
		em.getTransaction().commit();
		
		// Create a query condition for given class
		criteria = builder.createQuery(PersonPojo.class);
		// Select everything from the DB
		criteria.select(criteria.from(PersonPojo.class));
		// Execute query with created criteria and store result in list
		List<PersonPojo> all = em.createQuery(criteria).getResultList();
		// Create a query condition for given class
		criteria = builder.createQuery(PersonPojo.class);
		// Select everything where the name column contains 'le'
		criteria.select(criteria.from( PersonPojo.class)).where(builder.like(root.get("firstName"), "%le%"));
		// Execute query with created criteria and store result in list
		List<PersonPojo> filtered = em.createQuery(criteria).getResultList();
		// Close manager, transaction is now closed
		em.close();
		// Print both lists
		all.stream().forEach(person -> logger.info("{}", person.toString()));
		filtered.stream().forEach(person -> logger.info("{}", person.toString()));

		// JPA is shutoff
		em.close();
	}
}
