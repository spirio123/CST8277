package databank.ejb;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import databank.model.ProfessorPojo;

@Singleton
public class ProfessorService {
	
	@PersistenceContext
	protected EntityManager em;
	
	public List< ProfessorPojo> readAllProfs() {
		//use the named JPQL query from the PersonPojo class to grab all the people
		TypedQuery<ProfessorPojo> allProfQuery = em.createNamedQuery( ProfessorPojo.PROFESSOR_FIND_ALL, ProfessorPojo.class);
		//execute the query and return the result/s.
		return allProfQuery.getResultList();
	}
	

	@Transactional
	public ProfessorPojo createPerson( ProfessorPojo Pj) {
		em.persist(Pj);
		return Pj;
	}


	public ProfessorPojo readProfById( int profId) {
		return em.find( ProfessorPojo.class, profId);
	}


	@Transactional
	public ProfessorPojo updatePerson( ProfessorPojo profUpdates) {
		ProfessorPojo mergedProf = em.merge( profUpdates);
		try {
			return mergedProf;
		}
		catch (OptimisticLockException e) {
			return null;
		}
	}


	@Transactional
	public void deleteProfById( int profId) {
		ProfessorPojo prof = readProfById( profId);
		if ( prof != null) {
			em.refresh( prof);
			em.remove( prof);
		}
	}

}
