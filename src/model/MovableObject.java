package model;

import model.listeners.Listeners;

public abstract class MovableObject extends Thread {
	protected ObjectInfo objectInfo;
	protected GameField gameField;
	protected Listeners listeners;

	private boolean isAlive = true;
	private Direction currentDirection = Direction.STAY;

	public void kill() {
		isAlive = false;
	}

	public void setDirection(Direction direction) {
		this.currentDirection = direction;
	}
	
	public boolean getIsAlive() {
		return isAlive;
	}

	public Direction getCurrentDirection() {
		return currentDirection;
	}

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

	protected abstract boolean isStoppingAfterMoveNeeded();

	protected abstract void move(Direction direction);

	protected abstract Direction chooseDirection();

}
