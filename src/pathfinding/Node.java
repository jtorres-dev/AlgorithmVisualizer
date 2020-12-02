package pathfinding;

public class Node {

	private int x, y, cost, heuristic;
	private boolean isWall;
	private Node prev;

	public Node(int x, int y) {
		this.x = x;
		this.y = y;
		cost = 0;
		isWall = false;
		prev = null;
	}

	int getX() { return x; }

	int getY() { return y; }

	int cost() { return cost; }

	int heuristic() { return heuristic; }
	
	int f() { return cost + heuristic; }

	Node previous() { return prev; }

	boolean isWall() { return isWall; }



	void setHeuristic(int heuristic) { this.heuristic = heuristic; }

	void setCost(int cost) { this.cost = cost; }
	
	void setPrevious(Node prev) { this.prev = prev; }
	
	void setWall() { isWall = true; }



	boolean sameLocation(Node other) { return getX() == other.getX() && getY() == other.getY(); }

	@Override
	public String toString() { return "(" + x + ", " + y + ")"; }
}
