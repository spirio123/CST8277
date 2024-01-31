/*****************************************************************
 * File:  ProfessorDaoImpl.java Course materials (23W) CST8277
 *
 * @author Teddy Yap
 * @author Shahriar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
//import javax.persistence.EntityManager;
//import javax.persistence.TypedQuery;
//import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import databank.ejb.ProfessorService;
import databank.model.ProfessorPojo;

/**
 * Description:  Implements the C-R-U-D API for the database
 * 
 * TODONE 01 - Some components are managed by CDI.<br>
 * TODONE 02 - Methods which perform DML need @Transactional annotation.<br>
 * TODONE 03 - Fix the syntax errors to correct methods. <br>
 * TODONE 04 - Refactor this class.  Move all the method bodies and EntityManager to a new service class (e.g. ProfessorService) which is a
 * singleton (EJB).<br>
 * TODONE 05 - Inject the service class using EJB.<br>
 * TODONE 06 - Call all the methods of service class from each appropriate method here.
 */
@Named
@ApplicationScoped
public class ProfessorDaoImpl implements ProfessorDao, Serializable {
	/** explicitly set serialVersionUID */
	private static final long serialVersionUID = 1L;

	//Get the log4j2 logger for this class
	private static final Logger LOG = LogManager.getLogger();

	@EJB
	protected ProfessorService profservice;

	@Override
	public List<ProfessorPojo> readAllProfessors() {
		LOG.debug("reading all professors");
		//Use the named JPQL query from the ProfessorPojo class to grab all the professors
		//TypedQuery<ProfessorPojo> allProfessorsQuery = em.createNamedQuery(ProfessorPojo.PROFESSOR_FIND_ALL, ProfessorPojo.class);
		//Execute the query and return the result/s.
		return   profservice.readAllProfs();//allProfessorsQuery.getResultList();
	}

	
	@Override
	public ProfessorPojo createProfessor(ProfessorPojo professor) {
		LOG.debug("creating a professor = {}", professor);
		//em.something(professor);
		//em.persist(professor);
		return profservice.createPerson(professor);
	}

	
	@Override
	public ProfessorPojo readProfessorById(int professorId) {
		LOG.debug("read a specific professor = {}", professorId);
		//em.getProperties()
		return  profservice.readProfById(professorId);  //em.find(ProfessorPojo.class, professorId);
	}

	
	@Override
	public ProfessorPojo updateProfessor(ProfessorPojo professorWithUpdates) {
		LOG.debug("updating a specific professor = {}", professorWithUpdates);
		
		//ProfessorPojo mergedProfessorPojo = em.merge(professorWithUpdates);
		return  profservice.updatePerson(professorWithUpdates);//mergedProfessorPojo;
	}

	
	@Override
	public void deleteProfessorById(int professorId) {
		LOG.debug("deleting a specific professorID = {}", professorId);
		ProfessorPojo professor = readProfessorById(professorId);
		LOG.debug("deleting a specific professor = {}", professor);
		if (professor != null) {
			//em.refresh(professor);
			//em.remove(professor);
			profservice.deleteProfById(professorId);
		}
	}

}
