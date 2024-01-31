/**
 * File:  SecurityUser.java
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
package acmecollege.entity;

import java.io.Serializable;
import java.security.Principal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import acmecollege.rest.serializer.SecurityRoleSerializer;
import acmecollege.utility.MyQueriesConstants;

@SuppressWarnings("unused")
@Entity
@Table(name ="security_user")
@Access(AccessType.FIELD)
@NamedQuery(name = MyQueriesConstants.SECURITY_USER_FIND_BY_NAME, query = "SELECT su FROM SecurityUser su LEFT JOIN FETCH su.roles WHERE su.username = :username")
@NamedQuery(name = MyQueriesConstants.SECURITY_USER_IS_DUPLICATE, query = "SELECT COUNT(su) FROM SecurityUser su WHERE su.username = :username")
@NamedQuery(name = MyQueriesConstants.SECURITY_USER_FIND_BY_STUDENT_ID, query = "SELECT su FROM SecurityUser su WHERE su.student.id = :studentId")

public class SecurityUser implements Serializable, Principal {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    protected int id;
    
    @Basic (optional= false)
    @Column(name = "username", nullable=false)
    protected String username;
    
    @Basic(optional = false)
    @Column(name = "password_hash", nullable = false)
    protected String pwHash;
    
    @OneToOne(optional = true)
    @JoinColumn(name = "student_id", referencedColumnName="id")
    protected Student student;
    
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "user_has_role",
        joinColumns = @JoinColumn(referencedColumnName = "user_id", name = "user_id"),
        inverseJoinColumns = @JoinColumn(referencedColumnName = "role_id", name = "role_id"))
    protected Set<SecurityRole> roles = new HashSet<SecurityRole>();
   
    
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonSerialize(using = SecurityRoleSerializer.class)
    public Set<SecurityRole> getRoles() {
        return roles;
    }
    
    public void setRoles(Set<SecurityRole> roles) {
        this.roles = roles;
    }

    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String getName() {
        return getUsername();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
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
            return Objects.equals(this.getId(), otherSecurityUser.getId());
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SecurityUser [id = ").append(id).append(", username = ").append(username).append("]");
        return builder.toString();
    }
    
}