package runner;

import model.Model;
import view.View;
import controller.Controller;

public class Runner {

	private Runner() {

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Model model = new Model();
		Controller controller = new Controller(model);
		View view = new View(controller);

		model.startGame();
	}

}
