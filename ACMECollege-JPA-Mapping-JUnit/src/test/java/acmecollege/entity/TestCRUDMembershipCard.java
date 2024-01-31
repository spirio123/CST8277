package acmecollege.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
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
public class TestCRUDMembershipCard extends JUnitBase {
	private EntityManager em;
	private EntityTransaction et;

	private static ClubMembership clubMembership;
	private static StudentClub studentClub;
	private static Student student;
	private static MembershipCard membershipCard;
	private static final byte MyByte = 1;
	private static DurationAndStatus durationAndStatus;

	@BeforeAll
	static void setupAllInit() {

		student = new Student();
		student.setFullName("John", "Smith");

		studentClub = new AcademicStudentClub();
		studentClub.setName("Marian");
		clubMembership = new ClubMembership();
		durationAndStatus = new DurationAndStatus();
		durationAndStatus.setStartDate(LocalDateTime.of(2023, 7, 28, 3, 20, 0));
		durationAndStatus.setEndDate(LocalDateTime.of(2024, 5, 15, 3, 20));
		clubMembership.setStudentClub(studentClub);
		clubMembership.setDurationAndStatus(durationAndStatus);

		membershipCard = new MembershipCard();
		membershipCard.setOwner(student);
		membershipCard.setSigned(MyByte);
		membershipCard.setClubMembership(clubMembership);
		clubMembership.setCard(membershipCard);
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

	@Test
	@Order(1)
	void test01_Empty() {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<MembershipCard> root = query.from(MembershipCard.class);
		query.select(builder.count(root));
		TypedQuery<Long> tq = em.createQuery(query);
		long result = tq.getSingleResult();

		assertThat(result, is(comparesEqualTo(0L)));

	}

	@Test
	@Order(2)
	void test02_Create() {

		et.begin();
		em.persist(student);
		em.persist(membershipCard);
		et.commit();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<MembershipCard> root = query.from(MembershipCard.class);
		query.select(builder.count(root));
		query.where(builder.equal(root.get(MembershipCard_.id), builder.parameter(Integer.class, "id")));
		TypedQuery<Long> tq = em.createQuery(query);
		tq.setParameter("id", membershipCard.getId());
		long result = tq.getSingleResult();
		assertThat(result, is(greaterThanOrEqualTo(1L)));
	}

	@Test
	@Order(3)
	void test03_CreateInvalid() {
		et.begin();
		MembershipCard membershipCard = new MembershipCard();
		membershipCard.setOwner(student);
		membershipCard.setClubMembership(clubMembership);
		membershipCard.setSigned(MyByte);
		assertThrows(PersistenceException.class, () -> em.persist(membershipCard));
		et.commit();
	}

	@Test
	@Order(4)
	void test04_Read() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<MembershipCard> query = builder.createQuery(MembershipCard.class);
		Root<MembershipCard> root = query.from(MembershipCard.class);
		query.select(root);
		TypedQuery<MembershipCard> tq = em.createQuery(query);
		List<MembershipCard> membershipCardsList = tq.getResultList();
		assertThat(membershipCardsList, contains(equalTo(membershipCard)));
	}

	@Test
	@Order(5)
	void test05_ReadDependencies() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<MembershipCard> query = builder.createQuery(MembershipCard.class);
		Root<MembershipCard> root = query.from(MembershipCard.class);
		query.select(root);
		query.where(builder.equal(root.get(MembershipCard_.id), builder.parameter(Integer.class, "id")));

		TypedQuery<MembershipCard> tq = em.createQuery(query);
		tq.setParameter("id", membershipCard.getId());
		MembershipCard queriedMembershipCard = tq.getSingleResult();

