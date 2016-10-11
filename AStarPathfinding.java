
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

public class AStarPathfinding {
	static int[] start = { 1, 1 };
	static int[] end = { 150, 150 };

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] data = generateData(200, 200);
		FindPath find = new FindPath(data);
		AStarNode end = find.getPath();
		if (end == null)
			System.out.println("FAIL");
		else {
			ArrayList<AStarNode> nodes = find.traversePath(end);
			Collections.reverse(nodes);
			
			System.out.println("RESULT");
			for (int i = 0; i < nodes.size(); i++)
				data[nodes.get(i).getI()][nodes.get(i).getJ()] = (i + 1);

			printData(data);
		}
	}

	public static void printData(int[][] data) {
		int width = data.length;
		int height = data[0].length;

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				System.out.print(data[i][j] + "\t");
			}
			System.out.println();
		}
	}

	public static int[][] generateData(int width, int height) {
		int[][] data = new int[width][height];
		Random rand = new Random();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int num = rand.nextInt(100);
				if (num <= 20)
					data[i][j] = (-1);
				else
					data[i][j] = 0;
			}
		}

		return data;
	}
}
