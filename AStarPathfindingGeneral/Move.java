
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Move {
	public static final int BASE_THREE_CONNECTED = 20;
	public static final int BASE_CONNECTED_COST = 6;
	public static final int BASE_SINGLE_CONNECTED = 1;
	public static final int CONNECT_LENGTH = 3;

	private final double PERCENTAGE_MAX_COMBO = .90;

	private int[][] startState;

	private final int MOVEMENT_COST = 1;

	private int MIN_HEURISTIC;

	private int x;
	private int y;

	private final int firstX;
	private final int firstY;

	private String startHashCode;

	private PriorityQueue<PADNode> open;

	private HashMap<String, PADNode> openMap;
	private HashMap<String, PADNode> closed;

	private int nodeTraverseCount = 0;

	public Move(int[][] data, int firstX, int firstY) {

		startState = data;

		this.firstX = firstX;
		this.firstY = firstY;

		x = firstX;
		y = firstY;

		MIN_HEURISTIC = computeMinimumHeuristic(data);

		closed = new HashMap<String, PADNode>();
		openMap = new HashMap<String, PADNode>();

		open = new PriorityQueue<PADNode>(new NodeComparator());

		PADNode start = new PADNode(startState, x, y);
		startHashCode = start.getHashCode();
		open.add(start);
	}

	public int computeMinimumHeuristic(int[][] data) {
		int[] histogram = createHistogram(data);
		int maxCombo = findMaxCombo(histogram);
		return (int) (Math.ceil(maxCombo * PERCENTAGE_MAX_COMBO)) * BASE_THREE_CONNECTED;
	}

	public int findMaxCombo(int[] histogram) {
		int combo = 0;
		for (int i = 0; i < histogram.length; i++)
			combo += histogram[i] / CONNECT_LENGTH;

		return combo;
	}

	public int[] createHistogram(int[][] data) {
		int[] histogram = new int[6];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				int color = data[i][j];
				switch (color) {
				case 0:
					histogram[0]++;
					break;
				case 1:
					histogram[1]++;
					break;
				case 2:
					histogram[2]++;
					break;
				case 3:
					histogram[3]++;
					break;
				case 4:
					histogram[4]++;
					break;
				case 5:
					histogram[5]++;
					break;
				case 6:
					histogram[6]++;
					break;
				}
			}
		}

		return histogram;
	}

	public PADNode findMove() {
		while (!open.isEmpty()) {
			PADNode curr = open.poll();
			closed.put(curr.getHashCode(), curr);

			if (curr.getH() >= MIN_HEURISTIC) 
				return curr;

			int cost = curr.getG() + MOVEMENT_COST;

			x = curr.getX();
			y = curr.getY();

			if (x > 0) { // north
				int[][] temp = cloneArr(curr.getArray());
				replace(temp, x, y, x - 1, y);
				PADNode left = new PADNode(temp, x - 1, y);
				interpretNode(left, curr, cost, x - 1, y);
			}
			if (x < 4) { // south
				int[][] temp = cloneArr(curr.getArray());
				replace(temp, x, y, x + 1, y);
				PADNode left = new PADNode(temp, x + 1, y);
				interpretNode(left, curr, cost, x + 1, y);
			}
			if (y > 0) { // west
				int[][] temp = cloneArr(curr.getArray());
				replace(temp, x, y, x, y - 1);
				PADNode left = new PADNode(temp, x, y - 1);
				interpretNode(left, curr, cost, x, y - 1);
			}
			if (y < 5) { // east
				int[][] temp = cloneArr(curr.getArray());
				replace(temp, x, y, x, y + 1);
				PADNode left = new PADNode(temp, x, y + 1);
				interpretNode(left, curr, cost, x, y + 1);
			}
		}

		return null;
	}

	public void interpretNode(PADNode newNode, PADNode parentNode, int cost, int x, int y) {
		nodeTraverseCount++;

		String hashCode = newNode.getHashCode();

		// if current node is in closed and movement cost of current node is
		// less
		if (closed.get(hashCode) != null && cost < closed.get(hashCode).getG()) {
			closed.remove(hashCode);
			addNode(newNode, parentNode, cost);
			// if current node is in open and movement cost of curr is less
		} else if (openMap.get(hashCode) != null && cost < openMap.get(hashCode).getG()) {
			openMap.get(hashCode).setG(cost);
			openMap.get(hashCode).setParent(parentNode);
			// if current position of array has not been traversed
		} else if (openMap.get(newNode.getHashCode()) == null && closed.get(newNode.getHashCode()) == null) {
			addNode(newNode, parentNode, cost);
		}
	}

	public void addNode(PADNode node, PADNode parent, int g) {
		node.setG(g);
		node.setParent(parent);

		open.add(node);
		openMap.put(node.getHashCode(), node);
	}

	public ArrayList<int[]> traverseNodes(PADNode start) {
		PADNode curr = start;
		ArrayList<int[]> data = new ArrayList<int[]>();

		while (curr.getHashCode() != startHashCode) {
			data.add(curr.getCoordinatesAsArray());
			curr = curr.getParent();
		}
		int[] first = { firstX, firstY };
		data.add(first);

		return data;
	}

	public ArrayList<int[][]> traverseNodeStates(PADNode start) {
		PADNode curr = start;
		ArrayList<int[][]> data = new ArrayList<int[][]>();

		while (curr.getHashCode() != startHashCode) {
			data.add(curr.getArray());
			curr = curr.getParent();
		}
		data.add(curr.getArray());

		return data;
	}

	// comparator for priority queue - sorts in reverse order
	public static class NodeComparator implements Comparator<PADNode> {
		public int compare(PADNode node1, PADNode node2) {
			return node2.getF() - node1.getF();
		}
	}

	public int getTraversalCount(){
		return nodeTraverseCount;
	}
	
	// utility methods

	public void replace(int[][] arr, int i1, int i2, int j1, int j2) {
		int temp = arr[i1][i2];
		arr[i1][i2] = arr[j1][j2];
		arr[j1][j2] = temp;
	}

	public int[][] cloneArr(int[][] arr) {
		int[][] newArr = new int[arr.length][arr[0].length];

		for (int i = 0; i < arr.length; i++)
			newArr[i] = arr[i].clone();

		return newArr;
	}
}
