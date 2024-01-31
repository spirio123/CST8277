package com.algonquincollege.cst8277.week3.labexercise2;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhatIsNewJava14 {
	protected static final Class<?> MY_KLASSNAME = MethodHandles.lookup().lookupClass();
	private static final Logger logger = LoggerFactory.getLogger(MY_KLASSNAME);
	
	/*
	 * Old way:
	     private static final String A_QUOTE =
	         "Life is what happens" + "\n" + 
	         "when you're busy" + "\n" + 
	  if you want to add another line, must keep track of quotes, + (plus-sign) concatenation ...
	         "making other plans - John Lennon";
	 */
	
	// Java now has multi-line text blocks, just like Python
	private static final String A_QUOTE = """
		Life is what happens
		when you're busy
		making other plans - John Lennon
		""";

	public static void main(String[] args) {
		logger.info("\n{}", A_QUOTE);
	}
}
