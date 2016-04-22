package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import model.listeners.Listener;
import model.listeners.Listeners;
import model.objects.EnemyShip;
import model.objects.PlayerShip;
import view.EndGameFrame;

public class Model {
	private GameField gameField;
	private PlayerShip playerShip;
	private EnemyShip enemyShip;
	private boolean isGameRunning;

	private Listeners listeners = new Listeners();

	private Sound playerDead = new Sound(Constants.PLAYERDEAD_SOUND);
	private Sound enemyDead = new Sound(Constants.ENEMYDEAD_SOUND);

	public void addListener(Listener name) {
		this.listeners.addListener(name);
	}

	public Model() {
		super();
	}

	private void initializeGameField() {
		Dimension gameFieldDimension = new Dimension(
				Constants.GAMEFIELD_INITIALIZE_WIDTH,
				Constants.GAMEFIELD_INITIALIZE_HEIGHT);
		Point gameFieldPosition = new Point(Constants.GAMEFIELD_INITIALIZE_X,
				Constants.GAMEFIELD_INITIALIZE_Y);
		ObjectInfo gameFieldInfo = new ObjectInfo(gameFieldDimension,
				Color.BLACK, gameFieldPosition);
		this.gameField = new GameField(gameFieldInfo, this.listeners);
	}

	private void initializeShip() {
		Dimension shipDimension = new Dimension(Constants.SHIP_WIDTH,
				Constants.SHIP_HEIGHT);
		Point shipPosition = new Point(Constants.SHIP_INITIALIZE_X,
				Constants.SHIP_INITIALIZE_Y);
		ObjectInfo shipInfo = new ObjectInfo(shipDimension, Color.BLACK,
				shipPosition);
		this.playerShip = new PlayerShip(shipInfo, this.gameField, listeners);
	}

	private void initializeEnemyShip() {
		Dimension enemyShipDimension = new Dimension(
				Constants.ENEMY_SHIP_WIDTH, Constants.ENEMY_SHIP_HEIGHT);
		Point enemyShipPosition = new Point(Constants.ENEMY_SHIP_INITIALIZE_X,
				Constants.ENEMY_SHIP_INITIALIZE_Y);
		ObjectInfo enemyShipInfo = new ObjectInfo(enemyShipDimension,
				Color.BLACK, enemyShipPosition);
		this.enemyShip = new EnemyShip(enemyShipInfo, this.gameField, listeners);
	}

	public void shiftShipLeft() {
		if (isGameRunning) {
			playerShip.shiftLeft();
		}
	}

	public void shiftShipRight() {
		if (isGameRunning) {
			playerShip.shiftRight();
		}
	}

	private void initialize() {
		initializeGameField();
		initializeShip();
		initializeEnemyShip();
	}

	public void startGame() {
		isGameRunning = true;
		initialize();
		enemyShip.start();
		playerShip.start();

		while (isGameRunning) {

			if (enemyShip.isShootedBy(playerShip)) {
				isGameRunning = false;
				playerShip.kill();
				enemyShip.kill();
				enemyDead.play();
				new EndGameFrame(true);
			}

			if (playerShip.isShootedBy(enemyShip)) {
				isGameRunning = false;
				playerShip.kill();
				enemyShip.kill();
				playerDead.play();
				new EndGameFrame(false);
			}

			playerShip.flushOutScreenBullets();
			enemyShip.flushOutScreenBullets();

			try {
				Thread.sleep(Constants.THREAD_SLEEP);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public void makeShoot() {
		if (isGameRunning) {
			playerShip.shootBullet();
		}
	}

	public void enemyShoot() {
		if (isGameRunning) {
			enemyShip.shootBullet();
		}
	}
}
