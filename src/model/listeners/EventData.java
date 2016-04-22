package model.listeners;

public class EventData {

	private Sender sender;
	private Event event;
	private Object object;

	public EventData(Sender sender, Event event, Object object) {
		this.sender = sender;
		this.event = event;
		this.object = object;
	}

	public Sender getSender() {
		return sender;
	}

	public Event getEvent() {
		return event;
	}

	public Object getObject() {
		return object;
	}
}
