package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
/**
 * Класс текстурированной панели.
 * @author Maks_Sh
 *
 */
public class TexturedPanel extends JPanel {
	private static final long serialVersionUID = 846020334670944592L;

	/**
	 * Буферизированное изображение (текстура).
	 */
	private BufferedImage image;

	/**
	 * Конструктор текстурированной панели.
	 * @param path путь к текстуре.
	 */
	public TexturedPanel(String path) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(path));
		} catch (IOException ex) {

		}
	}

	/**
	 * Метод наносит текстуру на панель.
	 */
	@Override
	protected void paintComponent(Graphics source) {
		super.paintComponent(source);
		source.drawImage(image, 0, 0, null);
	}
}
