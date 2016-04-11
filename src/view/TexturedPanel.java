package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class TexturedPanel extends JPanel {
	private static final long serialVersionUID = 846020334670944592L;

	private BufferedImage image;

	public TexturedPanel(String path) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(path));
		} catch (IOException ex) {

		}
	}

	@Override
	protected void paintComponent(Graphics source) {
		super.paintComponent(source);
		source.drawImage(image, 0, 0, null);
	}
}