		assertThat(queriedMembershipCard.getOwner(), equalTo(student));
		assertThat(queriedMembershipCard.getSigned(), equalTo(MyByte));
		assertThat(queriedMembershipCard.getClubMembership(), equalTo(clubMembership));

	}

	@Test
	@Order(6)
	void test06_Update() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<MembershipCard> query = builder.createQuery(MembershipCard.class);
		Root<MembershipCard> root = query.from(MembershipCard.class);
		query.select(root);
		query.where(builder.equal(root.get(MembershipCard_.id), builder.parameter(Integer.class, "id")));
		TypedQuery<MembershipCard> tq = em.createQuery(query);
		tq.setParameter("id", membershipCard.getId());
		MembershipCard updatedMembershipCard = tq.getSingleResult();

		Byte updatedSignedStatus = 0;

		et.begin();
		updatedMembershipCard.setSigned(updatedSignedStatus);

		em.merge(updatedMembershipCard);
		et.commit();

		updatedMembershipCard = tq.getSingleResult();

		assertThat(updatedMembershipCard.getSigned(), equalTo(updatedSignedStatus));

	}

	@Test
	@Order(7)
	void test07_UpdateDependencies() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<MembershipCard> query = builder.createQuery(MembershipCard.class);
		Root<MembershipCard> root = query.from(MembershipCard.class);
		query.select(root);
		query.where(builder.equal(root.get(MembershipCard_.id), builder.parameter(Integer.class, "id")));
		TypedQuery<MembershipCard> tq = em.createQuery(query);
		tq.setParameter("id", membershipCard.getId());
		MembershipCard returnedmcard = tq.getSingleResult();

		student = returnedmcard.getOwner();
		student.setFullName("Bob", "Builder");

		clubMembership = returnedmcard.getClubMembership();
		studentClub = clubMembership.getStudentClub();

		studentClub.setName("boxing");
		clubMembership.setStudentClub(studentClub);
		clubMembership.setUpdated(LocalDateTime.now());

		et.begin();
		returnedmcard.setOwner(student);
		returnedmcard.setSigned(MyByte);
		returnedmcard.setClubMembership(clubMembership);
		returnedmcard.setUpdated(LocalDateTime.now());
		em.merge(returnedmcard);
		et.commit();

		returnedmcard = tq.getSingleResult();

		assertThat(returnedmcard.getOwner(), equalTo(student));
		assertThat(returnedmcard.getClubMembership(), equalTo(clubMembership));

	}

	@Test
	@Order(8)
	void test08_DeleteDependecy() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<MembershipCard> query = builder.createQuery(MembershipCard.class);
		Root<MembershipCard> root = query.from(MembershipCard.class);
		query.select(root);
		query.where(builder.equal(root.get(MembershipCard_.id), builder.parameter(Integer.class, "id")));
		TypedQuery<MembershipCard> tq = em.createQuery(query);
		tq.setParameter("id", membershipCard.getId());
		MembershipCard returnedmcard = tq.getSingleResult();

		int clubId = returnedmcard.getClubMembership().getId();

		et.begin();
		returnedmcard.setClubMembership(null);
		em.merge(returnedmcard);
		et.commit();

		returnedmcard = tq.getSingleResult();

		assertThat(returnedmcard.getClubMembership(), is(nullValue()));

		CriteriaQuery<Long> query2 = builder.createQuery(Long.class);
		Root<ClubMembership> root2 = query2.from(ClubMembership.class);
		query2.select(builder.count(root2));
		query2.where(builder.equal(root2.get(ClubMembership_.id), builder.parameter(Integer.class, "id")));
		TypedQuery<Long> tq2 = em.createQuery(query2);
		tq2.setParameter("id", clubId);
		long result = tq2.getSingleResult();
		assertThat(result, is(equalTo(1L)));
	}

	@Test
	@Order(9)
	void test09_Delete() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<MembershipCard> query = builder.createQuery(MembershipCard.class);
		Root<MembershipCard> root = query.from(MembershipCard.class);
		query.select(root);
		query.where(builder.equal(root.get(MembershipCard_.id), builder.parameter(Integer.class, "id")));
		TypedQuery<MembershipCard> tq = em.createQuery(query);
		tq.setParameter("id", membershipCard.getId());
		MembershipCard returnedmcard = tq.getSingleResult();

		et.begin();
		MembershipCard newMembershipCard = new MembershipCard();
		Student student = new Student();
		student.setFullName("Ollie", "Savill");
		newMembershipCard.setOwner(student);
		newMembershipCard.setSigned(false);
		newMembershipCard.setClubMembership(returnedmcard.getClubMembership());

		em.persist(student);
		em.persist(newMembershipCard);
		et.commit();

		et.begin();
		em.remove(returnedmcard);
		et.commit();

		CriteriaQuery<Long> query2 = builder.createQuery(Long.class);
		Root<MembershipCard> root2 = query2.from(MembershipCard.class);
		query2.select(builder.count(root2));
		query2.where(builder.equal(root2.get(MembershipCard_.id), builder.parameter(Integer.class, "id")));
		TypedQuery<Long> tq2 = em.createQuery(query2);
		tq2.setParameter("id", returnedmcard.getId());
		long result = tq2.getSingleResult();
		assertThat(result, is(equalTo(0L)));

		TypedQuery<Long> tq3 = em.createQuery(query2);
		tq3.setParameter("id", newMembershipCard.getId());
		result = tq3.getSingleResult();
		assertThat(result, is(equalTo(1L)));
	}
}
