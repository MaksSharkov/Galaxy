package model.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import model.BulletList;
import model.Constants;
import model.GameField;
import model.ObjectInfo;
import model.Sound;
import model.listeners.Direction;
import model.listeners.Listeners;
import model.listeners.ShipTypes;

public abstract class Ship extends MovableObject {

	private BulletList bullets = new BulletList();
	private Sound shootSound = new Sound(Constants.SHOOT_SOUND);

	public Ship(ObjectInfo objectInfo, GameField gameField, Listeners listeners) {
		super();
		this.listeners = listeners;
		this.gameField = gameField;
		this.setObjectInfo(objectInfo);
	}

	protected void setObjectInfo(ObjectInfo objectInfo) {
		this.objectInfo = objectInfo;
		notifyAboutCreation();
	}

	public ObjectInfo getObjectInfo() {
		return objectInfo;
	}

	public void flushOutScreenBullets() {
		bullets.flushOutOfScreenBullets();
	}

	public GameField getGameField() {
		return gameField;
	}

	public void setGameField(GameField gameField) {
		this.gameField = gameField;
	}

	public Point getCenterShip(ShipTypes shipType) {
		int bulletOffset = 0;
		switch (shipType) {
		case PLAYER_SHIP:
			bulletOffset = 0;
			break;
		case ENEMY_SHIP:
			bulletOffset = this.objectInfo.getDim().height;
			break;
		default:
			break;
		}
		return new Point(this.objectInfo.getPosition().x
				+ this.objectInfo.getDim().width / 2,
				this.objectInfo.getPosition().y + bulletOffset);
	}

	public void shootBullet() {
		if (bullets.isBulletsAvaible()) {
			Dimension bulletDimension = new Dimension(Constants.BULLET_WIDTH,
					Constants.BULLET_HEIGHT);
			Point bulletPosition = new Point(getCenterShip(getShipType()));
			ObjectInfo bulletInfo = new ObjectInfo(bulletDimension,
					Color.WHITE, bulletPosition);
			Bullet bullet = new Bullet(bulletInfo, this.gameField,
					this.listeners, getBulletDirection(), getShipType());

			if (bullets.addIfAvaible(bullet)) {
				shootSound.play();
				bullet.start();
				notifyAboutShoot();
			}
		}
	}

	public synchronized BulletList getBullets() {
		return bullets;
	}

	public synchronized boolean isShootedBy(Ship ship) {
		BulletList shipBullets = ship.getBullets();
		return shipBullets.haveShooted(this);
	}

	@Override
	protected synchronized void move(Direction direction) {
		int newDirection = 0;
		switch (direction) {
		case LEFT:
			newDirection = -1;
			break;
		case RIGHT:
			newDirection = 1;
			break;
		default:
			return;
		}

		int step = getMoveStep();
		moveShip(step, newDirection);

	}

	public void moveShip(int step, int newDirection) {
		Point oldPosition = this.objectInfo.getPosition();
		int shipWidth = objectInfo.getDim().width;
		int gameFieldWidth = this.gameField.getObjectInfo().getDim().width;

		Point newPosition = new Point(oldPosition.x + newDirection * step,
				oldPosition.y);

		if ((newPosition.x >= 0)
				&& (newPosition.x <= gameFieldWidth - shipWidth)) {
			this.objectInfo.setPosition(newPosition);
		}

		notifyAboutMovement();
	}

	protected abstract void notifyAboutShoot();

	protected abstract ShipTypes getShipType();

	protected abstract void notifyAboutCreation();

	protected abstract void notifyAboutMovement();

	protected abstract Direction getBulletDirection();

	protected abstract int getMoveStep();
}
