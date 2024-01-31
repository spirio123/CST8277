package testapp.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;

@Stateful
public class StatefulEJB implements ListElementsRemote {

	List<String> values = new ArrayList<>();
	
    public StatefulEJB() {
    }

	@Override
	public void addElement(String e) {
		values.add(e);
	}

	@Override
	public void removeElement(String e) {
		values.remove(e);
	}

	@Override
	public List<String> getElements() {
		return values;
	}
	
	@Override
	public void remove() {
		values.clear();
	}

}
