
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class FindPath {
	private int[][] data;

	private int[] start;
	private int[] end;

	private ArrayList<AStarNode> closed;
	private PriorityQueue<AStarNode> open;

	AStarNode[][] nodeMap;

	public FindPath(int[][] data) {
		this.data = data;

		start = AStarPathfinding.start;
		end = AStarPathfinding.end;

		// initialize nodeMap
		createNodeMap();

		// initialize open and closed sets:

		// closed set is empty
		closed = new ArrayList<AStarNode>();

		// add first node to open set
		open = new PriorityQueue<AStarNode>(new NodeComparator());
		open.add(nodeMap[start[0]][start[1]]);
	}

	// returns (hopefully) the goal node
	public AStarNode getPath() {
		// while the current node is not the goal
		while (!open.isEmpty()) {
			AStarNode curr = open.poll();
			closed.add(curr);

			if (curr.getI() == end[0] && curr.getJ() == end[1])
				return curr;

			int currI = curr.getI();
			int currJ = curr.getJ();

			// examine neighbor nodes of curr

			int diagonalCost = curr.getG() + 14;
			int straightCost = curr.getG() + 10;

			// SW NODE
			if (currI != 0 && currJ != 0 && data[currI - 1][currJ - 1] != (-1)) {
				AStarNode sw = nodeMap[currI - 1][currJ - 1];
				interpretNode(sw, diagonalCost, curr);
			}
			// NE NODE
			if (currI != data.length - 1 && currJ != data[0].length - 1 && data[currI + 1][currJ + 1] != (-1)) {
				AStarNode sw = nodeMap[currI + 1][currJ + 1];
				interpretNode(sw, diagonalCost, curr);
			}
			// SE NODE
			if (currI != data.length - 1 && currJ != 0 && data[currI + 1][currJ - 1] != (-1)) {
				AStarNode sw = nodeMap[currI + 1][currJ - 1];
				interpretNode(sw, diagonalCost, curr);
			}
			// NW NODE
			if (currI != 0 && currJ != data[0].length - 1 && data[currI - 1][currJ + 1] != (-1)) {
				AStarNode sw = nodeMap[currI - 1][currJ + 1];
				interpretNode(sw, diagonalCost, curr);
			}
			// S NODE
			if (currJ != 0 && data[currI][currJ - 1] != (-1)) {
				AStarNode sw = nodeMap[currI][currJ - 1];
				interpretNode(sw, straightCost, curr);
			}
			// W NODE
			if (currI != 0 && data[currI - 1][currJ] != (-1)) {
				AStarNode sw = nodeMap[currI - 1][currJ];
				interpretNode(sw, straightCost, curr);
			}
			// E NODE
			if (currI != data.length - 1 && data[currI + 1][currJ] != (-1)) {
				AStarNode sw = nodeMap[currI + 1][currJ];
				interpretNode(sw, straightCost, curr);
			}
			// N NODE
			if (currJ != data[0].length - 1 && data[currI][currJ + 1] != (-1)) {
				AStarNode sw = nodeMap[currI][currJ + 1];
				interpretNode(sw, straightCost, curr);
			}
		}

		return null;
	}

	// traverses node path from the end to start
	public ArrayList<AStarNode> traversePath(AStarNode start) {
		ArrayList<AStarNode> nodes = new ArrayList<AStarNode>();
		nodes.add(start);

		AStarNode curr = start;
		while (curr.getParent() != null) {
			curr = curr.getParent();
			nodes.add(curr);
		}

		return nodes;
	}

	public void interpretNode(AStarNode node, int cost, AStarNode parent) {

		// if node is in open set and cost < g(node)
		if (open.contains(node) && cost < node.getG()) {
			open.remove(node);
		}
		// if node in closed set and cost < g(node)
		else if (closed.contains(node) && cost < node.getG()) {
			 closed.remove(node);
			 open.add(node);
			 node.setParent(parent);
		}
		// if node is not in either set
		else if (!(open.contains(node) || closed.contains(node))) {
			node.setG(cost);
			open.add(node);
			node.setParent(parent);
		}
	}

	// initializes nodeMap
	public void createNodeMap() {
		int a = data.length;
		int b = data[0].length;

		nodeMap = new AStarNode[a][b];

		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				nodeMap[i][j] = new AStarNode(i, j);
			}
		}
	}

	// comparator for priority queue - sorts in reverse order
	public static class NodeComparator implements Comparator<AStarNode> {
		public int compare(AStarNode node1, AStarNode node2) {
			return (node1.getF() - node2.getF());
		}
	}
}
