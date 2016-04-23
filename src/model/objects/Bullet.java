package model.objects;

import java.awt.Dimension;
import java.awt.Point;

import model.Constants;
import model.GameField;
import model.ObjectInfo;
import model.listeners.Direction;
import model.listeners.Event;
import model.listeners.EventData;
import model.listeners.Listener;
import model.listeners.Listeners;
import model.listeners.Sender;
import model.listeners.ShipTypes;
/**
 * Класс пули.
 * Класс содержит набор методов определяющих поведение пули на игровом поле.
 * @author Maks_Sh
 *
 */
public class Bullet extends MovableObject {

	/**
	 * Тип корабля
	 */
	private ShipTypes shipType;

	/**
	 * Список слушателей
	 */
	private Listeners bulletsListeners = new Listeners();

	/**
	 * Конструктор пули
	 * @param objectInfo информация об объекте (размер, цвет, позиция).
	 * @param gameField игровое поле.
	 * @param listeners список слушателей.
	 * @param direction направление движения.
	 * @param shipType тип корабля.
	 */
	public Bullet(ObjectInfo objectInfo, GameField gameField,
			Listeners listeners, Direction direction, ShipTypes shipType) {
		super();
		setDirection(direction);
		this.shipType = shipType;
		this.listeners = listeners;
		this.gameField = gameField;
		this.setObjectInfo(objectInfo);
	}

	/**
	 * Метод задает инфомрацию о пуле.
	 * @param objectInfo информация об объекте (размер, цвет, позиция).
	 */
	private void setObjectInfo(ObjectInfo objectInfo) {
		this.objectInfo = objectInfo;

		notifyAbout(Event.INITIALIZE);
	}

	/**
	 * Метод получает информацию о пуле.
	 * @return информация об объекте.
	 */
	public ObjectInfo getObjectInfo() {
		return objectInfo;
	}

	/**
	 * Метод получает информацию о игровом поле.
	 * @return игровое поле.
	 */
	public GameField getGameField() {
		return gameField;
	}

	/**
	 * Метод задает информацию о игровом поле.
	 * @param gameField игровое поле.
	 */
	public void setGameField(GameField gameField) {
		this.gameField = gameField;
	}

	/**
	 * Метод проверяет улетела пуля за пределы игрового поля или нет.
	 * @return если пуля улетела за пределы игрового поля вернет true, иначе false.
	 */
	public boolean isOnScreen() {
		Point currentPosition = objectInfo.getPosition();

		if (getCurrentDirection() == Direction.UP) {
			return currentPosition.y >= 0 - Constants.BULLET_HEIGHT;
		} else if (getCurrentDirection() == Direction.DOWN) {
			ObjectInfo gameFieldInfo = gameField.getObjectInfo();
			Dimension gameFieldDimension = gameFieldInfo.getDim();
			return currentPosition.y <= gameFieldDimension.height;
		} else {
			return false;
		}
	}

	/**
	 * Метод оповещает слушателей о произошедшем событии.
	 * @param inputEvent произошедшее событие.
	 */
	protected void notifyAbout(Event inputEvent) {
		Sender sender = null;
		switch (shipType) {
		case PLAYER_SHIP:
			sender = Sender.BULLET;
			break;
		case ENEMY_SHIP:
			sender = Sender.ENEMY_BULLET;
			break;
		default:
			break;
		}

		if ((inputEvent == Event.INITIALIZE)) {
			listeners.notifyListeners(new EventData(sender, inputEvent, this));
		} else if ((inputEvent == Event.SHIFT)
				|| (inputEvent == Event.END_SHOOT)) {
			bulletsListeners.notifyListeners(new EventData(sender, inputEvent,
					objectInfo));
		}
	}

	/**
	 * Метод определяет необходимо ли пуле оставновиться после совершения движения.
	 * Всегда возвращает false.
	 */
	@Override
	protected boolean isStoppingAfterMoveNeeded() {
		return false;
	}

	/**
	 * Метод смещает пулю по заданному направлению.
	 */
	@Override
	protected void move(Direction direction) {
		int newDirection = 0;
		switch (direction) {
		case UP:
			newDirection = -1;
			break;
		case DOWN:
			newDirection = 1;
			break;
		default:
			return;
		}
		Point oldPosition = this.objectInfo.getPosition();
		Point newPosition = new Point(oldPosition.x, oldPosition.y
				+ newDirection * Constants.BULLET_MOVE_STEP);

		this.objectInfo.setPosition(newPosition);
		notifyAbout(Event.SHIFT);

	}

	/**
	 * Метод "убивает" пулю (останавливает поток).
	 */
	@Override
	public void kill() {
		super.kill();
		notifyAbout(Event.END_SHOOT);
	}

	/**
	 * Метод выбирает направление движения пули.
	 */
	@Override
	protected Direction chooseDirection() {
		return getCurrentDirection();
	}

	/**
	 * Метод добавляет нового слушателя.
	 * @param name имя слушателя.
	 */
	public void addListener(Listener name) {
		bulletsListeners.addListener(name);
	}

}
