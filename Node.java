import java.util.ArrayList;

public class Node implements Comparable {

	private int x, y, cost, heuristic;
	private Node prev;
	private boolean solution; // if cell is part of the solution path

	private ArrayList<Node> neighbors;

	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() { return x; }

	public int getY() { return y; }

	public int cost() { return cost; }

	public int heuristic() { return heuristic; }

	public Node previous() { return prev; }

	public boolean solution() { return solution; }
	
	public ArrayList<Node> neighbors() { return neighbors; }

	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}

	public void setPrevious(Node prev) {
		this.prev = prev;
	}

	public void isInPath(boolean solution) {
		this.solution = solution;
	}

	public void setNeighbors(ArrayList<Node> neighbors) {
		this.neighbors = neighbors;
	}

    @Override
    public int compareTo(Object o) {
    	Node node = (Node) o;
    	return (cost + heuristic) - (node.cost() + node.heuristic());
    }

    @Override
    public String toString() {
		return "[ " + x + ", " + y + "]";
    }

	public boolean isEqual(Node other) {
		return getX() == other.getX() && getY() == other.getY();
	}

}
