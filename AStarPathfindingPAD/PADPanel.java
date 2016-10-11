package spring;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PADPanel extends JPanel {

	private String BASE_DIR = "C://Users//steve//workspace//spring//pics//";

	int[][] start;
	ArrayList<int[][]> boards;

	JPanel topPanel;
	JPanel totalPanel;
	JLabel[][] labels;

	JButton next;

	BufferedImage heart;
	BufferedImage red;
	BufferedImage green;
	BufferedImage blue;
	BufferedImage dark;
	BufferedImage light;

	int index;

	public PADPanel(int[][] start, ArrayList<int[][]> boards) throws Exception {
		this.start = start;
		this.boards = boards;

		index = 0;

		next = new JButton("NEXT MOVE");
		next.addActionListener(new ButtonListener());

		topPanel = new JPanel(new GridLayout(Move.GRID_HEIGHT, Move.GRID_WIDTH));

		initializeImages();

		labels = new JLabel[Move.GRID_HEIGHT][Move.GRID_WIDTH];
		initializeLabels();

		initializeBoard();

		totalPanel = new JPanel(new BorderLayout());
		totalPanel.add(topPanel, BorderLayout.CENTER);
		totalPanel.add(next, BorderLayout.SOUTH);

		totalPanel.setPreferredSize(new Dimension(PADApplet.APPLET_HEIGHT, PADApplet.APPLET_WIDTH - 50));
		add(totalPanel);
	}

	public void initializeBoard() {
		for (int i = 0; i < Move.GRID_HEIGHT; i++) {
			for (int j = 0; j < Move.GRID_WIDTH; j++) {
				int curr = start[i][j];
				JLabel currLabel = labels[i][j];

				switch (curr) {
				case 0:
					currLabel.setIcon(new ImageIcon(heart));
					break;
				case 1:
					currLabel.setIcon(new ImageIcon(red));
					break;
				case 2:
					currLabel.setIcon(new ImageIcon(green));
					break;
				case 3:
					currLabel.setIcon(new ImageIcon(blue));
					break;
				case 4:
					currLabel.setIcon(new ImageIcon(light));
					break;
				case 5:
					currLabel.setIcon(new ImageIcon(dark));
					break;
				}
			}
		}
	}

	public void initializeImages() throws Exception {
		heart = ImageIO.read(new File(BASE_DIR + "0.png"));
		red = ImageIO.read(new File(BASE_DIR + "1.png"));
		green = ImageIO.read(new File(BASE_DIR + "2.png"));
		blue = ImageIO.read(new File(BASE_DIR + "3.png"));
		light = ImageIO.read(new File(BASE_DIR + "4.png"));
		dark = ImageIO.read(new File(BASE_DIR + "5.png"));

	}

	public void initializeLabels() throws Exception {
		for (int i = 0; i < Move.GRID_HEIGHT; i++) {
			for (int j = 0; j < Move.GRID_WIDTH; j++) {
				JLabel newLabel = new JLabel();
				topPanel.add(newLabel);
				labels[i][j] = newLabel;
			}
		}
	}

	// Button Listener
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (index >= boards.size())
				return;

			int[][] board = boards.get(index);
			start = board;

			initializeBoard();
			index++;
		}
	}

	public void replace(int[][] arr, int i1, int i2, int j1, int j2) {
		int temp = arr[i1][i2];
		arr[i1][i2] = arr[j1][j2];
		arr[j1][j2] = temp;
	}

}
