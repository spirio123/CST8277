package example;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entity.PersonPojo;

public class HibernateExample02 {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public static void main(String[] args) {
		Configuration config = new Configuration().addAnnotatedClass(PersonPojo.class) // load the entities
				.configure("hibernate.cfg.xml"); // load additional settings

		ServiceRegistry sR = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
		SessionFactory factory = config.buildSessionFactory(sR);
		logger.info("SessionFactory built");
		
		// Get an entity manager from the factory
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		
		// Create a named query
		Query< PersonPojo> query = session.< PersonPojo>createNamedQuery("Person.findAll", PersonPojo.class);
		List< PersonPojo> result = query.getResultList();

		logger.info("Result of the first named query");
		result.stream().forEach(person ->  logger.info("{}", person.toString()));
		
		// Create another named query		
		query = session.<PersonPojo>createNamedQuery("Person.findById", PersonPojo.class).setParameter("id", 5);
		result = query.getResultList();

		logger.info("Result of the second named query:");
		result.stream().forEach(person ->  logger.info("{}", person.toString()));

		// Create a person object
		PersonPojo p1 = new PersonPojo();
		p1.setFirstName("Mike");
		p1.setLastName("Norman");
		p1.setEmail("normanm@algonquincollege.com");
		p1.setCreated(LocalDateTime.now());
		p1.setCity("Ottawa");
		p1.setPhoneNumber("6131964543");

		query = session.<PersonPojo>createNamedQuery("Person.findByName",
			PersonPojo.class).setParameter("firstName", "Mike").setParameter("lastName", "Norman");
		result = query.getResultList();
		
		// Check if person already exists in database, if not, save it to the database
		int theId;
		if (result.isEmpty()) {
			logger.info("Person doesn't exist!");
			logger.info("Adding new person");
			theId = (int) session.save(p1);
			logger.info("Done saving person");
		}
		else {
			theId = result.get(0).getId();
			logger.info("Person already exist!");
		}
		
		// Create non-named query using HQL (Hibernate Query Language)
		Query< PersonPojo> nonNameQuery = session.createQuery("from PersonPojo", PersonPojo.class);
		List<PersonPojo> resultToo = nonNameQuery.getResultList();
		
		logger.info("Result of HQL query:");
		resultToo.stream().forEach(person ->  logger.info("{}", person.toString()));

		// Retrieve a specific person from database
		PersonPojo aPerson = session.get(PersonPojo.class, theId);
		logger.info("Person with Id = {}", theId);
		if (aPerson != null)
			logger.info(aPerson.toString());
		
		session.close();
		factory.close();
	}

}