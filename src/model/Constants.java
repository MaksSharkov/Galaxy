package model;

public class Constants {
	public static final int PLAYER_MOVE_STEP = 10;
	public static final int ENEMY_MOVE_STEP = 2;
	public static final int BULLET_MOVE_STEP = 10;

	public static final int SHIP_WIDTH = 70;
	public static final int SHIP_HEIGHT = 100;
	public static final int SHIP_INITIALIZE_X = 180;
	public static final int SHIP_INITIALIZE_Y = 490;

	public static final int ENEMY_SHIP_WIDTH = 100;
	public static final int ENEMY_SHIP_HEIGHT = 150;
	public static final int ENEMY_SHIP_INITIALIZE_X = 10;
	public static final int ENEMY_SHIP_INITIALIZE_Y = 2;

	public static final int BULLET_WIDTH = 5;
	public static final int BULLET_HEIGHT = 20;
	public static final int BULLET_INITIALIZE_X = 100;
	public static final int BULLET_INITIALIZE_Y = 100;

	public static final int VIEW_INITIALIZE_WIDTH = 410;
	public static final int VIEW_INITIALIZE_HEIGHT = 660;

	public static final int GAMEFIELD_INITIALIZE_WIDTH = 400;
	public static final int GAMEFIELD_INITIALIZE_HEIGHT = 600;
	public static final int GAMEFIELD_INITIALIZE_X = 0;
	public static final int GAMEFIELD_INITIALIZE_Y = 0;

	public static final long THREAD_SLEEP = 10;
	public static final int MAX_BULLET_COUNT_PER_SHIP = 2;

	public static final int MAXIMUM_PROBABILITY = 100;
	public static final int DODGE_PROBABILITY = 100;

	public static final String SHOOT_SOUND = "/resources/shoot.wav";
	public static final String PLAYERDEAD_SOUND = "/resources/playerDead.wav";
	public static final String ENEMYDEAD_SOUND = "/resources/enemyDead.wav";

	private Constants() {

	}
}
