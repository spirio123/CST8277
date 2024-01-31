/**
 * File: MyQueriesConstants.java
 * @author Ollie Savill
 * @author Yuxi Yaxi
 * @author Wissam
 */
package acmecollege.utility;

public interface MyQueriesConstants {
	// ClubMembership related queries
	public static final String CLUB_MEMBERSHIP_FIND_ALL = "ClubMembership.findAll";
	public static final String CLUB_MEMBERSHIP_FIND_BY_ID = "ClubMembership.findbyId";

	// Course related queries
	public static final String COURSE_FIND_ALL = "Course.findAll";
    public static final String COURSE_FIND_BY_ID="Course.findOne";
    public static final String COURSE_IS_DUPLICATE="Course.isDuplicate";

	// CourseRegistration related queries
	public static final String COURSE_REGISTRATION_FIND_ALL = "CourseRegistration.findAll";
	public static final String COURSE_STUDENT = "CourseStudent";

	// MembershipCard related queries
	public static final String MEMBERSHIP_CARD_FIND_ALL = "MembershipCard.findAll";
	public static final String MEMBERSHIP_CARD_FIND_BY_ID = "MembershipCard.findById";
	public static final String MEMBERSHIP_STUDENT = "MembershipStudent";

	// Professor related queries
	public static final String PROFESSOR_FIND_ALL = "Professor.findAll";
	public static final String PROFESSOR_FIND_BY_ID = "Professor.findbyId";
	public static final String PROFESSOR_IS_DUPLICATE = "Professor.isDuplicate";
	public static final String PROFESSOR_FIND_BY_NAME_DEPARTMENT = "Professor.findByNameDepartment";

	// SecurityRole related queries
	public static final String SECURITY_ROLE_FIND_ALL = "SecurityRole.findAll";

	// SecurityUser related queries
	public static final String SECURITY_USER_FIND_BY_ROLE = "SecurityUser.find";
	public static final String SECURITY_USER_FIND_BY_NAME = "SecurityUser.userByName";
	public static final String SECURITY_USER_IS_DUPLICATE = "SecurityUser.isDuplicate";
	public static final String SECURITY_USER_FIND_BY_STUDENT_ID = "SecurityUser.byStudentId";

	// Student related queries
	public static final String STUDENT_FIND_ALL = "Student.findAll";
	public static final String STUDENT_FIND_BY_ID = "Student.findAllByID";

	// StudentClub related queries
	public static final String STUDENT_CLUB_FIND_ALL = "StudentClub.findAll";
	public static final String STUDENT_CLUB_FIND_BY_NAME = "StudentClub.findByName";
	public static final String STUDENT_CLUB_IS_DUPLICATE = "StudentClub.isDuplicate";
}
