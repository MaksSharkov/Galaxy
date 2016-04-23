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
 * ќтвечает за отображение информации (визуализацию).
 * 
 * @author Maks_Sh
 * 
 */
public class View extends JFrame implements Listener {

	private static final long serialVersionUID = 7798987455797195035L;
	/**
	 * «аголовок окна.
	 */
	private final String title = "Galaxy";
	/**
	 * ќбъект контроллер.
	 */
	private Controller controller;
	/**
	 *  оличество игровых очков.
	 */
	private static int score = 0;
	/**
	 * –азрешение экрана монитора.
	 */
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	/**
	 * ѕоловина ширины экрана монитора.
	 */
	private int halfScreenWidth = screenSize.width / 2;
	/**
	 * ѕоловина высоты экрана монитора.
	 */
	private int halfScreenHeight = screenSize.height / 2;

	/**
	 * ѕанель игрового пол€.
	 */
	private JPanel gameField = new JPanel();
	/**
	 * ѕанель кораблика игрока с наложенной текстурой.
	 */
	private JPanel ship = new TexturedPanel(Constants.PLAYER_IMAGE);
	/**
	 * ѕанель кораблика врага с наложенной текстурой.
	 */
	private JPanel enemyShip = new TexturedPanel(Constants.ENEMY_IMAGE);
	/**
	 * ѕанель отображающа€ игровые очки.
	 */
	private static JLabel scores = new JLabel(String.format("%-7s%-3d",
			Resourcer.getString("msg.score"), score));

	/**
	 *  онструктор представлени€.
	 * @param controller объект контроллер.
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
	 * ћетод инициализирует панель с игровыми очками.
	 */
	private void initScoreField() {
		JPanel scorePanel = new JPanel();
		scorePanel.setLayout(new FlowLayout());
		scorePanel.add(scores);
		this.getContentPane().add(scorePanel, BorderLayout.SOUTH);
	}

	/**
	 * ћетод обрабатывает игровые событи€.
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
	 * ћетод инициализирующий слушател€ нажати€ клавиш клавиатуры.
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
	 * ћетод создающий панель вражеского кораблика.
	 * @param data данные о вражеском кораблике (размер, положение и т.д.)
	 */
	private void initializeEnemyShip(ObjectInfo data) {
		gameField.add(enemyShip);
		enemyShip.setVisible(true);
		enemyShip.setBackground(data.getColor());
		enemyShip.setSize(data.getDim());
		enemyShip.setLocation(data.getPosition());
	}

	/**
	 * ћетод создающий панель кораблика игрока.
	 * @param data данные о кораблике игрока (размер, положение и т.д.)
	 */
	private void initializeShip(ObjectInfo data) {
		gameField.add(ship);
		ship.setVisible(true);
		ship.setBackground(data.getColor());
		ship.setSize(data.getDim());
		ship.setLocation(data.getPosition());
	}

	/**
	 * ћетод создающий панель новой пули.
	 * @param bullet данные о пуле (размер, положение и т.д.)
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
	 * ћетод создающий панель игрового пол€.
	 * @param data данные о игровом поле (размер, положение и т.д.)
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
	 * ћетод измен€ет положение панельки кораблика игрока на экране.
	 * @param data данные о кораблике игрока (размер, положение и т.д.)
	 */
	private synchronized void moveShip(ObjectInfo data) {
		ship.setLocation(data.getPosition());
	}

	/**
	 * ћетод измен€ет положение панельки кораблика врага на экране.
	 * @param data данные о кораблике врага (размер, положение и т.д.)
	 */
	private synchronized void moveEnemy(ObjectInfo data) {
		enemyShip.setLocation(data.getPosition());
	}

	/**
	 * ћетод получает текущее значение игровых очков.
	 * @return возвращает значение игровых очков.
	 */
	public static int getScore() {
		return score;
	}

	/**
	 * ћетод устанавливает новое значение игровых очков и выводит его на панель очков.
	 * @param score новое значение игровых очков.
	 */
	public static void setScore(int score) {
		View.score = score;
		String scoreText = Resourcer.getString("msg.score");
		scores.setText(String.format("%-7s%-3d", scoreText, View.score));
	}
}
