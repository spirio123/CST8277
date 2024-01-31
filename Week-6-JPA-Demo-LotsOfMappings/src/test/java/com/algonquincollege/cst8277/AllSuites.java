package com.algonquincollege.cst8277;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.algonquincollege.cst8277.demo.ATestSuite;
import com.algonquincollege.cst8277.demo.BTestSuite;
import com.algonquincollege.cst8277.demo.CTestSuite;
import com.algonquincollege.cst8277.demo.DTestSuite;
import com.algonquincollege.cst8277.demo.ETestSuite;
import com.algonquincollege.cst8277.demo.FTestSuite;
import com.algonquincollege.cst8277.demo.GTestSuite;
import com.algonquincollege.cst8277.demo.HTestSuite;
import com.algonquincollege.cst8277.demo.ITestSuite;

@Suite
@SelectClasses({
   // I know - 'front' commas look weird, but it makes it easier to add to the end of the list
    ATestSuite.class
   ,BTestSuite.class
   ,CTestSuite.class
   ,DTestSuite.class
   ,ETestSuite.class
   ,FTestSuite.class
   ,GTestSuite.class
   ,HTestSuite.class
   ,ITestSuite.class
})
public class AllSuites {
}