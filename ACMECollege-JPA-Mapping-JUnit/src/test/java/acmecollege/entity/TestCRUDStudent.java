package acmecollege.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import common.JUnitBase;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCRUDStudent extends JUnitBase {
	private EntityManager em;
	private EntityTransaction et;

	private static Student student;
	private static final String FirstName = "Ollie";
	private static final String LastName = "Savill";

	@BeforeAll
	static void setupAllInit() {
		student = new Student();
		student.setFullName(FirstName, LastName);

	}

	@BeforeEach
	void setup() {
		em = getEntityManager();
		et = em.getTransaction();
	}

	@AfterEach
	void tearDown() {
		em.close();
	}

	@Order(1)
	@Test
	void test01_Empty() {
		long result = getTotalCount(em, Student.class);
		assertThat(result, is(comparesEqualTo(0L)));
	}

	@Test
	@Order(2)
	void test02_Create() {
		et.begin();
		student = new Student();
		student.setFirstName(FirstName);
		student.setLastName(LastName);
		student.setCreated(LocalDateTime.now());
		em.persist(student);
		et.commit();
		long result = getTotalCount(em, Student.class);
		assertThat(result, is(greaterThanOrEqualTo(1L)));
	}

	@Test
	@Order(3)
	void test03_CreateInvalid() {
		et.begin();

		Student invalidStudent = new Student();
		invalidStudent.setLastName("Arts & Crafts");
		assertThrows(PersistenceException.class, () -> em.persist(invalidStudent));
		et.commit();
	}

	@Test
	@Order(4)
	void test04_Read() {

		List<Student> allStudents = getAll(em, Student.class);
		assertThat(allStudents, contains(equalTo(student)));
	}

	@Test
	@Order(5)
	void test05_ReadAllFields() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Student> query = builder.createQuery(Student.class);
		Root<Student> root = query.from(Student.class);
		query.select(root);
		query.where(builder.equal(root.get(Student_.id), builder.parameter(Integer.class, "id")));
		TypedQuery<Student> tq = em.createQuery(query);
		tq.setParameter("id", student.getId());
		Student queriedStudent = tq.getSingleResult();

		assertThat(queriedStudent.getFirstName(), equalTo(FirstName));
		assertThat(queriedStudent.getLastName(), equalTo(LastName));
	}

	@Test
	@Order(6)
	void test06_Update() {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Student> query = builder.createQuery(Student.class);
		Root<Student> root = query.from(Student.class);
		query.select(root);
		query.where(builder.equal(root.get(Student_.id), builder.parameter(Integer.class, "id")));
		TypedQuery<Student> tq = em.createQuery(query);
		tq.setParameter("id", student.getId());
		Student queriedStudent = tq.getSingleResult();

		String newFirstName = "Zeme";
		String newLastName = "Greg";

		et.begin();
		queriedStudent.setFirstName(newFirstName);
		queriedStudent.setLastName(newLastName);
		em.merge(queriedStudent);

		et.commit();

		queriedStudent = tq.getSingleResult();

		assertThat(queriedStudent.getFirstName(), equalTo(newFirstName));
		assertThat(queriedStudent.getLastName(), equalTo(newLastName));
	}

	@Test
	@Order(7)
	void test07_Delete() {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Student> query = builder.createQuery(Student.class);
		Root<Student> root = query.from(Student.class);
		query.select(root);
		query.where(builder.equal(root.get(Student_.id), builder.parameter(Integer.class, "id")));
		TypedQuery<Student> tq = em.createQuery(query);
		tq.setParameter("id", student.getId());
		Student returnStud = tq.getSingleResult();

		et.begin();
		Student newStudent = new Student();

		String newFirstName = "Jojo";
		String newLastName = "Asun";

		newStudent.setFirstName(newFirstName);
		newStudent.setLastName(newLastName);

		em.persist(newStudent);

		et.commit();

		et.begin();
		em.remove(returnStud);
		et.commit();

		long result = getTotalCount(em, Student.class);
		assertThat(result, is(equalTo(1L)));

		result = getTotalCount(em, Student.class);
		assertThat(result, is(equalTo(1L)));
	}
}
