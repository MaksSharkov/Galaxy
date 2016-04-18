package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class EndGameFrame extends JFrame {

	private static final long serialVersionUID = 6340709615001796726L;

	private final String title = "Game over";
	private JLabel winStatus = new JLabel();

	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int halfScreenWidth = screenSize.width / 2;
	private int halfScreenHeight = screenSize.height / 2;
	private Font font = new Font("Verdana", Font.BOLD, 30);

	public EndGameFrame(boolean win) {
		this.setLayout(new BorderLayout());
		this.setTitle(title);
		this.setVisible(true);
		this.setLocation(halfScreenWidth
				- model.Constants.END_GAME_INITIALIZE_WIDTH / 2,
				halfScreenHeight - model.Constants.END_GAME_INITIALIZE_HEIGHT
						/ 2);
		this.setPreferredSize(new Dimension(
				model.Constants.END_GAME_INITIALIZE_WIDTH,
				model.Constants.END_GAME_INITIALIZE_HEIGHT));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();

		if (win) {
			
			winStatus.setForeground(Color.GREEN);
			winStatus.setText("YOU WIN!");
		}
		if (!win) {
			winStatus.setForeground(Color.RED);
			winStatus.setText("YOU LOSE!");
		}
		winStatus.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		winStatus.setVisible(true);
		winStatus.setFont(font);
		this.getContentPane().add(winStatus, BorderLayout.CENTER);
	}
}
