package runner;

import java.util.Locale;

import model.Model;
import view.View;
import controller.Controller;

public class Runner {

	private Runner() {

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Model model = new Model();
		Controller controller = new Controller(model);
		View view = new View(controller);

		model.startGame();
	}

}
