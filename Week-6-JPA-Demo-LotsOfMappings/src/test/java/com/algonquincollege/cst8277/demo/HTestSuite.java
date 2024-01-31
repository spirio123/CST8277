package com.algonquincollege.cst8277.demo;

import static com.algonquincollege.cst8277.demo.SomeEnum.THIS;
import static com.algonquincollege.cst8277.demo.SomeEnum.WAZZIT;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.algonquincollege.cst8277.TestSuiteBase;

@TestMethodOrder(MethodOrderer.MethodName.class) // execute test methods in alpha(numerical) order 
public class HTestSuite extends TestSuiteBase {

    @Test
    public void test_01_HProjo() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        HPojo h = new HPojo();
        h.foo = WAZZIT;
        em.persist(h);
        tx.commit();
    }

    @Test
    public void test_02_H1Projo() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        H1Pojo h1 = new H1Pojo();
        h1.foo = THIS;
        em.persist(h1);
        tx.commit();
    }

}