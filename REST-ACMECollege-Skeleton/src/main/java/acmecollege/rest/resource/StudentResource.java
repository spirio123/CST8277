/**
 * File: StudentResource.java
 * @author Ollie Savill
 * @author Yuxi Yaxi
 * @author Wissam
 */
package acmecollege.rest.resource;

import static acmecollege.utility.MyConstants.*;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.soteria.WrappingCallerPrincipal;

import acmecollege.ejb.ACMECollegeService;
import acmecollege.entity.*;

@Path(STUDENT_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {

    private static final Logger LOG = LogManager.getLogger();

    @EJB
    protected ACMECollegeService service;

    @Inject
    protected SecurityContext sc;

    @GET
    @RolesAllowed({ADMIN_ROLE})
    public Response getStudents() {
        LOG.debug("retrieving all students ...");
        List<Student> students = service.getAllStudents();
        Response response = Response.ok(students).build();
        return response;
    }

    @GET
    @RolesAllowed({ADMIN_ROLE, USER_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response getStudentById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        LOG.debug("try to retrieve specific student " + id);
        Response response = null;
        Student student = null;

        if (sc.isCallerInRole(ADMIN_ROLE)) {
            student = service.getStudentById(id);
            response = Response.status(student == null ? Status.NOT_FOUND : Status.OK).entity(student).build();
        } else if (sc.isCallerInRole(USER_ROLE)) {
            WrappingCallerPrincipal wCallerPrincipal = (WrappingCallerPrincipal) sc.getCallerPrincipal();
            SecurityUser sUser = (SecurityUser) wCallerPrincipal.getWrapped();
            student = sUser.getStudent();
            if (student != null && student.getId() == id) {
                response = Response.status(Status.OK).entity(student).build();
            } else {
                throw new ForbiddenException("User trying to access resource it does not own (wrong userid)");
            }
        } else {
            response = Response.status(Status.BAD_REQUEST).build();
        }
        return response;
    }

    @POST
    @RolesAllowed({ADMIN_ROLE})
    public Response addPerson(Student newStudent) {
        Response response = null;
        Student newStudentWithIdTimestamps = service.persistStudent(newStudent);
        // Build a SecurityUser linked to the new student
        service.buildUserForNewStudent(newStudentWithIdTimestamps);
        response = Response.ok(newStudentWithIdTimestamps).build();
        return response;
    }

   
    
    @PUT
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response updateStudent(@PathParam("studentId") int studentId, Student UpdatedStudent) {
        Response response = null;
        LOG.debug("Updating a student with id = {}", studentId);
        Student updatedstud =  service.updateStudentById(studentId, UpdatedStudent);
       
        response = Response.ok(updatedstud).build();
        return response;
    }
    
    
    @PUT
    @RolesAllowed({ADMIN_ROLE})
    @Path(STUDENT_COURSE_PROFESSOR_RESOURCE_PATH)
    public Response updateProfessorForStudentCourse(@PathParam("studentId") int studentId, @PathParam("courseId") int courseId, Professor newProfessor) {
        Response response = null;
        Professor professor = service.setProfessorForStudentCourse(studentId, courseId, newProfessor);
        response = Response.ok(professor).build();
        return response;
    }
    
    
    @DELETE
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response deleteStudent(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        LOG.debug("Deleting Student with id = {}", id);
        Student stud =  service.getStudentById(id);
        
        if (stud == null) {
			HttpErrorResponse err = new HttpErrorResponse(Status.CONFLICT.getStatusCode(), 
					"Entity not found.");
			return Response.status(Status.CONFLICT).entity(err).build();
		}
		try {
			service.deleteStudentById(id);
			
			return Response.ok(stud).build(); // return 200 OK if deletion successful
		} catch (Exception e) {
			HttpErrorResponse err = new HttpErrorResponse(Status.CONFLICT.getStatusCode(),
					"Entity associated to a course registration cannot be deleted.");
			return Response.status(Status.CONFLICT).entity(err).build();
		}
    }
}