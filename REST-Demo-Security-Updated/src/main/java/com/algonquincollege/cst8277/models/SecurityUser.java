/*****************************************************************
 * File:  SecurityUser.java
 * Course materials CST 8277
 * @author Mike Norman
 *
 */
package com.algonquincollege.cst8277.models;

import static com.algonquincollege.cst8277.models.SecurityUser.SECURITY_USER_BY_NAME_QUERY;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * User class used for (JSR-375) Java EE Security authorization/authentication
 */

@Entity
@Table(name = "security_user")
@NamedQuery(name = SECURITY_USER_BY_NAME_QUERY, query = "SELECT u FROM SecurityUser u left join fetch u.regularUser WHERE u.username = :param1")
public class SecurityUser implements Serializable, Principal { // Principal is used by EJB and JAX-RS (aka Jersey)
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;
    
    public static final String SECURITY_USER_BY_NAME_QUERY = "SecurityUser.userByName";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected int id;

    @Column(name = "user_name")
    protected String username;
    
    @Column(name = "password_hash")
    protected String pwHash;
    
    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "users_have_roles",
        joinColumns = @JoinColumn(referencedColumnName = "id", name = "user_id"), // this entity, which is SecurityUser
        inverseJoinColumns = @JoinColumn(referencedColumnName = "role_id", name = "role_id")) // the other entity, which is SecurityRole
    protected List<SecurityRole> roles;
    
    @OneToOne(optional = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    protected User regularUser;

    public SecurityUser() {
        super();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwHash() {
        return pwHash;
    }
    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    public List<SecurityRole> getSecurityRoles() {
        return roles;
    }
    public void setSecurityRoles(List<SecurityRole> roles) {
        this.roles = roles;
    }

    public User getUser() {
        return regularUser;
    }

    public void setUser(User emp) {
        this.regularUser = emp;
    }

    //for Principal API
    @Override
    public String getName() {
        return getUsername();
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        // only include member variables that really contribute to an object's identity
        // i.e. if variables like version/updated/name/etc. change throughout an object's lifecycle,
        // they shouldn't be part of the hashCode calculation
        return prime * result + Objects.hash(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof SecurityUser otherSecurityUser) {
            // see comment (above) in hashCode(): compare using only member variables that are
            // truely part of an object's identity
            return Objects.equals(this.getId(), otherSecurityUser.getId());
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SecurityUser [id=").append(id).append(", ");
        if (username != null) {
            builder.append("username=").append(username).append(", ");
        }
        if (pwHash != null) {
            builder.append("pwHash=").append(pwHash).append(", ");
        }
        if (roles != null) {
            builder.append("roles=").append(roles).append(", ");
        }
        if (regularUser != null) {
            builder.append("regularUser=").append(regularUser);
        }
        builder.append("]");
        return builder.toString();
    }
    
}