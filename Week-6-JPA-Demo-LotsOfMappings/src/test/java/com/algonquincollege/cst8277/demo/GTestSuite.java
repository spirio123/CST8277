package com.algonquincollege.cst8277.demo;

import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.algonquincollege.cst8277.TestSuiteBase;

@TestMethodOrder(MethodOrderer.MethodName.class) // execute test methods in alpha(numerical) order 
public class GTestSuite extends TestSuiteBase {

    @Test
    public void test_01_GProjo() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        GPojo g = new GPojo();
        g.bar = 34;
        em.persist(g);
        tx.commit();
    }

}