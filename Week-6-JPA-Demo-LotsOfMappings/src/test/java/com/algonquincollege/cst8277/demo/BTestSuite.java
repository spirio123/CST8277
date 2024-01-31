package com.algonquincollege.cst8277.demo;

import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.algonquincollege.cst8277.TestSuiteBase;

@TestMethodOrder(MethodOrderer.MethodName.class) // execute test methods in alpha(numerical) order 
public class BTestSuite extends TestSuiteBase {

    @Test
    public void test_01_BProjo() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        BPojo b = new BPojo();
        b.foo = "short and stout, here is my handle, here is my spout";
        b.bar = "never gonna give you up";
        em.persist(b);
        tx.commit();
    }

}