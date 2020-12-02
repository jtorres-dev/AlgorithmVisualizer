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

	public int getX() { return x; }

	public int getY() { return y; }

	public int cost() { return cost; }

	public int heuristic() { return heuristic; }
	
	public int f() { return cost + heuristic; }

	public Node previous() { return prev; }

	public boolean isWall() { return isWall; }



	public void setHeuristic(int heuristic) { this.heuristic = heuristic; }

	public void setCost(int cost) { this.cost = cost; }
	
	public void setPrevious(Node prev) { this.prev = prev; }
	
	public void setWall() { isWall = true; }



	public boolean sameLocation(Node other) { return getX() == other.getX() && getY() == other.getY(); }

	@Override
	public String toString() { return "(" + x + ", " + y + ")"; }
}
