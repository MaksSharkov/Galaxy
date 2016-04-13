package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Bullet;
import model.Constants;
import model.ObjectInfo;
import model.listeners.EventData;
import model.listeners.Listener;
import model.listeners.Sender;
import controller.Controller;

public class View extends JFrame implements Listener {

	private static final long serialVersionUID = 7798987455797195035L;
	private final String title = "Galaxy";
	private Controller controller;
	private int score = 0;

	private JPanel gameField = new JPanel();
	private JPanel ship = new TexturedPanel("/resources/ship.png");
	private JPanel enemyShip = new TexturedPanel("/resources/enemy.png");
	private List<JPanel> bulletList = new ArrayList<JPanel>();
	JLabel scores = new JLabel("Score: " + score);

	public View(Controller controller) {
		this.controller = controller;
		this.controller.addListener(this);
		this.setLayout(new BorderLayout());
		this.setTitle(title);
		this.setPreferredSize(new Dimension(Constants.VIEW_INITIALIZE_WIDTH,
				Constants.VIEW_INITIALIZE_HEIGHT));
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();

		initKeyListener();
		initScoreField();
	}

	private void initScoreField() {
		JPanel scorePanel = new JPanel();
		scorePanel.setLayout(new FlowLayout());
		scorePanel.add(scores);
		this.getContentPane().add(scorePanel, BorderLayout.SOUTH);
	}

	public void handleEvent(EventData eventData) {
		Sender sender = eventData.getSender();
		model.listeners.Event event = eventData.getEvent();
		Object bulletObject = eventData.getObject();

		if (sender == Sender.BULLET
				&& event == model.listeners.Event.INITIALIZE) {
			initializeBullet((Bullet) bulletObject);
			return;
		}
		if (sender == Sender.ENEMY_BULLET
				&& event == model.listeners.Event.INITIALIZE) {
			initializeBullet((Bullet) bulletObject);
			return;
		}

		ObjectInfo data = (ObjectInfo) bulletObject;

		if (sender == Sender.GAME_FIELD
				&& event == model.listeners.Event.INITIALIZE) {
			initializeGameField(data);
		}
		if (sender == Sender.SHIP && event == model.listeners.Event.INITIALIZE) {
			initializeShip(data);
		}
		if (sender == Sender.SHIP && event == model.listeners.Event.SHIFT) {
			moveShip(data);
		}
		if (sender == Sender.ENEMY_SHIP
				&& event == model.listeners.Event.INITIALIZE) {
			initializeEnemyShip(data);
		}
		if (sender == Sender.ENEMY_SHIP && event == model.listeners.Event.SHIFT) {
			moveEnemy(data);
		}
	}

	private void initKeyListener() {
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent event) {
				super.keyPressed(event);
				switch (event.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					controller.shiftShipLeft();
					break;
				case KeyEvent.VK_RIGHT:
					controller.shiftShipRight();
					break;
				case KeyEvent.VK_UP:
					controller.makeShoot();
					break;
				default:
					break;
				}

			}

		});
	}

	private void initializeEnemyShip(ObjectInfo data) {
		gameField.add(enemyShip);
		enemyShip.setVisible(true);
		enemyShip.setBackground(data.getColor());
		enemyShip.setSize(data.getDim());
		enemyShip.setLocation(data.getPosition());
	}

	private void initializeShip(ObjectInfo data) {
		gameField.add(ship);
		ship.setVisible(true);
		ship.setBackground(data.getColor());
		ship.setSize(data.getDim());
		ship.setLocation(data.getPosition());
	}

	private void initializeBullet(Bullet bullet) {
		ObjectInfo bulletObject = bullet.getObjectInfo();
		final JPanel newBullet = new JPanel();
		newBullet.setVisible(true);
		newBullet.setBackground(bulletObject.getColor());
		newBullet.setSize(bulletObject.getDim());
		newBullet.setLocation(bulletObject.getPosition());

		bullet.addListener(new Listener() {
			public void handleEvent(EventData eventData) {
				Sender sender = eventData.getSender();

				switch (eventData.getEvent()) {
				case SHIFT:
					moveBullet(newBullet, (ObjectInfo) eventData.getObject());
					break;
				case END_SHOOT:
					if (sender == Sender.ENEMY_BULLET) {
						addPoint();
						return;
					}
					if (sender == Sender.BULLET) {
						delPoint();
						return;
					}
					newBullet.setVisible(false);
					bulletList.remove(newBullet);
					break;
				default:
					break;
				}
			}
		});
		gameField.add(newBullet);
	}

	private void addPoint() {
		score += 2;
		scores.setText("Score: " + score);
	}
	
	private void delPoint() {
		score -= 2;
		scores.setText("Score: " + score);
	}

	private void initializeGameField(ObjectInfo data) {
		gameField.setLayout(null);
		gameField.setVisible(true);
		gameField.setLocation(data.getPosition());
		gameField.setBackground(data.getColor());
		gameField.setSize(data.getDim());
		this.getContentPane().add(gameField, BorderLayout.CENTER);
	}

	private synchronized void moveShip(ObjectInfo data) {
		ship.setLocation(data.getPosition());
	}

	private synchronized void moveEnemy(ObjectInfo data) {
		enemyShip.setLocation(data.getPosition());
	}

	private synchronized void moveBullet(JPanel bulletPanel, ObjectInfo data) {
		bulletPanel.setLocation(data.getPosition());
	}

}
