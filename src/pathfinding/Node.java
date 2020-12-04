package pathfinding;

public class Node {

	private int x, y, cost, heuristic;
	private boolean isWall;
	private boolean isDiagonal;
	private Node prev;

	public Node(int x, int y) {
		this.x = x;
		this.y = y;
		cost = 0;
		isWall = false;
		isDiagonal = false;
		prev = null;
	}

	int getX() { return x; }

	int getY() { return y; }

	int cost() { return cost; }

	int heuristic() { return heuristic; }

	int f() { return cost + heuristic; }

	Node previous() { return prev; }

	boolean isWall() { return isWall; }

	boolean isDiagonal() { return isDiagonal; }


	void setHeuristic(int heuristic) { this.heuristic = heuristic; }

	void setCost(int cost) { this.cost = cost; }

	void setPrevious(Node prev) { this.prev = prev; }

	void setWall(boolean isWall) { this.isWall = isWall; }

	void setDiagonal(boolean isDiagonal) { this.isDiagonal = isDiagonal; }


	boolean sameLocation(Node other) { return getX() == other.getX() && getY() == other.getY(); }

	@Override
	public String toString() { return "(" + x + ", " + y + ")"; }
}
