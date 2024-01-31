/**
 * File: TestCourseRegistration.java
 * @author Ollie Savill
 * @author Yuxi Yaxi
 * @author Wissam
 */
package acmecollege;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.invoke.MethodHandles;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static acmecollege.utility.MyConstants.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;

import acmecollege.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.logging.LoggingFeature;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.junit.jupiter.api.Order;

@TestMethodOrder(MethodOrderer.MethodName.class)
class TestCourseRegistration {

	private static final Class<?> _thisClaz = MethodHandles.lookup().lookupClass();
    private static final Logger logger = LogManager.getLogger(_thisClaz);
    static final String HTTP_SCHEMA = "http";
    static final String HOST = "localhost";
    static final int PORT = 8080;
	
	// Test fixture(s)
    static URI uri;
    static HttpAuthenticationFeature adminAuth;
    static HttpAuthenticationFeature userAuth;
	
    @BeforeAll
    public static void oneTimeSetUp() throws Exception {
        logger.debug("oneTimeSetUp");
        uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        adminAuth = HttpAuthenticationFeature.basic(DEFAULT_ADMIN_USER, DEFAULT_ADMIN_USER_PASSWORD);
        userAuth = HttpAuthenticationFeature.basic(DEFAULT_USER, DEFAULT_USER_PASSWORD);
    }

    protected WebTarget webTarget;
    @BeforeEach
    public void setUp() {
        Client client = ClientBuilder.newClient(
            new ClientConfig().register(MyObjectMapperProvider.class).register(new LoggingFeature()));
        webTarget = client.target(uri);
    }

	@Test
	@Order(1)
	public void test01_all_course_registrations_with_adminrole() throws JsonMappingException, JsonProcessingException {
		Response response = webTarget
            .register(adminAuth)
            .path(COURSE_REGISTRATION_RESOURCE_NAME)
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
    }

	@Test
	@Order(2)
	public void test02_all_course_registrations_with_userrole() throws JsonMappingException, JsonProcessingException {
		Response response = webTarget
            .register(userAuth)
            .path(COURSE_REGISTRATION_RESOURCE_NAME)
            .request()
            .get();
        assertThat(response.getStatus(), is(403));
    }
	
	@Test
	@Order(3)
	public void test03_add_course_registration_with_userrole() throws JsonMappingException, JsonProcessingException {
		CourseRegistration CourseRegistration = new CourseRegistration();
		Response response = webTarget
            .register(userAuth)
            .path(COURSE_REGISTRATION_RESOURCE_NAME)
            .request()
            .post(Entity.json(CourseRegistration)); 
        assertThat(response.getStatus(), is(403));
    }
	
	@Test
	@Order(4)
	public void test04_delete_course_registration_by_id_with_userrole() throws JsonMappingException, JsonProcessingException {
		Response response = webTarget
            .register(userAuth)
            .path(COURSE_REGISTRATION_RESOURCE_NAME + "/" + 1)
            .request()
            .delete(); 
        assertThat(response.getStatus(), is(405));
		
    }
	
	

}