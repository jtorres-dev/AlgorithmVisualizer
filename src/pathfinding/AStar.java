package pathfinding;

import java.util.*;


public class AStar {

	private Node[][] grid;
	private Node start, end;

	private List<Node> open;
	private Set<Node> closed;

	// sqrt(2) * 10
	private final int COST = 10;
	private final int DIAGONAL_COST = 14;


	public AStar(int gridSize, Node start, Node end, int[][] walls) {
		createGrid(gridSize);
		this.start = start;
		this.end = end;
		setWallNodes(walls);
		calculateHeuristics(end);

		open = new ArrayList<>();
		closed = new HashSet<>();
	}

	/*	
	    createGrid(6):

		    x-axis
    		    1   2   3   4   5   6  
   y-axis     +-----------------------+
			1 |   |   |   |   |   |   |
			  |---+---+---+---+---+---|
			2 |   |   |   |   |   |   |
			  |---+---+---+---+---+---|
			3 |   |   |   |   |   |   |
			  |---+---+---+---+---+---|
			4 |   |   |   |   |   |   |
			  |---+---+---+---+---+---|
			5 |   |   |   |   |   |   |
			  |---+---+---+---+---+---|
			6 |   |   |   |   |   |   |
			  +-----------------------+

	*/
	private void createGrid(int size) {
		grid = new Node[size + 1][size + 1];
		for(int i = 1; i < grid.length; i++) {
			for(int j = 1; j < grid[0].length; j++) {
				grid[i][j] = new Node(i, j);
			}
		}
	}

	private void setWallNodes(int[][] coords) {
		for(int[] coord : coords)
			grid[coord[0]][coord[1]].setWall();
	}

	// Manhattan format
	private void calculateHeuristics(Node end) {
		for(int i = 1; i < grid.length; i++) {
			for(int j = 1; j < grid[0].length; j++) {

				if(grid[i][j].isWall())
					continue;

				int dx = 0;
				int dy = 0;

				if(i < end.getX())
					dx = end.getX() - i;
				
				else if(i > end.getX())
					dx = i - end.getX();

				if(j < end.getY())
					dy = end.getY() - j;

				else if(j > end.getY())
					dy = j - end.getY();
				
				grid[i][j].setHeuristic(dx + dy);
//				System.out.print(grid[i][j] + "-> h:" + grid[i][j].heuristic() + ", ");
			}
//			System.out.println();
		}
	}

