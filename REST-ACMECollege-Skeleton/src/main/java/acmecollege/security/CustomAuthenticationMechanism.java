/**
 * File:  CustomAuthenticationMechanism.java
 * Course materials (23S) CST 8277
 * Teddy Yap
 * (Original Author) Mike Norman
 *
 * @date 2020 10
 *
 * (Modified) @author Ollie Savill
 * 			  @author Yuxi Yaxi
 * 			  @author Wissam
 */
package acmecollege.security;

import static javax.security.enterprise.identitystore.CredentialValidationResult.Status.VALID;
import static javax.servlet.http.HttpServletRequest.BASIC_AUTH;

import java.util.Base64;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

@ApplicationScoped
public class CustomAuthenticationMechanism implements HttpAuthenticationMechanism {

    @Inject
    protected CustomIdentityStore identityStore;

    @Context
    protected ServletContext servletContext;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {

        AuthenticationStatus result = httpMessageContext.doNothing();
        String name = null;
        String password = null;
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null) {
            boolean startsWithBasic = authHeader.toLowerCase().startsWith(BASIC_AUTH.toLowerCase());
            if (startsWithBasic) {
                String b64Token = authHeader.substring(BASIC_AUTH.length() + 1, authHeader.length());
                byte[] token = Base64.getDecoder().decode(b64Token);
                String tmp = new String(token);
                String[] tokenFields = tmp.split(":");
                if (tokenFields.length == 2) {
                    name = tokenFields[0];
                    password = tokenFields[1];
                }
            }
        }
        if (name != null && password != null) {
            CredentialValidationResult validationResult = identityStore.validate(new UsernamePasswordCredential(name, password));
            if (validationResult.getStatus() == VALID) {
                String validationResultStr = String.format("valid result: callerGroups=%s, callerPrincipal=%s",
                    validationResult.getCallerGroups(), validationResult.getCallerPrincipal().getName());
                servletContext.log(validationResultStr);
                result = httpMessageContext.notifyContainerAboutLogin(validationResult);
            }
            else {
                result = httpMessageContext.responseUnauthorized();
            }
        }
        return result;
    }
}