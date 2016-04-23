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

/**
 * Родительский абстрактный класс для кораблика игрока и врага.
 * Содержит набор методов определяющих поведение кораблей на игровом поле.
 * @author Maks_Sh
 *
 */
public abstract class Ship extends MovableObject {

	/**
	 * Список пуль выстреленных данным кораблем.
	 */
	private BulletList bullets = new BulletList();
	/**
	 * Звук проигрываемый при выстреле пули кораблем.
	 */
	private Sound shootSound = new Sound(Constants.SHOOT_SOUND);

	/**
	 * Конструктор каорбля
	 * @param objectInfo информация об объекте (размер, цвет, позиция).
	 * @param gameField игровое поле.
	 * @param listeners список слушателей.
	 */
	public Ship(ObjectInfo objectInfo, GameField gameField, Listeners listeners) {
		super();
		this.listeners = listeners;
		this.gameField = gameField;
		this.setObjectInfo(objectInfo);
	}

	/**
	 * Метод задает информацию о текущем корабле.
	 * После задания информации оповещает всех слушателей о том, что был создан корабль.
	 * @param objectInfo информация об объекте (размер, цвет, позиция).
	 */
	protected void setObjectInfo(ObjectInfo objectInfo) {
		this.objectInfo = objectInfo;
		notifyAboutCreation();
	}

	/**
	 * Метод получает инормацию о данном корабле.
	 * @return информация о корабле.
	 */
	public ObjectInfo getObjectInfo() {
		return objectInfo;
	}

	/**
	 * Метод вызывает метод у списка пуль, который удаляет все пули улетевшие за пределы игрового поля.
	 */
	public void flushOutScreenBullets() {
		bullets.flushOutOfScreenBullets();
	}

	/**
	 * Метод получает текущее игровое поле.
	 * @return игровое поле.
	 */
	public GameField getGameField() {
		return gameField;
	}

	/**
	 * Метод задает текущее игровое поле.
	 * @param gameField игровое поле.
	 */
	public void setGameField(GameField gameField) {
		this.gameField = gameField;
	}

	/**
	 * Метод получает координаты центра текущего корабля.
	 * @param shipType тип корабля.
	 * @return координаты центра корабля.
	 */
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

	/**
	 * Метод выстреливает пулю из текущего корабля.
	 * После выстрела оповещает всех слушателей о том, что корабль совершил выстрел.
	 */
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

	/**
	 * Метод получает список всех пуль выпущенных данным кораблем.
	 * @return список пуль.
	 */
	public synchronized BulletList getBullets() {
		return bullets;
	}

	/**
	 * Метод определяет был ли подстрелен текущий корабль и чьим кораблем он был подстрелен.
	 * @param ship объект корабль.
	 * @return true если корабль был подстрелен, иначе false.
	 */
	public synchronized boolean isShootedBy(Ship ship) {
		BulletList shipBullets = ship.getBullets();
		return shipBullets.haveShooted(this);
	}

	/**
	 * Метод определяет в каком направлении нужно сместить корабль и вызывает метод смещения корабля с заданным шагом и направлением.
	 */
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

	/**
	 * Метод смещает корабль в заданном направлении и на заданное количество координат если это возможно.
	 * После смещения оповещает всех слушателей о том, что корабль сместился в новую позицию.
	 * @param step на сколько координат должен сместиться корабль.
	 * @param newDirection направление смещения корабля.
	 */
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

	/**
	 * Метод оповещает слушателей о том, что корабль совершил выстрел.
	 */
	protected abstract void notifyAboutShoot();

	/**
	 * Метод получает тип текущего корабля.
	 * @return тип корабля.
	 */
	protected abstract ShipTypes getShipType();

	/**
	 * Метод оповещает слушателей о том, что был создан корабль.
	 */
	protected abstract void notifyAboutCreation();

	/**
	 * Метод оповещает слушателей о том, что корабль сместился на новые координаты.
	 */
	protected abstract void notifyAboutMovement();

	/**
	 * Метод получает информацию о том, в какую сторону должна двигаться пуля.
	 * @return направление движения пули.
	 */
	protected abstract Direction getBulletDirection();

	/**
	 * Метод получает количество координат на которые должен сдвинуться корабль при смещении.
	 * @return количество координат.
	 */
	protected abstract int getMoveStep();
}
