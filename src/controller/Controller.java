package controller;

import model.Model;
import model.listeners.Listener;
/**
 * Обеспечивает связь между пользователем и системой: контролирует ввод данных пользователем и использует модель и представление для реализации необходимой реакции.
 * @author Maks_Sh
 *
 */
public class Controller {
	/**
	 * Объект модель.
	 */
	private Model model;

	/**
	 * Конструктор контроллера
	 * @param model объект модель
	 */
	public Controller(Model model) {
		super();
		this.model = model;
	}

	/**
	 * Метод добавляет нового слушателя в список слушателей.
	 * @param name имя нового слушателя.
	 */
	public void addListener(Listener name) {
		model.addListener(name);
	}

	/**
	 * Метод вызывает у модели метод смещения кораблика игрока влево.
	 */
	public void shiftShipLeft() {
		model.shiftShipLeft();
	}

	/**
	 * Метод вызывает у модели метод смещения кораблика игрока вправо.
	 */
	public void shiftShipRight() {
		model.shiftShipRight();
	}

	/**
	 * Метод вызывает у модели метод выстрела кораблика игрока.
	 */
	public void makeShoot() {
		model.makeShoot();
	}

	/**
	 * Метод вызывает у модели метод выстрела кораблика врага.
	 */
	public void enemyShoot() {
		model.enemyShoot();
	}
}