	/* 
		I'll assume there is a more elegant way of doing this instead of hand declaring all the edge cases.
		TODO:
		 - Improvements: maybe a double for loop? recursion? ...	
	*/
	private Node findBestNeighbor(Node curr) {
		int rLen = grid.length;
		int cLen = grid[0].length;

		closed.add(curr);

		// center
		if(curr.getX() > 1 && curr.getX() < rLen - 1 &&
		   curr.getY() > 1 && curr.getY() < cLen - 1) {
			addNeighbor(curr, grid[curr.getX() + 1][curr.getY()], false); // right
			// check if neighbor is the end node
			if(grid[curr.getX() + 1][curr.getY()].sameLocation(end))
				return grid[curr.getX() + 1][curr.getY()];

			addNeighbor(curr, grid[curr.getX() - 1][curr.getY()], false); // left
			// check if neighbor is the end node
			if(grid[curr.getX() - 1][curr.getY()].sameLocation(end))
				return grid[curr.getX() - 1][curr.getY()];

			addNeighbor(curr, grid[curr.getX()][curr.getY() + 1], false); // down
			// check if neighbor is the end node
			if(grid[curr.getX()][curr.getY() + 1].sameLocation(end))
				return grid[curr.getX()][curr.getY() + 1];

			addNeighbor(curr, grid[curr.getX()][curr.getY() - 1], false); // up
			// check if neighbor is the end node
			if(grid[curr.getX()][curr.getY() - 1].sameLocation(end))
				return grid[curr.getX()][curr.getY() - 1];

			addNeighbor(curr, grid[curr.getX() + 1][curr.getY() + 1], true); // bot right
			// check if neighbor is the end node
			if(grid[curr.getX() + 1][curr.getY() + 1].sameLocation(end))
				return grid[curr.getX() + 1][curr.getY() + 1];

			addNeighbor(curr, grid[curr.getX() + 1][curr.getY() - 1], true); // upper right
			// check if neighbor is the end node
			if(grid[curr.getX() + 1][curr.getY() - 1].sameLocation(end))
				return grid[curr.getX() + 1][curr.getY() - 1];

			addNeighbor(curr, grid[curr.getX() - 1][curr.getY() + 1], true); // bot left
			// check if neighbor is the end node
			if(grid[curr.getX() - 1][curr.getY() + 1].sameLocation(end))
				return grid[curr.getX() - 1][curr.getY() + 1];

			addNeighbor(curr, grid[curr.getX() - 1][curr.getY() - 1], true); // upper left
			// check if neighbor is the end node
			if(grid[curr.getX() - 1][curr.getY() - 1].sameLocation(end))
				return grid[curr.getX() - 1][curr.getY() - 1];
		} 

		// left edge
		else if(curr.getX() == 1 && curr.getY() > 1 && curr.getY() < cLen - 1) {
			addNeighbor(curr, grid[curr.getX() + 1][curr.getY()], false); // right
			// check if neighbor is the end node
			if(grid[curr.getX() + 1][curr.getY()].sameLocation(end))
				return grid[curr.getX() + 1][curr.getY()];

			addNeighbor(curr, grid[curr.getX()][curr.getY() + 1], false); // down
			// check if neighbor is the end node
			if(grid[curr.getX()][curr.getY() + 1].sameLocation(end))
				return grid[curr.getX()][curr.getY() + 1];

			addNeighbor(curr, grid[curr.getX()][curr.getY() - 1], false); // up
			// check if neighbor is the end node
			if(grid[curr.getX()][curr.getY() - 1].sameLocation(end))
				return grid[curr.getX()][curr.getY() - 1];

			addNeighbor(curr, grid[curr.getX() + 1][curr.getY() + 1], true); // bot right
			// check if neighbor is the end node
			if(grid[curr.getX() + 1][curr.getY() + 1].sameLocation(end))
				return grid[curr.getX() + 1][curr.getY() + 1];

			addNeighbor(curr, grid[curr.getX() + 1][curr.getY() - 1], true); // upper right
			// check if neighbor is the end node
			if(grid[curr.getX() + 1][curr.getY() - 1].sameLocation(end))
				return grid[curr.getX() + 1][curr.getY() - 1];
		}

		// top edge
		else if(curr.getY() == 1 && curr.getX() > 1 && curr.getX() < rLen - 1) { 
			addNeighbor(curr, grid[curr.getX() + 1][curr.getY()], false); // right
			// check if neighbor is the end node
			if(grid[curr.getX() + 1][curr.getY()].sameLocation(end))
				return grid[curr.getX() + 1][curr.getY()];

			addNeighbor(curr, grid[curr.getX() - 1][curr.getY()], false); // left
			// check if neighbor is the end node
			if(grid[curr.getX() - 1][curr.getY()].sameLocation(end))
				return grid[curr.getX() - 1][curr.getY()];

			addNeighbor(curr, grid[curr.getX()][curr.getY() + 1], false); // down
			// check if neighbor is the end node
			if(grid[curr.getX()][curr.getY() + 1].sameLocation(end))
				return grid[curr.getX()][curr.getY() + 1];

			addNeighbor(curr, grid[curr.getX() + 1][curr.getY() + 1], true); // bot right
			// check if neighbor is the end node
			if(grid[curr.getX() + 1][curr.getY() + 1].sameLocation(end))
				return grid[curr.getX() + 1][curr.getY() + 1];

			addNeighbor(curr, grid[curr.getX() - 1][curr.getY() + 1], true); // bot left
			// check if neighbor is the end node
			if(grid[curr.getX() - 1][curr.getY() + 1].sameLocation(end))
				return grid[curr.getX() - 1][curr.getY() + 1];
		}

		// right edge
		else if(curr.getX() == rLen - 1 && curr.getY() > 1 && curr.getY() < cLen - 1) {
			addNeighbor(curr, grid[curr.getX() - 1][curr.getY()], false); // left
			// check if neighbor is the end node
			if(grid[curr.getX() - 1][curr.getY()].sameLocation(end))
				return grid[curr.getX() - 1][curr.getY()];

			addNeighbor(curr, grid[curr.getX()][curr.getY() + 1], false); // down
			// check if neighbor is the end node
			if(grid[curr.getX()][curr.getY() + 1].sameLocation(end))
				return grid[curr.getX()][curr.getY() + 1];

			addNeighbor(curr, grid[curr.getX()][curr.getY() - 1], false); // up
			// check if neighbor is the end node
			if(grid[curr.getX()][curr.getY() - 1].sameLocation(end))
				return grid[curr.getX()][curr.getY() - 1];

			addNeighbor(curr, grid[curr.getX() - 1][curr.getY() + 1], true); // bot left
			// check if neighbor is the end node
			if(grid[curr.getX() - 1][curr.getY() + 1].sameLocation(end))
				return grid[curr.getX() - 1][curr.getY() + 1];

			addNeighbor(curr, grid[curr.getX() - 1][curr.getY() - 1], true); // upper left
			// check if neighbor is the end node
			if(grid[curr.getX() - 1][curr.getY() - 1].sameLocation(end))
				return grid[curr.getX() - 1][curr.getY() - 1];
		}

		// bottom edge
		else if(curr.getY() == cLen - 1 && curr.getX() > 1 && curr.getX() < rLen - 1) {
			addNeighbor(curr, grid[curr.getX() + 1][curr.getY()], false); // right
			// check if neighbor is the end node
			if(grid[curr.getX() + 1][curr.getY()].sameLocation(end))
				return grid[curr.getX() + 1][curr.getY()];

			addNeighbor(curr, grid[curr.getX() - 1][curr.getY()], false); // left
			// check if neighbor is the end node
			if(grid[curr.getX() - 1][curr.getY()].sameLocation(end))
				return grid[curr.getX() - 1][curr.getY()];

			addNeighbor(curr, grid[curr.getX()][curr.getY() - 1], false); // up
			// check if neighbor is the end node
			if(grid[curr.getX()][curr.getY() - 1].sameLocation(end))
				return grid[curr.getX()][curr.getY() - 1];

			addNeighbor(curr, grid[curr.getX() + 1][curr.getY() - 1], true); // upper right
			// check if neighbor is the end node
			if(grid[curr.getX() + 1][curr.getY() - 1].sameLocation(end))
				return grid[curr.getX() + 1][curr.getY() - 1];

			addNeighbor(curr, grid[curr.getX() - 1][curr.getY() - 1], true); // upper left
			// check if neighbor is the end node
			if(grid[curr.getX() - 1][curr.getY() - 1].sameLocation(end))
				return grid[curr.getX() - 1][curr.getY() - 1];
		}

		// top left corner
		else if(curr.getX() == 1 && curr.getY() == 1) {
			addNeighbor(curr, grid[curr.getX() + 1][curr.getY()], false); // right
			// check if neighbor is the end node
			if(grid[curr.getX() + 1][curr.getY()].sameLocation(end))
				return grid[curr.getX() + 1][curr.getY()];

			addNeighbor(curr, grid[curr.getX()][curr.getY() + 1], false); // down
			// check if neighbor is the end node
			if(grid[curr.getX()][curr.getY() + 1].sameLocation(end))
				return grid[curr.getX()][curr.getY() + 1];

			addNeighbor(curr, grid[curr.getX() + 1][curr.getY() + 1], true); // bot right
			// check if neighbor is the end node
			if(grid[curr.getX() + 1][curr.getY() + 1].sameLocation(end))
				return grid[curr.getX() + 1][curr.getY() + 1];
		}

		// top right corner
		else if(curr.getX() == rLen - 1 && curr.getY() == 1) {
			addNeighbor(curr, grid[curr.getX() - 1][curr.getY()], false); // left
			// check if neighbor is the end node
			if(grid[curr.getX() - 1][curr.getY()].sameLocation(end))
				return grid[curr.getX() - 1][curr.getY()];

			addNeighbor(curr, grid[curr.getX()][curr.getY() + 1], false); // down
			// check if neighbor is the end node
			if(grid[curr.getX()][curr.getY() + 1].sameLocation(end))
				return grid[curr.getX()][curr.getY() + 1];

			addNeighbor(curr, grid[curr.getX() - 1][curr.getY() + 1], true); // bot left
			// check if neighbor is the end node
			if(grid[curr.getX() - 1][curr.getY() + 1].sameLocation(end))
				return grid[curr.getX() - 1][curr.getY() + 1];
		}

		// bottom right corner
		else if(curr.getX() == rLen - 1 && curr.getY() == cLen - 1) {
			addNeighbor(curr, grid[curr.getX() - 1][curr.getY()], false); // left
			// check if neighbor is the end node
			if(grid[curr.getX() - 1][curr.getY()].sameLocation(end))
				return grid[curr.getX() - 1][curr.getY()];

			addNeighbor(curr, grid[curr.getX()][curr.getY() - 1], false); // up
			// check if neighbor is the end node
			if(grid[curr.getX()][curr.getY() - 1].sameLocation(end))
				return grid[curr.getX()][curr.getY() - 1];

			addNeighbor(curr, grid[curr.getX() - 1][curr.getY() - 1], true);  // upper left
			// check if neighbor is the end node
			if(grid[curr.getX() - 1][curr.getY() - 1].sameLocation(end))
				return grid[curr.getX() - 1][curr.getY() - 1];
		}

		// bottom left corner
		else if(curr.getX() ==  1 && curr.getY() == cLen - 1) {
			addNeighbor(curr, grid[curr.getX() + 1][curr.getY()], false); // right
			// check if neighbor is the end node
			if(grid[curr.getX() + 1][curr.getY()].sameLocation(end))
				return grid[curr.getX() + 1][curr.getY()];

			addNeighbor(curr, grid[curr.getX()][curr.getY() - 1], false); // up
			// check if neighbor is the end node
			if(grid[curr.getX()][curr.getY() - 1].sameLocation(end))
				return grid[curr.getX()][curr.getY() - 1];

			addNeighbor(curr, grid[curr.getX() + 1][curr.getY() - 1], true); // upper right
			// check if neighbor is the end node
			if(grid[curr.getX() + 1][curr.getY() - 1].sameLocation(end))
				return grid[curr.getX() + 1][curr.getY() - 1];
		}

		/* sorts by Node's f() value */
		Collections.sort(open, Comparator.comparingInt(Node::f));

		// maybe switch to priorityQueue
		return open.remove(0);
	}

