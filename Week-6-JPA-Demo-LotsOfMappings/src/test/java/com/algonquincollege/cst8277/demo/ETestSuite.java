package com.algonquincollege.cst8277.demo;

import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.algonquincollege.cst8277.TestSuiteBase;

@TestMethodOrder(MethodOrderer.MethodName.class) // execute test methods in alpha(numerical) order 
public class ETestSuite extends TestSuiteBase {

    @Test
    public void test_01_EProjo() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        EPojo e1 = new EPojo();
        e1.foo = "short and stout, here is my handle, here is my spout";
        EPojo e2 = new EPojo();
        e2.foo = "tip me over and pour me out!";
        EPojo e3 = new EPojo();
        e3.foo = "is there a next line?";
        em.persist(e1);
        em.persist(e2);
        em.persist(e3);
        tx.commit();
    }

}