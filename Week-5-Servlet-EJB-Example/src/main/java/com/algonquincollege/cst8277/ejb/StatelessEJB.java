package com.algonquincollege.cst8277.ejb;

import javax.ejb.Stateless;

@Stateless //since this is stateless, we do not store any information about the client, no instance variables or argument variables
// stateless seems to be static vs stateful is dynamic, will verify with him after this
public class StatelessEJB {

    public StatelessEJB() {
    }
    
    public String sayHello(String name) {
    	return "Hello " + name + "!";
    }

}
