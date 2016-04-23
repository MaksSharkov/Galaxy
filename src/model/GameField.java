package model;

import model.listeners.Event;
import model.listeners.EventData;
import model.listeners.Listeners;
import model.listeners.Sender;

/**
 * Класс игрового поля.
 * Содержит всю информацию о игровом поле.
 * @author Maks_Sh
 *
 */
public class GameField {
	/**
	 * Информация об объекте (размер, цвет, позиция)
	 */
	private ObjectInfo objectInfo;
	/**
	 * Список слушателей.
	 */
	private Listeners listeners;

	/**
	 * Конструктор игрового поля.
	 * @param objectInfo информация об объекте (размер, цвет, позиция).
	 * @param listeners список слушателей.
	 */
	public GameField(ObjectInfo objectInfo, Listeners listeners) {
		super();
		this.listeners = listeners;
		this.setObjectInfo(objectInfo);
	}

	/**
	 * Метод задает информацию о игровом поле.
	 * @param objectInfo информация об объекте (размер, цвет, позиция).
	 */
	private void setObjectInfo(ObjectInfo objectInfo) {
		this.objectInfo = objectInfo;
		EventData event = new EventData(Sender.GAME_FIELD, Event.INITIALIZE,
				this.objectInfo);
		listeners.notifyListeners(event);
	}

	/**
	 * Метод получает информацию о игровом поле.
	 * @return информация об объекте (размер, цвет, позиция).
	 */
	public ObjectInfo getObjectInfo() {
		return objectInfo;
	}
}