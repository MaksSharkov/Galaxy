package model.objects;

import model.Constants;
import model.GameField;
import model.ObjectInfo;
import model.listeners.Direction;
import model.listeners.Event;
import model.listeners.EventData;
import model.listeners.Listeners;
import model.listeners.Sender;
import model.listeners.ShipTypes;

public class PlayerShip extends Ship {

	public PlayerShip(ObjectInfo objectInfo, GameField gameField,
			Listeners listeners) {
		super(objectInfo, gameField, listeners);

	}

	public void shiftLeft() {
		setDirection(Direction.LEFT);
	}

	public void shiftRight() {
		setDirection(Direction.RIGHT);
	}

	@Override
	protected Direction getBulletDirection() {
		return Direction.UP;
	}

	@Override
	protected void notifyAboutCreation() {
		EventData event = new EventData(Sender.SHIP, Event.INITIALIZE,
				this.objectInfo);
		listeners.notifyListeners(event);
	}

	@Override
	protected void notifyAboutShoot() {
		EventData event = new EventData(Sender.SHIP, Event.SHOOT,
				this.objectInfo);
		listeners.notifyListeners(event);
	}

	@Override
	protected ShipTypes getShipType() {
		return ShipTypes.PLAYER_SHIP;
	}

	@Override
	protected Direction chooseDirection() {
		return getCurrentDirection();
	}

	@Override
	protected void notifyAboutMovement() {
		EventData event = new EventData(Sender.SHIP, Event.SHIFT,
				this.objectInfo);
		listeners.notifyListeners(event);
	}

	@Override
	protected int getMoveStep() {
		return Constants.PLAYER_MOVE_STEP;
	}

	@Override
	protected boolean isStoppingAfterMoveNeeded() {
		return true;
	}

}
