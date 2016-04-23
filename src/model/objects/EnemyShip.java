package model.objects;

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
 * Класс вражеского корабля.
 * Содержит набор методов и логику определяющих поведение корабля на игровом поле.
 * @author Maks_Sh
 *
 */
public class EnemyShip extends Ship implements Listener {

	/**
	 * Поток для стрельбы вражеского корабля.
	 */
	private Thread shoot;

	/**
	 * Конструктор вражеского корабля
	 * @param objectInfo информация об объекте (размер, цвет, позиция).
	 * @param gameField игровое поле.
	 * @param listeners список слушателей.
	 */
	public EnemyShip(ObjectInfo objectInfo, GameField gameField,
			Listeners listeners) {
		super(objectInfo, gameField, listeners);
		setDirection(Direction.RIGHT);
		shoot = new Thread(new EnemyShooter());
		this.shoot.start();
		this.listeners.addListener(this);
	}

	/**
	 * Метод определяет направление движение пули при выстреле вражеского корабля.
	 */
	@Override
	protected Direction getBulletDirection() {
		return Direction.DOWN;
	}

	/**
	 * Метод оповещает слушателей о том, что был создан вражеский корабль.
	 */
	@Override
	protected void notifyAboutCreation() {
		EventData event = new EventData(Sender.ENEMY_SHIP, Event.INITIALIZE,
				this.objectInfo);
		listeners.notifyListeners(event);
	}

	/**
	 * Метод оповещает слушателей о том, что вражеский корабль совершил выстрел.
	 */
	@Override
	protected void notifyAboutShoot() {
		EventData event = new EventData(Sender.ENEMY_SHIP, Event.SHOOT,
				this.objectInfo);
		listeners.notifyListeners(event);
	}

	/**
	 * Метод возвращает тип вражеского корабля.
	 */
	@Override
	protected ShipTypes getShipType() {
		return ShipTypes.ENEMY_SHIP;
	}

	/**
	 * Метод определяет нужно ли кораблю сменить направление движения.
	 * @param newPosition позиция на которую будет смещен корабль при движении.
	 * @return если направление движения необходимо сменить вернет true, иначе false.
	 */
	private boolean isDirectionChangeNeeded(Point newPosition) {
		int width = this.gameField.getObjectInfo().getDim().width;
		return (newPosition.x <= 0)
				|| (newPosition.x >= width - Constants.ENEMY_SHIP_WIDTH);
	}

	/**
	 * Метод определяет новое направление движения для вражеского корабля.
	 */
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

	/**
	 * Метод оповещает слушателей о том, что вражеский корабль сместился на новую позицию.
	 */
	@Override
	protected void notifyAboutMovement() {
		EventData event = new EventData(Sender.ENEMY_SHIP, Event.SHIFT,
				this.objectInfo);
		listeners.notifyListeners(event);
	}

	/**
	 * Метод получает количество координат на которые необходимо сместить вражеский корабль при движении.
	 */
	@Override
	protected int getMoveStep() {
		return Constants.ENEMY_MOVE_STEP;
	}

	/**
	 * Метод определяет необходимо ли вражескому кораблю оставновиться после совершения движения.
	 * Всегда возвращает false.
	 */
	@Override
	protected boolean isStoppingAfterMoveNeeded() {
		return false;
	}

	/**
	 * Метод обработки события выстрела корабля игрока.
	 */
	public void handleEvent(EventData eventData) {
		Sender sender = eventData.getSender();
		model.listeners.Event event = eventData.getEvent();
		Object bulletObject = eventData.getObject();

		if (sender == Sender.BULLET
				&& event == model.listeners.Event.INITIALIZE) {
			dodgeTheBullets((Bullet) bulletObject);
			return;
		}
	}

	/**
	 * Метод определяет будет ли вражеский корабль уклоняться от пули игрока.
	 * @return вернет true если будет совершен маневр уклонения, иначе false.
	 */
	private boolean tryToDodgeTheBullets() {

		long probability = (int) ((Math.random() * Constants.MAXIMUM_PROBABILITY) + 1);
		if (probability <= Constants.DODGE_PROBABILITY) {
			return true;
		}

		return false;
	}

	/**
	 * Метод проверяет необходимо ли вражескому кораблю увернуться от пули игрока.
	 * @param bullet пуля игрока.
	 * @return если необходимо уворачиваться от пули игрока вернет true, иначе false.
	 */
	private boolean checkThatDodgeIsNeeded(Bullet bullet) {

		ObjectInfo bulletObject = bullet.getObjectInfo();
		int bulletCoordinate = bulletObject.getPosition().x;
		int shipCoordinate = getCenterShip(getShipType()).x;
		Direction direction = getCurrentDirection();

		if ((direction == Direction.LEFT)
				&& (shipCoordinate > bulletCoordinate)) {
			return true;
		}

		if ((direction == Direction.RIGHT)
				&& (shipCoordinate < bulletCoordinate)) {
			return true;
		}

		return false;
	}

	/**
	 * Метод выполняет маневр уклонения от пули игрока.
	 * @param bullet пуля игрока.
	 */
	private synchronized void dodgeTheBullets(Bullet bullet) {
		Direction newDirection;

		if (checkThatDodgeIsNeeded(bullet)) {
			if (tryToDodgeTheBullets()) {
				switch (getCurrentDirection()) {
				case RIGHT:
					newDirection = Direction.LEFT;
					break;
				case LEFT:
					newDirection = Direction.RIGHT;
					break;
				default:
					newDirection = Direction.STAY;
					break;
				}

				setDirection(newDirection);
			}
		}
	}

	/**
	 * Класс с потоком для стрельбы вражеского корабля.
	 * Рассчитывает время до следующего выстрела.
	 * @author Maks_Sh
	 *
	 */
	private final class EnemyShooter implements Runnable {
		public void run() {
			while (getIsAlive()) {
				shootBullet();
				long delay = (int) ((Math.random() * 4) + 1) * 1000;
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
