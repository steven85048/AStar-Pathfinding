
import java.util.Arrays;

public class PADNode {

	private final int BASE_DIAGONAL_V = 6;
	private final int BASE_BACK_COST = 4;

	private int f;
	private int g;
	private int h;

	private int x;
	private int y;

	private int[][] data;
	private String hashCode;

	PADNode parent;

	public PADNode(int[][] data, int x, int y) {
		this.x = x;
		this.y = y;

		this.data = data;
		hashCode = computeHashCode();

		g = 0;
		h = heuristic();
	}

	// Computes total heuristic value for board (prioritizes combos)
	public int heuristic() {
		int[][] arr = cloneArr(data);

		int heuristic = 0;
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				if (arr[i][j] == (-1))
					continue;
				heuristic += expand(arr, i, j);
			}
		}

		return heuristic;
	}

	// Computes total heuristic cost for ** connected group only **
	public int expand(int[][] arr, int i, int j) {
		int currColor = arr[i][j];

		int x = i;

		int nodeSum = 0;
		int horizontalSum = 0;

		boolean threeConnected = false;

		// while node extension in x direction corresponds with starting color
		while (x < arr.length && arr[x][j] == currColor) {
			horizontalSum++;
			arr[x][j] = -1;

			int verticalSum = 0;

			int y = j + 1;
			// extend nodes in y positive direction
			while (y < arr[0].length && arr[x][y] == currColor) {
				verticalSum++;
				arr[x][y] = -1;
				y++;
			}

			// extend nodes in y negative direction
			y = j - 1;
			while (y >= 0 && arr[x][y] == currColor) {
				verticalSum++;
				arr[x][y] = -1;
				y--;
			}

			if ((verticalSum + 1) >= Move.CONNECT_LENGTH)
				threeConnected = true;

			// add traversed y direction nodes to nodeSum + middle node
			nodeSum += verticalSum + 1;

			x++;
		}

		if (horizontalSum >= Move.CONNECT_LENGTH)
			threeConnected = true;

		// do nothing for single orbs
		if (nodeSum == 1)
			return 0;

		// Compute total heuristic cost for connected group
		return computeConnectedCost(nodeSum, threeConnected);
	}

	// computes hashCode appending x and y onto end of array
	public String computeHashCode() {
		String key = "";
		
		int[][] arr = new int[data.length + 1][data[0].length];

		for (int i = 0; i < data.length; i++)
			for (int j = 0; j < data[0].length; j++)
				key += data[i][j];
	
		key += x;
		key += y;
		
		return key;
	}

	// Computes total cost of connected cluster
	public int computeConnectedCost(int nodeSum, boolean threeConnected) {
		if (threeConnected)
			return Move.BASE_THREE_CONNECTED + (nodeSum - Move.CONNECT_LENGTH) * Move.BASE_SINGLE_CONNECTED;
		else
			return Move.BASE_CONNECTED_COST + (nodeSum - 2) * Move.BASE_SINGLE_CONNECTED;
	}

	// setter methods

	public void setG(int g) {
		this.g = g;
	}

	public void setParent(PADNode parent) {
		this.parent = parent;
	}

	// getter methods
	public int getF() {
		return h - g;
	}

	public int getG() {
		return g;
	}

	public int getH() {
		return h;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int[][] getArray() {
		return data;
	}

	public int[] getCoordinatesAsArray() {
		int[] arr = new int[2];
		arr[0] = x;
		arr[1] = y;

		return arr;
	}

	public PADNode getParent() {
		return parent;
	}

	public String getHashCode() {
		return hashCode;
	}

	// utility methods

	public int[][] cloneArr(int[][] arr) {
		int[][] newArr = new int[arr.length][arr[0].length];

		for (int i = 0; i < arr.length; i++)
			newArr[i] = arr[i].clone();

		return newArr;
	}

}
