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

public class Bullet extends MovableObject {

	private ShipTypes shipType;

	private Listeners bulletsListeners = new Listeners();

	public Bullet(ObjectInfo objectInfo, GameField gameField,
			Listeners listeners, Direction direction, ShipTypes shipType) {
		super();
		setDirection(direction);
		this.shipType = shipType;
		this.listeners = listeners;
		this.gameField = gameField;
		this.setObjectInfo(objectInfo);
	}

	private void setObjectInfo(ObjectInfo objectInfo) {
		this.objectInfo = objectInfo;

		notifyAbout(Event.INITIALIZE);
	}

	public ObjectInfo getObjectInfo() {
		return objectInfo;
	}

	public GameField getGameField() {
		return gameField;
	}

	public void setGameField(GameField gameField) {
		this.gameField = gameField;
	}

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

	@Override
	protected boolean isStoppingAfterMoveNeeded() {
		return false;
	}

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

	@Override
	public void kill() {
		super.kill();
		notifyAbout(Event.END_SHOOT);
	}

	@Override
	protected Direction chooseDirection() {
		return getCurrentDirection();
	}

	public void addListener(Listener name) {
		bulletsListeners.addListener(name);
	}

}
