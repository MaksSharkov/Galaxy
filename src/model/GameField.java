package model;

import model.listeners.Event;
import model.listeners.EventData;
import model.listeners.Listeners;
import model.listeners.Sender;

public class GameField {
	private ObjectInfo objectInfo;
	private Listeners listeners;

	public GameField(ObjectInfo objectInfo, Listeners listeners) {
		super();
		this.listeners = listeners;
		this.setObjectInfo(objectInfo);
	}

	private void setObjectInfo(ObjectInfo objectInfo) {
		this.objectInfo = objectInfo;
		EventData event = new EventData(Sender.GAME_FIELD, Event.INITIALIZE,
				this.objectInfo);
		listeners.notifyListeners(event);
	}

	public ObjectInfo getObjectInfo() {
		return objectInfo;
	}
}