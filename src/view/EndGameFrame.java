package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class EndGameFrame extends JFrame {

	private final String title = "Game over";

	private JLabel winStatus = new JLabel();

	public EndGameFrame(boolean win) {
		this.setLayout(new BorderLayout());
		this.setTitle(title);
		this.setVisible(true);
		this.setPreferredSize(new Dimension(350, 200));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();

		if (win) {
			winStatus.setText("YOU WIN!");
		}
		if (!win) {
			winStatus.setText("YOU LOSE!");
		}
		winStatus.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		winStatus.setVisible(true);
		this.getContentPane().add(winStatus, BorderLayout.CENTER);
	}
}
