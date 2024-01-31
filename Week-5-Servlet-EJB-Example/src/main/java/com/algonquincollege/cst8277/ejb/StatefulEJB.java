package com.algonquincollege.cst8277.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;

@Stateful
public class StatefulEJB implements ListElementsRemote {

	List<String> values = new ArrayList<>(); // list of stuff that the stuff is applied to
	// list is like your shopping cart in this case, that is the 'state' we are talking about when talking about stateful
	// its the state if the client and their cart
	// if opened in a new browser, it will still keep the items added which is useful
	// bad if the application can be accessed directly because two clients will clash
	// as the state is shared between both sessions, this can be negated with proper log on proceeders
	// the reason both clients can see the same state is they use the same servlet
	// servlet doesnt have a concept of session so any person who joins will be served by the same servlets
	// 
	
    public StatefulEJB() {
    }

	@Override
	public void addElement(String e) { // implements what it inherits from the interface (jsp guy) // add values
		values.add(e);
	}

	@Override
	public void removeElement(String e) { // remove value
		values.remove(e);
	}

	@Override
	public List<String> getElements() { // returns entire list of strings
		return values;
	}
	
	@Override
	public void remove() { // clear the list of strings
		values.clear();
	}

}
