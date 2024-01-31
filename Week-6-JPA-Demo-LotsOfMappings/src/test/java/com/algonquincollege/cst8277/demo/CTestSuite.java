package com.algonquincollege.cst8277.demo;

import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.algonquincollege.cst8277.TestSuiteBase;

@TestMethodOrder(MethodOrderer.MethodName.class) // execute test methods in alpha(numerical) order 
public class CTestSuite extends TestSuiteBase {

    @Test
    public void test_01_CProjo() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        BPojo b = new BPojo();
        b.foo = "short and stout, here is my handle, here is my spout";
        b.bar = "never gonna give you up";
        em.persist(b);
        CPojo c = new CPojo();
        c.foo = "short and stout, here is my handle, here is my spout";
        em.persist(c);
        tx.commit();
    }

}