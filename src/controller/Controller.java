package controller;

import model.Model;
import model.listeners.Listener;
/**
 * ������������ ����� ����� ������������� � ��������: ������������ ���� ������ ������������� � ���������� ������ � ������������� ��� ���������� ����������� �������.
 * @author Maks_Sh
 *
 */
public class Controller {
	/**
	 * ������ ������.
	 */
	private Model model;

	/**
	 * ����������� �����������
	 * @param model ������ ������
	 */
	public Controller(Model model) {
		super();
		this.model = model;
	}

	/**
	 * ����� ��������� ������ ��������� � ������ ����������.
	 * @param name ��� ������ ���������.
	 */
	public void addListener(Listener name) {
		model.addListener(name);
	}

	/**
	 * ����� �������� � ������ ����� �������� ��������� ������ �����.
	 */
	public void shiftShipLeft() {
		model.shiftShipLeft();
	}

	/**
	 * ����� �������� � ������ ����� �������� ��������� ������ ������.
	 */
	public void shiftShipRight() {
		model.shiftShipRight();
	}

	/**
	 * ����� �������� � ������ ����� �������� ��������� ������.
	 */
	public void makeShoot() {
		model.makeShoot();
	}

	/**
	 * ����� �������� � ������ ����� �������� ��������� �����.
	 */
	public void enemyShoot() {
		model.enemyShoot();
	}
}
