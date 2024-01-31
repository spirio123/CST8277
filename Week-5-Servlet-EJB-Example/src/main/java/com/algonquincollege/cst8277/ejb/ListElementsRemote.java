package com.algonquincollege.cst8277.ejb;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ListElementsRemote {
	
	void addElement(String e);
	
	void removeElement(String e);
	
	List<String> getElements();
	
	void remove();

}
