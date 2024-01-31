/*****************************************************************
 * File:  UserService.java
 * Course materials CST 8277
 * @author Mike Norman
 *
 */
package com.algonquincollege.cst8277.ejbs;

import static com.algonquincollege.cst8277.util.MyConstants.PU_NAME;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.algonquincollege.cst8277.models.User;

@Stateless
public class UserService implements Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext(name = PU_NAME)
	protected EntityManager em;

	public List<User> getAllUsers() {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<User> cq = cb.createQuery(User.class);
    	cq.select(cq.from(User.class));
    	return em.createQuery(cq).getResultList();
	}
    
    public User findUserById(int id) {
    	return em.find(User.class, id);
    }

}
