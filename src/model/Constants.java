package model;

public class Constants {
	/**
	 *  оличество координат на которые сместитс€ корабль игрока при движении.
	 */
	public static final int PLAYER_MOVE_STEP = 10;
	/**
	 *  оличество координат на которые сместитс€ вражеский корабль при движении.
	 */
	public static final int ENEMY_MOVE_STEP = 2;
	/**
	 *  оличество координат на которые сместитс€ пул€ при движении.
	 */
	public static final int BULLET_MOVE_STEP = 10;

	/**
	 * Ўирина корабл€ игрока.
	 */
	public static final int SHIP_WIDTH = 70;
	/**
	 * ¬ысота корабл€ игрока.
	 */
	public static final int SHIP_HEIGHT = 100;
	/**
	 * X координата по€влени€ корабл€ игрока.
	 */
	public static final int SHIP_INITIALIZE_X = 180;
	/**
	 * Y координата по€влени€ корабл€ игрока.
	 */
	public static final int SHIP_INITIALIZE_Y = 490;
	/**
	 * Ўирина вражеского корабл€.
	 */
	public static final int ENEMY_SHIP_WIDTH = 100;
	/**
	 * ¬ысота вражеского корабл€.
	 */
	public static final int ENEMY_SHIP_HEIGHT = 150;
	/**
	 * X координата по€влени€ вражеского корабл€.
	 */
	public static final int ENEMY_SHIP_INITIALIZE_X = 10;
	/**
	 * Y координата по€влени€ вражеского корабл€.
	 */
	public static final int ENEMY_SHIP_INITIALIZE_Y = 2;
	
	/**
	 * Ўирина пули.
	 */
	public static final int BULLET_WIDTH = 5;
	/**
	 * ¬ысота пули.
	 */
	public static final int BULLET_HEIGHT = 20;
	/**
	 * X координата по€влени€ пули.
	 */
	public static final int BULLET_INITIALIZE_X = 100;
	/**
	 * Y координата по€влени€ пули.
	 */
	public static final int BULLET_INITIALIZE_Y = 100;

	/**
	 * Ўирина игрового окна при создании.
	 */
	public static final int VIEW_INITIALIZE_WIDTH = 410;
	/**
	 * ¬ысота игрового окна при создании.
	 */
	public static final int VIEW_INITIALIZE_HEIGHT = 660;
	/**
	 * Ўирина окна окончани€ игры при создании.
	 */
	public static final int END_GAME_INITIALIZE_WIDTH = 350;
	/**
	 * ¬ысота окна окончани€ игры при создании.
	 */
	public static final int END_GAME_INITIALIZE_HEIGHT = 200;

	/**
	 * Ўирина игрового пол€ при создании.
	 */
	public static final int GAMEFIELD_INITIALIZE_WIDTH = 400;
	/**
	 * ¬ысота игрового пол€ при создании.
	 */
	public static final int GAMEFIELD_INITIALIZE_HEIGHT = 600;
	/**
	 * X координата игрового пол€ при создании.
	 */
	public static final int GAMEFIELD_INITIALIZE_X = 0;
	/**
	 * Y координата игрового пол€ при создании.
	 */
	public static final int GAMEFIELD_INITIALIZE_Y = 0;

	/**
	 * ¬рем€ сна главного игрового потока.
	 */
	public static final long THREAD_SLEEP = 10;
	/**
	 * ћаксимальное количесво пуль выстреливаемых одновременно кораблем
	 */
	public static final int MAX_BULLET_COUNT_PER_SHIP = 2;

	/**
	 * ћаксимальна€ веро€тность уклонени€ вражеского корабл€ от пули игрока.
	 */
	public static final int MAXIMUM_PROBABILITY = 100;
	/**
	 * ¬еро€тность уклонени€ вражеского корабл€ от пули игрока.
	 */
	public static final int DODGE_PROBABILITY = 100;

	/**
	 * —сылка на звук при выстреле корабл€.
	 */
	public static final String SHOOT_SOUND = "/resources/shoot.wav";
	/**
	 * —сылка на звук при проигрыше.
	 */
	public static final String PLAYERDEAD_SOUND = "/resources/playerDead.wav";
	/**
	 * —сылка на звук при выигрыше.
	 */
	public static final String ENEMYDEAD_SOUND = "/resources/enemyDead.wav";
	/**
	 * —сылка на текстуру дл€ вражеского корабл€.
	 */
	public static final String ENEMY_IMAGE = "/resources/enemy.png";
	/**
	 * —сылка на текстуру дл€ корабл€ игрока.
	 */
	public static final String PLAYER_IMAGE = "/resources/ship.png";

	/**
	 *  онструктор дл€ констант.
	 */
	private Constants() {

	}
}
