package common;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import acmecollege.entity.ClubMembership;
import acmecollege.entity.Course;
import acmecollege.entity.CourseRegistration;
import acmecollege.entity.MembershipCard;
import acmecollege.entity.Professor;
import acmecollege.entity.Student;
import acmecollege.entity.StudentClub;

/**
 * Super class for all JUnit tests, holds common methods for creating
 * {@link EntityManagerFactory} and truncating the DB before all.
 * 
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @version August 28, 2022
 */
public class JUnitBase {

	protected static final Logger LOG = LogManager.getLogger();

	/**
	 * Default name of Persistence Unit = "acmecollege-PU"
	 */
	private static final String PERSISTENCE_UNIT = "acmecollege-PU";

	/**
	 * Static instance of {@link EntityManagerFactory} for subclasses
	 */
	protected static EntityManagerFactory emf;

	/**
	 * Create an instance of {@link EntityManagerFactory} using
	 * {@link JUnitBase#PERSISTENCE_UNIT}.<br>
	 * redirects to {@link JUnitBase#buildEMF(String)}.
	 * 
	 * @return An instance of EntityManagerFactory
	 */
	protected static EntityManagerFactory buildEMF() {
		return buildEMF(PERSISTENCE_UNIT);
	}

	/**
	 * Create an instance of {@link EntityManagerFactory} using provided Persistence
	 * Unit name.
	 * 
	 * @return An instance of EntityManagerFactory
	 */
	protected static EntityManagerFactory buildEMF(String persistenceUnitName) {
		Objects.requireNonNull(persistenceUnitName, "Persistence Unit name cannot be null");
		if (persistenceUnitName.isBlank()) {
			throw new IllegalArgumentException("Persistence Unit name cannot be empty or just white space");
		}
		return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	}

	/**
	 * Create a new instance of {@link EntityManager}.<br>
	 * must call {@link JUnitBase#buildEMF()} or {@link JUnitBase#buildEMF(String)}
	 * first.
	 * 
	 * @return An instance of {@link EntityManager}
	 */
	protected static EntityManager getEntityManager() {
		if (emf == null) {
			throw new IllegalStateException("EntityManagerFactory is null, must call JUnitBase::buildEMF first");
		}
		return emf.createEntityManager();
	}

	/**
	 * Delete all Entities. Order of delete matters.
	 */
	protected static void deleteAllData() {
		EntityManager em = getEntityManager();

		// TODO JB01 - Begin transaction and truncate all tables. Order matters.

		EntityTransaction trans = em.getTransaction();
		trans.begin();
		deleteAllFrom(Professor.class, em);
		deleteAllFrom(Course.class, em);
		deleteAllFrom(MembershipCard.class, em);
		deleteAllFrom(ClubMembership.class, em);
		deleteAllFrom(CourseRegistration.class, em);
		deleteAllFrom(StudentClub.class, em);
		deleteAllFrom(Student.class, em);
		trans.commit();
	}

	/**
	 * Delete all instances of provided type form the DB. Same operation as
	 * truncate.
	 * 
	 * @see <a href =
	 *      "https://stackoverflow.com/questions/23269885/truncate-delete-from-given-the-entity-class">
	 *      StackOverflow: Truncate with JPA</a>
	 * @param <T>        - Type of entity to delete, can be inferred by JVM when
	 *                   method is being executed.
	 * @param entityType - Class type of entity, like Professor.class
	 * @param em         - EntityManager to be used
	 * @return The number of entities updated or deleted
	 */
	public static <T> int deleteAllFrom(Class<T> entityType, EntityManager em) {
		// TODO JB02 - Using CriteriaBuilder create a CriteriaDelete to execute a
		// truncate on DB.
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaDelete<T> query = cb.createCriteriaDelete(entityType);
		query.from(entityType);
		em.createQuery(query).executeUpdate();
		return -1;
	}

	protected static <T> long getTotalCount(EntityManager em, Class<T> clazz) {
		// TODO JB03 - Optional helper method. Create a CriteriaQuery here to be reused
		// in your tests.
		// Method signature is just a suggestion it can be modified if need be.
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<T> root = query.from(clazz);
		query.select(builder.count(root));
		TypedQuery<Long> tq = em.createQuery(query);
		long result = tq.getSingleResult();
		return result;
	}

	protected static <T> List<T> getAll(EntityManager em, Class<T> clazz) {
		// TODO JB04 - Optional helper method. Create a CriteriaQuery here to be reused
		// in your tests.
		// Method signature is just a suggestion it can be modified if need be.

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root);
		TypedQuery<T> tq = em.createQuery(query);
		List<T> results = tq.getResultList();

		// return null;
		return results;
	}

	protected static <T, R> T getWithId(EntityManager em, Class<T> clazz, Class<R> classPK,
			SingularAttribute<? super T, R> sa, R id) {
		// TODO JB05 - Optional helper method. Create a CriteriaQuery here to be reused
		// in your tests.
		// Method signature is just a suggestion it can be modified if need be.
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root).where(builder.equal(root.get(sa), builder.parameter(classPK.getClass(), "id")));
		TypedQuery<T> tq = em.createQuery(query);
		tq.setParameter("id", id);
		List<T> results = tq.getResultList();

		// return null;
		if (results != null)
			return results.get(0);
		else
			return null;
	}

	protected static <T, R> long getCountWithId(EntityManager em, Class<T> clazz, Class<R> classPK,
			SingularAttribute<? super T, R> sa, R id) {
		// TODO JB06 - Optional helper method. Create a CriteriaQuery here to be reused
		// in your tests.
		// Method signature is just a suggestion it can be modified if need be.
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root).where(builder.equal(root.get(sa), builder.parameter(classPK.getClass(), "id")));
		TypedQuery<T> tq = em.createQuery(query);
		tq.setParameter("id", id);
		List<T> results = tq.getResultList();

		// return -1;
		if (results != null)
			return results.size();
		else
			return -1;
	}

	@BeforeAll
	static void setupAll() {
		emf = buildEMF();
		deleteAllData();
	}

	@AfterAll
	static void tearDownAll() {
		emf.close();
	}
}
