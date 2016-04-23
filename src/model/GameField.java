package model;

import model.listeners.Event;
import model.listeners.EventData;
import model.listeners.Listeners;
import model.listeners.Sender;

/**
 * ����� �������� ����.
 * �������� ��� ���������� � ������� ����.
 * @author Maks_Sh
 *
 */
public class GameField {
	/**
	 * ���������� �� ������� (������, ����, �������)
	 */
	private ObjectInfo objectInfo;
	/**
	 * ������ ����������.
	 */
	private Listeners listeners;

	/**
	 * ����������� �������� ����.
	 * @param objectInfo ���������� �� ������� (������, ����, �������).
	 * @param listeners ������ ����������.
	 */
	public GameField(ObjectInfo objectInfo, Listeners listeners) {
		super();
		this.listeners = listeners;
		this.setObjectInfo(objectInfo);
	}

	/**
	 * ����� ������ ���������� � ������� ����.
	 * @param objectInfo ���������� �� ������� (������, ����, �������).
	 */
	private void setObjectInfo(ObjectInfo objectInfo) {
		this.objectInfo = objectInfo;
		EventData event = new EventData(Sender.GAME_FIELD, Event.INITIALIZE,
				this.objectInfo);
		listeners.notifyListeners(event);
	}

	/**
	 * ����� �������� ���������� � ������� ����.
	 * @return ���������� �� ������� (������, ����, �������).
	 */
	public ObjectInfo getObjectInfo() {
		return objectInfo;
	}
}