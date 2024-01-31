/*****************************************************************
 * File:  CustomIdentityStore.java
 * Course materials CST 8277
 * @author Mike Norman
 *
 */
package com.algonquincollege.cst8277.security;

import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Typed;
import javax.inject.Inject;
import javax.security.enterprise.credential.CallerOnlyCredential;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

import org.glassfish.soteria.WrappingCallerPrincipal;

import com.algonquincollege.cst8277.models.SecurityUser;

@ApplicationScoped
@Typed(CustomIdentityStore.class)
public class CustomIdentityStore implements IdentityStore {

    @Inject
    protected CustomIdentityStoreJPAHelper jpaHelper;

    @Override
    public CredentialValidationResult validate(Credential credential) {

        CredentialValidationResult result = INVALID_RESULT;

        if (credential instanceof UsernamePasswordCredential) {
            String callerName = ((UsernamePasswordCredential)credential).getCaller();
            String credentialPassword = ((UsernamePasswordCredential)credential).getPasswordAsString();
            SecurityUser user = jpaHelper.findUserByName(callerName);
            if (user != null) {
            	// in a real production application, the Db would store the HASH of the password,
            	// but for this demo, "dumb" things down a bit
                String pwHash = user.getPwHash();
                boolean verified = pwHash.equalsIgnoreCase(credentialPassword);
                if (verified) {
                    Set<String> rolesForUser = jpaHelper.findRoleNamesForUser(callerName);
                    result = new CredentialValidationResult(new WrappingCallerPrincipal(user), rolesForUser);
                }
            }
        }
        // check if the credential was CallerOnlyCredential
        else if (credential instanceof CallerOnlyCredential) {
            String callerName = ((CallerOnlyCredential)credential).getCaller();
            SecurityUser user = jpaHelper.findUserByName(callerName);
            if (user != null) {
                result = new CredentialValidationResult(callerName);
            }
        }
        return result;
    }
}