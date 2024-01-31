package com.algonquincollege.cst8277.demo;

import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.algonquincollege.cst8277.TestSuiteBase;

@TestMethodOrder(MethodOrderer.MethodName.class) // execute test methods in alpha(numerical) order 
public class FTestSuite extends TestSuiteBase {

    @Test
    public void test_01_FProjo() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        FPojo f1 = new FPojo();
        f1.foo = "short and stout, here is my handle, here is my spout";
        FPojo f2 = new FPojo();
        f2.foo = "tip me over and pour me out!";
        FPojo f3 = new FPojo();
        f3.foo = "is there a next line?";
        em.persist(f1);
        em.persist(f2);
        em.persist(f3);
        tx.commit();
    }

}