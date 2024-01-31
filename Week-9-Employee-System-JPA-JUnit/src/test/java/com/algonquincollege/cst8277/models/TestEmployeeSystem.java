/*****************************************************************
 * File:  EmployeeTestSuite.java Course materials CST 8277
 * 
 * @author Shariar (Shawn) Emami
 * @date Mar 8, 2021
 * 
 * @author Mike Norman
 * @date 2020 09
 */
package com.algonquincollege.cst8277.models;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder( MethodOrderer.MethodName.class)
public class TestEmployeeSystem {

	private static final String PROFESSOR_EMAIL = "normanm@algonquincollege.com";
	private static final String PROFESSOR_TITLE = "Professor";
	private static final String STREET_WOODROFFE_AVE = "1385 Woodroffe Ave.";
	private static final String CITY_NEPEAN = "Nepean";
	private static final String PROV_ONT = "On";
	private static final String COUNTRY_CANADA = "Canada";
	private static final String FIND_EMP_BY_EMAIL = "select e from Employee e where e.email = :param1";

	public static EntityManagerFactory emf;

	public static final String PERSISTENCE_UNIT = "EmployeeSystem-PU";

	public static EntityManagerFactory buildEMF() {
		return Persistence.createEntityManagerFactory( PERSISTENCE_UNIT);
	}

	@BeforeAll
	public static void setUpClass() {
		emf = buildEMF();
	}

	@AfterAll
	public static void tearDownClass() {
		emf.close();
	}

	protected EntityManager em;

	@BeforeEach
	void setupEntityManagerForEachTestcase() {
		em = emf.createEntityManager();
	}

	@AfterEach
	void tearDownEntityManagerForEachTestcase() {
		em.close();;
	}

	// C-U-D - only use EntityManager APIs: em.persist(), em.merge(), em.remove()
	// do NOT use JPQL queries:
	//   em.createQuery("update Employee e set ...) or
	//   em.createQuery("delete from Employee e where ...)
	@Test
	void test01_no_Employees_at_start() {
		EmployeePojo emp1 = em.find( EmployeePojo.class, 1);
		assertThat( emp1, is( nullValue()));
	}

	@Test
	void test02_new_employee() {
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		//create a new employee
		EmployeePojo e1 = new EmployeePojo();
		e1.setEmail( PROFESSOR_EMAIL);
		e1.setFirstName( "Mike");
		e1.setLastName( "Norman");
		e1.setTitle( PROFESSOR_TITLE);
		e1.setSalary( 10001.00);
		em.persist( e1);
		tx.commit();

		// Have to do the query AFTER the commit (won't be able to find e1 'foobar' employee before)
		TypedQuery< EmployeePojo> findProfessor = em.createQuery( FIND_EMP_BY_EMAIL, EmployeePojo.class)
				.setParameter( "param1", PROFESSOR_EMAIL);
		EmployeePojo copyOfE1 = findProfessor.getSingleResult();
		assertThat( copyOfE1.getEmail(), is( PROFESSOR_EMAIL));
		// could also verify firstName, lastName, id ... you get the idead
	}

	@Test
	void test03_new_employee_with_phones_and_project() {
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		//create a new employee and phones
		EmployeePojo newEmployee = new EmployeePojo();
		newEmployee.setFirstName( "Brad");
		newEmployee.setLastName( "Pitt");
		newEmployee.setSalary( 1000000.00);
		WorkPhone wPhone = new WorkPhone();
		wPhone.setPhoneNumber( "555-1212"); // phonenumber for tv/movies aren't real
		wPhone.setAreacode( "323"); // but areaCode for West Hollywood is (!)
		wPhone.setDepartment( "Production Office - Mr. Tarantino");
		newEmployee.addPhone( wPhone);
		MobilePhone mPhone = new MobilePhone();
		mPhone.setAreacode( "323");
		mPhone.setPhoneNumber( "555-2121");
		mPhone.setProvider( "Verizon");
		newEmployee.addPhone( mPhone);
		ProjectPojo p = new ProjectPojo();
		p.setDescription( "obviously a huge blockbuster, it has Brad Pitt in it!");
		p.setName( "in a world ...");
		newEmployee.addProject( p);
		em.persist( newEmployee);
		em.persist( p);
		tx.commit();

		Long phoneCount = (Long) em
				.createQuery( "select count(elements(e.phones)) from Employee e where e.firstName = :param1")
				.setParameter( "param1", "Brad").getSingleResult();
		assertEquals( 2, phoneCount.longValue());
	}

	// R queries
	// do NOT use 'native' SQL queries

