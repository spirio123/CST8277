package com.algonquincollege.cst8277.demo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.algonquincollege.cst8277.TestSuiteBase;

@TestMethodOrder(MethodOrderer.MethodName.class) // execute test methods in alpha(numerical) order 
public class ITestSuite extends TestSuiteBase {

    @Test
    public void test_01_IProjo() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        IPojo shopping = new IPojo();
        shopping.description = "Shopping List";
        List<String> listOfThingsToDo = new ArrayList<>();
        listOfThingsToDo.add("2% Milk");
        listOfThingsToDo.add("5% Cream");
        listOfThingsToDo.add("Toilet Papger");
        shopping.reminders = listOfThingsToDo;
        em.persist(shopping);
        tx.commit();
    }

}