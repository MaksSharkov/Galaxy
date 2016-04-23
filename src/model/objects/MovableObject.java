package model.objects;

import model.Constants;
import model.GameField;
import model.ObjectInfo;
import model.listeners.Direction;
import model.listeners.Listeners;

/**
 * Родительский абстрактный класс для всех игровых объектов, которые двигаются
 * внутри игрового поля. Каждый объект обеспечивается свои потоком.
 * 
 * @author Maks_Sh
 * 
 */
public abstract class MovableObject extends Thread {
	/**
	 * Информация об объекте (размер, цвет, позиция).
	 */
	protected ObjectInfo objectInfo;
	/**
	 * Игровое поле.
	 */
	protected GameField gameField;
	/**
	 * Список слушателей.
	 */
	protected Listeners listeners;

	/**
	 * Определяет жив ли объект (может ли он совершать какие лтбо действия:
	 * двигаться, стрелять и т.д.).
	 */
	private boolean isAlive = true;
	/**
	 * Определяет направление движения объекта.
	 */
	private Direction currentDirection = Direction.STAY;

	/**
	 * Метод "убивает" объект (устанавливает значение isAlive=false).
	 */
	public void kill() {
		isAlive = false;
	}

	/**
	 * Метод задает направление движения объекта.
	 * @param direction направление движения.
	 */
	public void setDirection(Direction direction) {
		this.currentDirection = direction;
	}

	/**
	 * Метод получает текущее состояние жизни объекта.
	 * @return если true, объект "жив", иначе объект "мертв".
	 */
	public boolean getIsAlive() {
		return isAlive;
	}

	/**
	 * Метод получает текущее направление движения объекта.
	 * @return направление движения
	 */
	public Direction getCurrentDirection() {
		return currentDirection;
	}

	/**
	 * Метод запускает поток объекта.
	 */
	@Override
	public void run() {
		try {
			while (isAlive) {
				currentDirection = chooseDirection();
				move(currentDirection);
				if (isStoppingAfterMoveNeeded()) {
					currentDirection = Direction.STAY;
				}
				Thread.sleep(Constants.THREAD_SLEEP);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Метод определяет, нужно ли объекту остановиться после совершения движения.
	 * @return если объекту необходимо остановиться после совершения движения - true, иначе false.
	 */
	protected abstract boolean isStoppingAfterMoveNeeded();

	/**
	 * Метод смещает объект в заданном направлении.
	 * @param direction направление движения
	 */
	protected abstract void move(Direction direction);

	/**
	 * Метод задает направление движения объекта.
	 * @return направление движения
	 */
	protected abstract Direction chooseDirection();

}
