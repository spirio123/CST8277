package example;

import java.lang.invoke.MethodHandles;
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

public class HibernateExample {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public static void main(String[] args) {
		Configuration config = new Configuration().addAnnotatedClass(PersonPojo.class) // Load the entities
				.configure("hibernate.cfg.xml"); // Load additional settings

		ServiceRegistry sR = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
		SessionFactory factory = config.buildSessionFactory(sR);
		logger.info("SessionFactory built");
		
		// Get an entity manager from the factory
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Query<PersonPojo> query = session.< PersonPojo>createNamedQuery("Person.findAll", PersonPojo.class);
		List<PersonPojo> result = query.getResultList();

		result.stream().forEach(person -> logger.info("{}", person.toString()));
		
		session.close();
		factory.close();
	}

}
