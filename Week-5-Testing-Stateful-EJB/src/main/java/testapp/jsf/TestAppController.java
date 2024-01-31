/************************************************************

 * File:  TestController.java Course materials CST8277
 *
 * @author Teddy Yap
 */
package testapp.jsf;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import testapp.ejb.ListElementsRemote;

@Named
@SessionScoped
public class TestAppController implements Serializable { // specific client will hav eits own controller instead of all
															// being shared, but multiple tabs within the same browser
															// represents the same client session so it doesn't fix the
															// problem exactly, the way to fix it would probably be a
															// log in or token, then this can be saved in a database for
															// the future linked to the user
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/Testing-Stateful-EJB/StatefulEJB")
	ListElementsRemote myStatefulEJB; // same as the other one

	String userInput = "";

	public List<String> getValues() {
		return this.myStatefulEJB.getElements();
	}

	public void setUserInput(String ui) {
		this.userInput = ui;
	}

	public String getUserInput() {
		return this.userInput;
	}

	public void addUserInput() {
		this.myStatefulEJB.addElement(this.userInput);
		this.userInput = "";
	}

	public void removeUserInput() {
		this.myStatefulEJB.removeElement(this.userInput);
		this.userInput = "";
	}

	public void removeAll() {
		this.myStatefulEJB.remove();
	}

	public int getListSize() {
		return this.myStatefulEJB.getElements().size();
	}

}