	private void addNeighbor(Node curr, Node neighbor, boolean diagonal) {
		if(neighbor.isWall() || closed.contains(neighbor))
			return;

		if(neighbor.sameLocation(end)) {
			neighbor.setPrevious(curr);
			return;
		}

		if(diagonal) {
			// if curr is better option. (Relax edge)
			if(open.contains(neighbor) && neighbor.cost() > curr.cost() + DIAGONAL_COST)
				neighbor.setPrevious(curr);

			else if(!open.contains(neighbor)) {
				neighbor.setPrevious(curr);
				neighbor.setCost(DIAGONAL_COST + curr.cost());
				open.add(neighbor);
			}
		}

		else {
			// if curr is better option. (Relax edge)
			if(open.contains(neighbor) && neighbor.cost() > curr.cost() + COST)
				neighbor.setPrevious(curr);

			else if(!open.contains(neighbor)) {
				neighbor.setPrevious(curr);
				neighbor.setCost(COST + curr.cost());
				open.add(neighbor);
			}
		}
	}

	public void process() {
		Node curr = start;
		open.add(start);

		while(!open.isEmpty()) {
			curr = findBestNeighbor(curr);
			if(curr.sameLocation(end))
				break;
		}

		Stack<Node> stack = new Stack<>();

		while(curr != null) {
			stack.push(curr);
			curr = curr.previous();
		}

		while(!stack.isEmpty()) {
			Node n = stack.pop();
			if(n.sameLocation(start)) {
				System.out.println("Shortest Path: ");
				System.out.println("Start Node: " + n);
				System.out.println();
			}
			else if(n.sameLocation(end)) {
				System.out.println("End Node: " + n);
				System.out.println();
			}
			else {
				System.out.println("Path Node: " + n);
				System.out.println("Cost: " + n.cost());
				System.out.println("Heuristic: " + n.heuristic());
				System.out.println("f: " + n.f());
				System.out.println();
			}
		}
	}
}
