public class AStarNode {
	private int g;
	private int h;
	private int f;

	private int i;
	private int j;

	private AStarNode parent;

	public AStarNode(int i, int j) {
		this.i = i;
		this.j = j;

		this.g = 0;
		h = heuristic();
	}

	// computes heuristic based on the manhattan distance; scaled as per distance
	public int heuristic() {
		return 10 * (Math.abs(i - AStarPathfinding.end[0]) + Math.abs(j - AStarPathfinding.end[1]));
	}

	public String toString(){
		return (i + "    " + j);
	}
	
	// setter methods

	public void setParent(AStarNode parent) {
		this.parent = parent;
	}

	public void setG(int g){
		this.g= g;
	}
	

	// accessor methods
	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public int getF() {
		return g + h;
	}

	public int getG() {
		return g;
	}

	public AStarNode getParent() {
		return parent;
	}
}