	@Test
	void test04_find_westHollywoodEmployees_phone() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery< EmployeePojo> cq = cb.createQuery( EmployeePojo.class);
		Root< EmployeePojo> root = cq.from( EmployeePojo.class);
		ListJoin< EmployeePojo, PhonePojo> phonesJoin = root.join( EmployeePojo_.phones);
		cq.where( cb.equal( phonesJoin.get( PhonePojo_.areacode), cb.parameter( String.class, "areaCodeParam")));
		TypedQuery< EmployeePojo> tq = em.createQuery( cq.distinct( true));
		tq.setParameter( "areaCodeParam", "323");
		List< EmployeePojo> westHollywoodEmployees = tq.getResultList();
		assertThat( westHollywoodEmployees, is( not( empty())));
		// this is why we need the distinct above .. resultList has two employees (which are actually
		// the same employee) 'cause of the 2 phones in 323 areaCode
		assertThat( westHollywoodEmployees, hasSize( 1));
	}

	@Test
	void test05_find_employee_by_salary() {
		// all 'expensive' Employees
		TypedQuery< EmployeePojo> q1 = em.createQuery( "select e from Employee e where e.salary > 10000",
				EmployeePojo.class);
		List< EmployeePojo> hundredKEmps = q1.getResultList();
		assertThat( hundredKEmps, is( not( empty())));
		assertThat( hundredKEmps, hasSize( 2));
	}

	@Test
	void test06_find_employee_with_highest_salary() {
		// example of sub-select - Note: the query in the sub-select can use 'e' again,
		// but for the humans out there ... a little clarity (!)
		TypedQuery< EmployeePojo> q1 = em.createQuery(
				"select e from Employee e where e.salary = (select max(e2.salary) from Employee e2)",
				EmployeePojo.class);
		EmployeePojo expensiveEmp = q1.getSingleResult();
		assertThat( expensiveEmp, is( not( nullValue())));
		// this shouldn't be a surprise ;-)
		assertThat( expensiveEmp.getFirstName(), is( equalToIgnoringCase( "Brad")));
	}

	@Test
	void test07_find_how_many_employees() {
		// how may Employees are there?
		TypedQuery< Long> q1 = em.createQuery( "select count(e) from Employee e", Long.class);
		Long numEmployees = q1.getSingleResult();
		//count always returns long ... so our assert value has to have 'l' after it
		//otherwise compiler complains 'cause (just) '2' is an int
		assertThat( 2l, is( numEmployees));
	}

	@Test
	void test08_find_lazy_employees() {

		// Employees that are not actually working on any Projects
		TypedQuery< EmployeePojo> q1 = em.createQuery( "select e from Employee e where e.projects is empty",
				EmployeePojo.class);
		List< EmployeePojo> lazyEmps = q1.getResultList();
		assertThat( lazyEmps, is( not( empty())));
		assertThat( lazyEmps, hasSize( 1));
		EmployeePojo firstLazyEmp = lazyEmps.get( 0);
		// Prof. Mike Norman is lazy! ;-)
		assertThat( firstLazyEmp.getTitle(), is( equalToIgnoringCase( "Professor")));
	}

	@Test
	void test09_set_employee_address() {
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		TypedQuery< EmployeePojo> findProfessor = em.createQuery( FIND_EMP_BY_EMAIL, EmployeePojo.class)
				.setParameter( "param1", PROFESSOR_EMAIL);
		EmployeePojo prof = findProfessor.getSingleResult();
		AddressPojo address = new AddressPojo();
		address.setStreet( STREET_WOODROFFE_AVE);
		address.setCity( CITY_NEPEAN);
		address.setState( PROV_ONT);
		address.setCountry( COUNTRY_CANADA);
		prof.setAddress( address);
		em.merge( prof);
		tx.commit();

		// verify - Note: JPQL 'path' navigates through e to its address property (join not required, JPA generates it automatically)
		TypedQuery< EmployeePojo> q2 = em.createQuery( "select e from Employee e where e.address is not null",
				EmployeePojo.class);
		EmployeePojo copyOfProf = q2.getSingleResult();
		assertThat( copyOfProf.getTitle(), is( equalToIgnoringCase( PROFESSOR_TITLE)));

		// JPQL 'path' expression navigates through e to its address property then address' city property
		TypedQuery< EmployeePojo> q3 = em
				.createQuery( "select e from Employee e where e.address.city = :param1", EmployeePojo.class)
				.setParameter( "param1", CITY_NEPEAN);
		EmployeePojo copyOfProf2 = q3.getSingleResult();
		assertThat( copyOfProf2.getTitle(), is( equalToIgnoringCase( PROFESSOR_TITLE)));
	}

	@Test
	void test10_find_employee_CriteriBuilder_lastnameN() {
		// Employee whose lastname starts with N
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery< EmployeePojo> q1 = cb.createQuery( EmployeePojo.class);
		Root< EmployeePojo> root = q1.from( EmployeePojo.class);
		//this is an example of where we use the auto generated classes with _ at the end of their names.
		//instead of having to use and employee object inside of root.get(), we can use EmployeePojo_.lastName as the parameter.
		//Separating the query from the object.
		//also the last bit cb.parameter( String.class, "name"), is how we define a placeholder.
		//in JPQL it will look like:
		//select e from Employee e where upper(e.lastname) like :name
		//https://www.objectdb.com/java/jpa/query/jpql/string
		q1.where( cb.like( cb.upper( root.get( EmployeePojo_.lastName)), cb.parameter( String.class, "name")));
		TypedQuery< EmployeePojo> tq = em.createQuery( q1).setParameter( "name", "N%");
		EmployeePojo employee_lastnameN = tq.getSingleResult();
		assertThat( employee_lastnameN, is( not( nullValue())));
		assertThat( employee_lastnameN.getTitle(), is( equalToIgnoringCase( PROFESSOR_TITLE)));
	}

	@Test
	void test11_find_Phones_join_path_expression() {
		// JPQL 'path' expression navigates through e to its phoneNumber property; however, unlike
		// the address property, phones is a higher cardinality (fancy wording means 'more than 1')
		// so a join is required
		TypedQuery< EmployeePojo> tq = em
				.createQuery( "select distinct e from Employee e join e.phones ph where ph.phoneNumber like :param1",
						EmployeePojo.class)
				.setParameter( "param1", "555%");
		EmployeePojo brad = tq.getSingleResult();
		assertThat( brad, is( not( nullValue())));
		assertThat( brad.getFirstName(), is( equalToIgnoringCase( "Brad")));
	}

}