package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Constants;
import model.ObjectInfo;
import model.listeners.EventData;
import model.listeners.Listener;
import model.listeners.Sender;
import model.objects.Bullet;
import resourcer.Resourcer;
import controller.Controller;

/**
 * �������� �� ����������� ���������� (������������).
 * 
 * @author Maks_Sh
 * 
 */
public class View extends JFrame implements Listener {

	private static final long serialVersionUID = 7798987455797195035L;
	/**
	 * ��������� ����.
	 */
	private final String title = "Galaxy";
	/**
	 * ������ ����������.
	 */
	private Controller controller;
	/**
	 * ���������� ������� �����.
	 */
	private static int score = 0;
	/**
	 * ���������� ������ ��������.
	 */
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	/**
	 * �������� ������ ������ ��������.
	 */
	private int halfScreenWidth = screenSize.width / 2;
	/**
	 * �������� ������ ������ ��������.
	 */
	private int halfScreenHeight = screenSize.height / 2;

	/**
	 * ������ �������� ����.
	 */
	private JPanel gameField = new JPanel();
	/**
	 * ������ ��������� ������ � ���������� ���������.
	 */
	private JPanel ship = new TexturedPanel(Constants.PLAYER_IMAGE);
	/**
	 * ������ ��������� ����� � ���������� ���������.
	 */
	private JPanel enemyShip = new TexturedPanel(Constants.ENEMY_IMAGE);
	/**
	 * ������ ������������ ������� ����.
	 */
	private static JLabel scores = new JLabel(String.format("%-7s%-3d",
			Resourcer.getString("msg.score"), score));

	/**
	 * ����������� �������������.
	 * @param controller ������ ����������.
	 */
	public View(Controller controller) {
		this.controller = controller;
		this.controller.addListener(this);
		this.setLayout(new BorderLayout());
		this.setTitle(title);
		this.setPreferredSize(new Dimension(Constants.VIEW_INITIALIZE_WIDTH,
				Constants.VIEW_INITIALIZE_HEIGHT));
		this.setLocation(halfScreenWidth
				- model.Constants.VIEW_INITIALIZE_WIDTH / 2, halfScreenHeight
				- model.Constants.VIEW_INITIALIZE_HEIGHT / 2);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();

		initKeyListener();
		initScoreField();
	}

	/**
	 * ����� �������������� ������ � �������� ������.
	 */
	private void initScoreField() {
		JPanel scorePanel = new JPanel();
		scorePanel.setLayout(new FlowLayout());
		scorePanel.add(scores);
		this.getContentPane().add(scorePanel, BorderLayout.SOUTH);
	}

	/**
	 * ����� ������������ ������� �������.
	 */
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

	/**
	 * ����� ���������������� ��������� ������� ������ ����������.
	 */
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

	/**
	 * ����� ��������� ������ ���������� ���������.
	 * @param data ������ � ��������� ��������� (������, ��������� � �.�.)
	 */
	private void initializeEnemyShip(ObjectInfo data) {
		gameField.add(enemyShip);
		enemyShip.setVisible(true);
		enemyShip.setBackground(data.getColor());
		enemyShip.setSize(data.getDim());
		enemyShip.setLocation(data.getPosition());
	}

	/**
	 * ����� ��������� ������ ��������� ������.
	 * @param data ������ � ��������� ������ (������, ��������� � �.�.)
	 */
	private void initializeShip(ObjectInfo data) {
		gameField.add(ship);
		ship.setVisible(true);
		ship.setBackground(data.getColor());
		ship.setSize(data.getDim());
		ship.setLocation(data.getPosition());
	}

	/**
	 * ����� ��������� ������ ����� ����.
	 * @param bullet ������ � ���� (������, ��������� � �.�.)
	 */
	private void initializeBullet(Bullet bullet) {
		ObjectInfo bulletObject = bullet.getObjectInfo();
		final BulletPanel newBullet = new BulletPanel();
		newBullet.setVisible(true);
		newBullet.setBackground(bulletObject.getColor());
		newBullet.setSize(bulletObject.getDim());
		newBullet.setLocation(bulletObject.getPosition());
		bullet.addListener(newBullet);
		gameField.add(newBullet);
	}

	/**
	 * ����� ��������� ������ �������� ����.
	 * @param data ������ � ������� ���� (������, ��������� � �.�.)
	 */
	private void initializeGameField(ObjectInfo data) {
		gameField.setLayout(null);
		gameField.setVisible(true);
		gameField.setLocation(data.getPosition());
		gameField.setBackground(data.getColor());
		gameField.setSize(data.getDim());
		this.getContentPane().add(gameField, BorderLayout.CENTER);
	}

	/**
	 * ����� �������� ��������� �������� ��������� ������ �� ������.
	 * @param data ������ � ��������� ������ (������, ��������� � �.�.)
	 */
	private synchronized void moveShip(ObjectInfo data) {
		ship.setLocation(data.getPosition());
	}

	/**
	 * ����� �������� ��������� �������� ��������� ����� �� ������.
	 * @param data ������ � ��������� ����� (������, ��������� � �.�.)
	 */
	private synchronized void moveEnemy(ObjectInfo data) {
		enemyShip.setLocation(data.getPosition());
	}

	/**
	 * ����� �������� ������� �������� ������� �����.
	 * @return ���������� �������� ������� �����.
	 */
	public static int getScore() {
		return score;
	}

	/**
	 * ����� ������������� ����� �������� ������� ����� � ������� ��� �� ������ �����.
	 * @param score ����� �������� ������� �����.
	 */
	public static void setScore(int score) {
		View.score = score;
		String scoreText = Resourcer.getString("msg.score");
		scores.setText(String.format("%-7s%-3d", scoreText, View.score));
	}
}
