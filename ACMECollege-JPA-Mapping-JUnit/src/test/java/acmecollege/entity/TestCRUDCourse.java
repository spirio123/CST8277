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
public class TestCRUDCourse extends JUnitBase {

	private EntityManager entityManager;
	private EntityTransaction entityTransaction;

	private static Course course;
	private static final String CourseCode = "CST8277";
	private static final String CourseTitle = "Computer Programming";
	private static final int Year = 2023;
	private static final String Semester = "Fall";
	private static final int CreditUnit = 200;
	private static final byte online = 0;

	@BeforeAll
	static void setupAllInit() {
		course = new Course();
		course.setCourse(CourseCode, CourseTitle, Year, Semester, CreditUnit, online);

	}

	@BeforeEach
	void setup() {
		entityManager = getEntityManager();
		entityTransaction = entityManager.getTransaction();
	}

	@AfterEach
	void tearDown() {
		entityManager.close();
	}

	@Order(1)
	@Test
	void test01_Empty() {

		long result = getTotalCount(entityManager, Course.class);
		assertThat(result, is(comparesEqualTo(0L)));
	}

	@Test
	@Order(2)
	void test02_Create() {
		entityTransaction.begin();
		course = new Course();
		course.setCourse(CourseCode, CourseTitle, Year, Semester, CreditUnit, online);
		course.setCreated(LocalDateTime.now());

		entityManager.persist(course);
		entityTransaction.commit();

		long result = getTotalCount(entityManager, Course.class);
		assertThat(result, is(greaterThanOrEqualTo(1L)));
	}

	@Test
	@Order(3)
	void test03_CreateInvalid() {
		entityTransaction.begin();

		Course tesTtCourse = new Course();
		tesTtCourse.setCourseTitle("Mathematics");
		assertThrows(PersistenceException.class, () -> entityManager.persist(tesTtCourse));
		entityTransaction.commit();
	}

	@Test
	@Order(4)
	void test04_Read() {

		List<Course> allCourses = getAll(entityManager, Course.class);
		assertThat(allCourses, contains(equalTo(course)));
	}

	@Test
	@Order(5)
	void test05_ReadAllFields() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Course> query = builder.createQuery(Course.class);
		Root<Course> root = query.from(Course.class);
		query.select(root);
		query.where(builder.equal(root.get(Course_.id), builder.parameter(Integer.class, "id")));
		TypedQuery<Course> tq = entityManager.createQuery(query);
		tq.setParameter("id", course.getId());
		Course queriedCourse = tq.getSingleResult();

		assertThat(queriedCourse.getCourseCode(), equalTo(CourseCode));
		assertThat(queriedCourse.getCourseTitle(), equalTo(CourseTitle));
		assertThat(queriedCourse.getYear(), equalTo(Year));
		assertThat(queriedCourse.getSemester(), equalTo(Semester));
		assertThat(queriedCourse.getCreditUnits(), equalTo(CreditUnit));

	}

	@Test
	@Order(6)
	void test06_Update() {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Course> query = builder.createQuery(Course.class);
		Root<Course> root = query.from(Course.class);
		query.select(root);
		query.where(builder.equal(root.get(Course_.id), builder.parameter(Integer.class, "id")));
		TypedQuery<Course> tq = entityManager.createQuery(query);
		tq.setParameter("id", course.getId());
		Course queriedCourse = tq.getSingleResult();

		String newCourseTitle = "A.P Biology";
		LocalDateTime now = LocalDateTime.now();
		String newCourseCode = "BSCE";

		entityTransaction.begin();
		queriedCourse.setCourseCode(newCourseCode);
		queriedCourse.setCourseTitle(newCourseTitle);
		queriedCourse.setOnline((byte) 1);
		queriedCourse.setUpdated(now);
		entityManager.merge(queriedCourse);

		entityTransaction.commit();

		queriedCourse = tq.getSingleResult();

		assertThat(queriedCourse.getCourseCode(), equalTo(newCourseCode));
		assertThat(queriedCourse.getCourseTitle(), equalTo(newCourseTitle));
	}

	@Test
	@Order(7)
	void test07_Delete() {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Course> query = builder.createQuery(Course.class);
		Root<Course> root = query.from(Course.class);
		query.select(root);
		query.where(builder.equal(root.get(Course_.id), builder.parameter(Integer.class, "id")));
		TypedQuery<Course> tq = entityManager.createQuery(query);
		tq.setParameter("id", course.getId());
		Course queriedCourse = tq.getSingleResult();

		entityTransaction.begin();
		Course courseTest = new Course();

		String newCourseCode = "ABC123";
		String newCourseTitle = "Alphabet";
		int newYear = 2024;
		String newSemester = "Winter";
		int newcreditUni = 5;
		byte Newbyte = 1;

		courseTest.setCourse(newCourseCode, newCourseTitle, newYear, newSemester, newcreditUni, Newbyte);

		entityManager.persist(courseTest);

		entityTransaction.commit();

		entityTransaction.begin();
		entityManager.remove(queriedCourse);
		entityTransaction.commit();

		long result = getTotalCount(entityManager, Course.class);
		assertThat(result, is(equalTo(1L)));

		result = getTotalCount(entityManager, Course.class);
		assertThat(result, is(equalTo(1L)));
	}
}
