package model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class BulletList {
	private List<Bullet> list = new ArrayList<Bullet>();

	public synchronized int size() {
		return list.size();
	}

	public synchronized boolean isEmpty() {
		return list.isEmpty();
	}

	public synchronized boolean addIfAvaible(Bullet e) {
		if (isBulletsAvaible()) {
			return list.add(e);
		} else {
			return false;
		}
	}

	public synchronized boolean isBulletsAvaible() {
		return list.size() < Constants.MAX_BULLET_COUNT_PER_SHIP;
	}

	public synchronized boolean remove(Object o) {
		return list.remove(o);
	}

	public synchronized void clear() {
		list.clear();
	}

	public synchronized boolean haveShooted(Ship ship) {
		boolean isShooted = false;

		for (Bullet bullet : list) {
			synchronized (ship) {
				Rectangle shipRectangle = ship.getObjectInfo().getRectangle();

				Rectangle bulletRectangle = bullet.getObjectInfo()
						.getRectangle();

				isShooted = bulletRectangle.intersects(shipRectangle);
				if (isShooted) {
					bullet.kill();
					break;
				}
			}
		}

		return isShooted;
	}

	public synchronized void flushOutOfScreenBullets() {
		for (Bullet bullet : list) {
			if (!bullet.isOnScreen()) {
				bullet.kill();
				list.remove(bullet);
				break;
			}
		}
	}
}
