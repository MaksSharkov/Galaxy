package model;

import java.awt.Point;

import model.listeners.Event;
import model.listeners.EventData;
import model.listeners.Listeners;
import model.listeners.Sender;
import model.listeners.ShipTypes;

public class EnemyShip extends Ship {

	private Thread shoot;

	public EnemyShip(ObjectInfo objectInfo, GameField gameField,
			Listeners listeners) {
		super(objectInfo, gameField, listeners);
		setDirection(Direction.RIGHT);
		shoot = new Thread(new EnemyShooter());
		shoot.start();
	}

	@Override
	protected Direction getBulletDirection() {
		return Direction.DOWN;
	}

	@Override
	protected void notifyAboutCreation() {
		EventData event = new EventData(Sender.ENEMY_SHIP, Event.INITIALIZE,
				this.objectInfo);
		listeners.notifyListeners(event);
	}

	@Override
	protected void notifyAboutShoot() {
		EventData event = new EventData(Sender.ENEMY_SHIP, Event.SHOOT,
				this.objectInfo);
		listeners.notifyListeners(event);
	}

	@Override
	protected ShipTypes getShipType() {
		return ShipTypes.ENEMY_SHIP;
	}

	private boolean isDirectionChangeNeeded(Point newPosition) {
		int width = this.gameField.getObjectInfo().getDim().width;
		return (newPosition.x <= 0)
				|| (newPosition.x >= width - Constants.ENEMY_SHIP_WIDTH);
	}

	@Override
	protected Direction chooseDirection() {
		Direction result = getCurrentDirection();

		Point currentPosition = this.objectInfo.getPosition();
		if (isDirectionChangeNeeded(currentPosition)) {
			switch (getCurrentDirection()) {
			case RIGHT:
				result = Direction.LEFT;
				break;
			case LEFT:
				result = Direction.RIGHT;
				break;
			default:
				result = Direction.STAY;
				break;
			}
		}

		return result;

	}

	@Override
	protected void notifyAboutMovement() {
		EventData event = new EventData(Sender.ENEMY_SHIP, Event.SHIFT,
				this.objectInfo);
		listeners.notifyListeners(event);
	}

	@Override
	protected int getMoveStep() {
		return Constants.ENEMY_MOVE_STEP;
	}

	@Override
	protected boolean isStoppingAfterMoveNeeded() {
		return false;
	}

	private final class EnemyShooter implements Runnable {
		public void run() {
			while (getIsAlive()) {
				shootBullet();
				long delay = (int) ((Math.random() * 4) + 1) * 1000;
				System.out.println("Sleep=" + delay / 1000);
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
