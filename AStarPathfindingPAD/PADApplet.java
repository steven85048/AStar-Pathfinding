package spring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import javax.swing.JApplet;

public class PADApplet extends JApplet {
	public static int APPLET_WIDTH = 1500, APPLET_HEIGHT = 1500;

	int[][] start;
	char[] directions;
	ArrayList<int[][]> boards;

	// String input = "HGHRBLLGBGRBDDHLBHBLDHHLHLLGBG";
	String input;

	public void init() {
		input = randomizeBoard();
		start = new int[Move.GRID_HEIGHT][Move.GRID_WIDTH];

		parseInput(start, input);

		Move move = new Move(start, Move.startArr[0], Move.startArr[1]);
		PADNode node = move.findMove();

		boards = move.traverseNodeStates(node);
		Collections.reverse(boards);

		System.out.println(Arrays.deepToString(node.getArray()));
		System.out.println("TRAVERSE COUNT: " + move.getTraversalCount());
		System.out.println("MOVE COUNT: " + boards.size());

		setSize(APPLET_WIDTH, APPLET_HEIGHT);

		try {
			getContentPane().add(new PADPanel(start, boards));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String randomizeBoard() {
		Random rand = new Random();
		String str = "";

		for (int i = 0; i < Move.GRID_HEIGHT * Move.GRID_WIDTH; i++) {
			int randomNum = rand.nextInt(66);
			if (randomNum < 11)
				str += "H";
			else if (randomNum < 22)
				str += "R";
			else if (randomNum < 33)
				str += "G";
			else if (randomNum < 44)
				str += "B";
			else if (randomNum < 55)
				str += "L";
			else if (randomNum < 66)
				str += "D";
		}

		return str;
	}

	public void parseInput(int[][] data, String input) {
		int index = 0;
		input.toUpperCase();

		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				char curr = input.charAt(index);

				switch (curr) {
				case 'H':
					data[i][j] = 0;
					break;
				case 'R':
					data[i][j] = 1;
					break;
				case 'G':
					data[i][j] = 2;
					break;
				case 'B':
					data[i][j] = 3;
					break;
				case 'L':
					data[i][j] = 4;
					break;
				case 'D':
					data[i][j] = 5;
					break;
				}

				index++;
			}
		}
	}

	public static char[] getDirections(ArrayList<int[]> data) {
		char[] directions = new char[data.size()];
		for (int i = 0; i < data.size() - 1; i++) {
			int[] first = data.get(i);
			int[] second = data.get(i + 1);

			int differenceX = first[0] - second[0];
			int differenceY = first[1] - second[1];

			if (differenceX == -1)
				directions[i] = 'S';
			else if (differenceX == 1)
				directions[i] = 'N';
			else if (differenceY == -1)
				directions[i] = 'E';
			else if (differenceY == 1)
				directions[i] = 'W';
		}

		return directions;
	}
}
