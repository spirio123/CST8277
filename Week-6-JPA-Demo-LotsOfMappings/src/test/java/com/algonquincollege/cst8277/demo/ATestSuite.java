package com.algonquincollege.cst8277.demo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.algonquincollege.cst8277.TestSuiteBase;

@TestMethodOrder(MethodOrderer.MethodName.class) // execute test methods in alpha(numerical) order 
public class ATestSuite extends TestSuiteBase {

    @Test
    public void test_01_AProjo() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        APojo a = new APojo();
        a.foo = "I'm a little tea-pot!";
        em.persist(a);
        tx.commit();
        assertThat(1, equalTo(a.id));
    }

}