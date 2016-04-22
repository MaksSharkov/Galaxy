package view;

import javax.swing.JPanel;

import model.ObjectInfo;
import model.listeners.EventData;
import model.listeners.Listener;
import model.listeners.Sender;

public class BulletPanel extends JPanel implements Listener {
	private static final long serialVersionUID = -7116372245775847002L;

	@Override
	public void handleEvent(EventData eventData) {
		Sender sender = eventData.getSender();

		switch (eventData.getEvent()) {
		case SHIFT:
			moveBullet(this, (ObjectInfo) eventData.getObject());
			break;
		case END_SHOOT:
			if (sender == Sender.ENEMY_BULLET) {
				int score;
				score = View.getScore() + 2;
				View.setScore(score);
				return;
			}
			if (sender == Sender.BULLET) {
				int score;
				score = View.getScore() - 2;
				View.setScore(score);
				return;
			}
			this.setVisible(false);
			break;
		default:
			break;
		}
	}

	private synchronized void moveBullet(JPanel bulletPanel, ObjectInfo data) {
		this.setLocation(data.getPosition());
	}

}
