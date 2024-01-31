package com.algonquincollege.cst8277.demo;

import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.algonquincollege.cst8277.TestSuiteBase;

@TestMethodOrder(MethodOrderer.MethodName.class) // execute test methods in alpha(numerical) order 
public class DTestSuite extends TestSuiteBase {

    @Test
    public void test_01_DProjo() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        DPojo d1 = new DPojo();
        d1.foo = "short and stout, here is my handle, here is my spout";
        DPojo d2 = new DPojo();
        d2.foo = "tip me over and pour me out!";
        em.persist(d1);
        em.persist(d2);
        tx.commit();
    }

}