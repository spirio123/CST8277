/***************************************************************************
 * File:  TestSuiteBase.java
 * Course materials CST 8277
 * @author Mike Norman
 * 
 */
package com.algonquincollege.cst8277;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class TestSuiteBase {

    // test fixture(s)
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("lots-of-mappings-PU");
    public static EntityManager em = null;
	
    @BeforeAll
    public static void setUp() {
        em = emf.createEntityManager();
    }
    
    @AfterAll
    public static void tearDown() {
        em.close();
    }

}