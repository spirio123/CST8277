/*****************************************************************
 * File:  RestConfig.java
 * Course materials CST 8277
 * 
 * @author Mike Norman
 *
 */
package com.algonquincollege.cst8277.rest;

import static com.algonquincollege.cst8277.util.MyConstants.ADMIN_ROLE;
import static com.algonquincollege.cst8277.util.MyConstants.APPLICATION_PATH;
import static com.algonquincollege.cst8277.util.MyConstants.USER_ROLE;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath(APPLICATION_PATH)
@DeclareRoles({USER_ROLE, ADMIN_ROLE}) //or in web.xml
public class RestConfig extends Application {

    /*
     * Without the following 'feature', the default Serialization/Deserialization for Jakarta EE 8
     * is JSON-B, a project called 'Yasson'.
     * 
     * However, it does not 'nicely' handle a variety of issues ... so substitute a non-standard package
     * called 'Jackson (2)'
     */
    
    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("jersey.config.jsonFeature", "JacksonFeature");
        // add x-jersey-tracing tracing headers Note: if more than 100 trace messages, need to alter configuration to allow for more
        props.put("jersey.config.server.tracing.type", "ALL");
        props.put("jersey.config.server.tracing.threshold", "VERBOSE");
        return props;
    }
}