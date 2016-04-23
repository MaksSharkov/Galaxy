package model.objects;

import model.Constants;
import model.GameField;
import model.ObjectInfo;
import model.listeners.Direction;
import model.listeners.Listeners;

/**
 * ������������ ����������� ����� ��� ���� ������� ��������, ������� ���������
 * ������ �������� ����. ������ ������ �������������� ���� �������.
 * 
 * @author Maks_Sh
 * 
 */
public abstract class MovableObject extends Thread {
	/**
	 * ���������� �� ������� (������, ����, �������).
	 */
	protected ObjectInfo objectInfo;
	/**
	 * ������� ����.
	 */
	protected GameField gameField;
	/**
	 * ������ ����������.
	 */
	protected Listeners listeners;

	/**
	 * ���������� ��� �� ������ (����� �� �� ��������� ����� ���� ��������:
	 * ���������, �������� � �.�.).
	 */
	private boolean isAlive = true;
	/**
	 * ���������� ����������� �������� �������.
	 */
	private Direction currentDirection = Direction.STAY;

	/**
	 * ����� "�������" ������ (������������� �������� isAlive=false).
	 */
	public void kill() {
		isAlive = false;
	}

	/**
	 * ����� ������ ����������� �������� �������.
	 * @param direction ����������� ��������.
	 */
	public void setDirection(Direction direction) {
		this.currentDirection = direction;
	}

	/**
	 * ����� �������� ������� ��������� ����� �������.
	 * @return ���� true, ������ "���", ����� ������ "�����".
	 */
	public boolean getIsAlive() {
		return isAlive;
	}

	/**
	 * ����� �������� ������� ����������� �������� �������.
	 * @return ����������� ��������
	 */
	public Direction getCurrentDirection() {
		return currentDirection;
	}

	/**
	 * ����� ��������� ����� �������.
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
	 * ����� ����������, ����� �� ������� ������������ ����� ���������� ��������.
	 * @return ���� ������� ���������� ������������ ����� ���������� �������� - true, ����� false.
	 */
	protected abstract boolean isStoppingAfterMoveNeeded();

	/**
	 * ����� ������� ������ � �������� �����������.
	 * @param direction ����������� ��������
	 */
	protected abstract void move(Direction direction);

	/**
	 * ����� ������ ����������� �������� �������.
	 * @return ����������� ��������
	 */
	protected abstract Direction chooseDirection();

}
