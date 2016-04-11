package model.listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Listeners {

	private List<Listener> listeners = new ArrayList<Listener>();

	public boolean addListener(Listener name) {
		return listeners.add(name);
	}

	public boolean delListener(Listener name) {
		return listeners.remove(name);
	}

	public void notifyListeners(EventData event) {
		Iterator<Listener> iterator = listeners.iterator();
		while (iterator.hasNext()) {
			Listener currentListener = iterator.next();
			currentListener.handleEvent(event);
		}
	}
}
