package controller;

import model.Model;
import model.listeners.Listener;

public class Controller {
	private Model model;

	public Controller(Model model) {
		super();
		this.model = model;
	}

	public void addListener(Listener name) {
		model.addListener(name);
	}

	public void shiftShipLeft() {
		model.shiftShipLeft();
	}

	public void shiftShipRight() {
		model.shiftShipRight();
	}

	public void makeShoot() {
		model.makeShoot();
	}

	public void enemyShoot() {
		model.enemyShoot();
	}
}
