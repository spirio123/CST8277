package example;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entity.PersonPojo;

public class JPAExample {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public static void main(String[] args) {
		
		logger.info("building EntityManagerFactory (similar to Hibernate ServiceRegistry)");
		logger.info("Note: no hibernate.cfg.xml used, replaced by JPA Standard META-INF/persistence.xml");
		// Get factory with the unit name defined in persistence.xml
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Example");
		
		// Get an entity manager from the factory
		logger.info("building EntityManager (similar to Hibernate SessionFactory)");
		EntityManager em = emFactory.createEntityManager();
		
		// A simple query doesn't need a transaction
		TypedQuery< PersonPojo> query = em.< PersonPojo>createNamedQuery("Person.findAll", PersonPojo.class);

		List< PersonPojo> result = query.getResultList();

		result.stream().forEach(person -> logger.info("{}", person.toString()));

		emFactory.close();
	}
}
