/*****************************************************************
 * File:  UserResource.java
 * Course materials CST 8277
 * @author Mike Norman
 *
 */
package com.algonquincollege.cst8277.rest;

import static com.algonquincollege.cst8277.util.MyConstants.ADMIN_ROLE;
import static com.algonquincollege.cst8277.util.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static com.algonquincollege.cst8277.util.MyConstants.RESOURCE_PATH_ID_PATH;
import static com.algonquincollege.cst8277.util.MyConstants.USER_PATH;
import static com.algonquincollege.cst8277.util.MyConstants.USER_ROLE;
import static com.algonquincollege.cst8277.util.MyConstants.castObject;
import static javax.ws.rs.core.Response.Status.FORBIDDEN;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.soteria.WrappingCallerPrincipal;

import com.algonquincollege.cst8277.ejbs.UserService;
import com.algonquincollege.cst8277.models.SecurityUser;
import com.algonquincollege.cst8277.models.User;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path(USER_PATH)
public class UserResource {
    
    private static final String DETAILED_FORBIDDEN_MSG = """
        {
          "detailedMsg " :  "User trying to access resource it does not own, given id does not match Principal id"
        }
    """;

    @EJB
    protected UserService service;

    @Context
    protected ServletContext servletContext;

    @Inject
    protected SecurityContext sc;
    
    @RolesAllowed({ADMIN_ROLE, USER_ROLE})
    @GET
    public Response getAllUsers() {
        Response response = Response.noContent().build();
        List<User> users = new ArrayList<>();
        WrappingCallerPrincipal wCallerPrincipal = (WrappingCallerPrincipal)sc.getCallerPrincipal();
        SecurityUser principal = (SecurityUser)wCallerPrincipal.getWrapped();
        if (sc.isCallerInRole(ADMIN_ROLE)) {
            users.addAll(service.getAllUsers());
        }
        else {
            User u = principal.getUser();
            if (u != null) {
                users.add(u);
            }
        }
        response = Response.ok(users).build();
        return response;
    }

    @RolesAllowed({USER_ROLE})
    @GET
    @Path(RESOURCE_PATH_ID_PATH)
    public Response getSpecificUser(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        Response response = Response.status(FORBIDDEN).entity(DETAILED_FORBIDDEN_MSG).build();
        WrappingCallerPrincipal wCallerPrincipal = castObject( WrappingCallerPrincipal.class, sc.getCallerPrincipal());
        if (wCallerPrincipal != null) {
            SecurityUser sUser = castObject(SecurityUser.class, wCallerPrincipal.getWrapped());
            User u = sUser.getUser();
            if (u.getId() == id) {
                response = Response.ok(u).build();
                servletContext.log("yeah! retrieved specific user = " + u.toString());
            }
        }
        return response;
    }

}