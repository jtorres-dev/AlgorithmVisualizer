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
			grid[coord[0]][coord[1]].setWall(true);
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
			}
		}
	}

	/*
		I'll assume there is a more elegant way of doing this
		TODO:
		 - Improvements: maybe a double for loop? recursion? ...	
	*/
	private Node findBestNeighbor(Node curr) {
		int width = grid.length;
		int height = grid[0].length;

		closed.add(curr);

		// center node
		if(curr.getX() > 1 && curr.getX() < width - 1 &&
		   curr.getY() > 1 && curr.getY() < height - 1) {

			Node right = grid[curr.getX() + 1][curr.getY()];
			Node down  = grid[curr.getX()][curr.getY() + 1];
			Node left  = grid[curr.getX() - 1][curr.getY()];
			Node up    = grid[curr.getX()][curr.getY() - 1];

			Node upperRight  = grid[curr.getX() + 1][curr.getY() - 1];
			Node bottomRight = grid[curr.getX() + 1][curr.getY() + 1];
			Node bottomLeft  = grid[curr.getX() - 1][curr.getY() + 1];
			Node upperLeft   = grid[curr.getX() - 1][curr.getY() - 1];

			upperRight.setDiagonal(true);
			bottomRight.setDiagonal(true);
			bottomLeft.setDiagonal(true);
			upperLeft.setDiagonal(true);

			List<Node> neighbors = new ArrayList<>();
			Collections.addAll(neighbors, right, down, left, up, upperRight, bottomRight, bottomLeft, upperLeft);

			for(Node neighbor : neighbors) {
				if(neighbor.sameLocation(end)) {
					neighbor.setPrevious(curr);
					return neighbor;
				}
				addNeighbor(curr, neighbor);
			}
		}

		// left edge node
		else if(curr.getX() == 1 && curr.getY() > 1 && curr.getY() < height - 1) {
			Node right = grid[curr.getX() + 1][curr.getY()];
			Node down  = grid[curr.getX()][curr.getY() + 1];
			Node up    = grid[curr.getX()][curr.getY() - 1];

			Node upperRight  = grid[curr.getX() + 1][curr.getY() - 1];
			Node bottomRight = grid[curr.getX() + 1][curr.getY() + 1];
			upperRight.setDiagonal(true);
			bottomRight.setDiagonal(true);

			List<Node> neighbors = new ArrayList<>();
			Collections.addAll(neighbors, right, down, up, upperRight, bottomRight);

			for(Node neighbor : neighbors) {
				if(neighbor.sameLocation(end)) {
					neighbor.setPrevious(curr);
					return neighbor;
				}
				addNeighbor(curr, neighbor);
			}
		}

		// top edge node
		else if(curr.getY() == 1 && curr.getX() > 1 && curr.getX() < width - 1) {
			Node right = grid[curr.getX() + 1][curr.getY()];
			Node down  = grid[curr.getX()][curr.getY() + 1];
			Node left  = grid[curr.getX() - 1][curr.getY()];

			Node bottomRight = grid[curr.getX() + 1][curr.getY() + 1];
			Node bottomLeft  = grid[curr.getX() - 1][curr.getY() + 1];
			bottomRight.setDiagonal(true);
			bottomLeft.setDiagonal(true);

			List<Node> neighbors = new ArrayList<>();
			Collections.addAll(neighbors, right, down, left, bottomRight, bottomLeft);

			for(Node neighbor : neighbors) {
				if(neighbor.sameLocation(end)) {
					neighbor.setPrevious(curr);
					return neighbor;
				}
				addNeighbor(curr, neighbor);
			}
		}

		// right edge node
		else if(curr.getX() == width - 1 && curr.getY() > 1 && curr.getY() < height - 1) {
			Node down  = grid[curr.getX()][curr.getY() + 1];
			Node left  = grid[curr.getX() - 1][curr.getY()];
			Node up    = grid[curr.getX()][curr.getY() - 1];

			Node bottomLeft  = grid[curr.getX() - 1][curr.getY() + 1];
			Node upperLeft   = grid[curr.getX() - 1][curr.getY() - 1];
			bottomLeft.setDiagonal(true);
			upperLeft.setDiagonal(true);

			List<Node> neighbors = new ArrayList<>();
			Collections.addAll(neighbors, down, left, up, bottomLeft, upperLeft);

			for(Node neighbor : neighbors) {
				if(neighbor.sameLocation(end)) {
					neighbor.setPrevious(curr);
					return neighbor;
				}
				addNeighbor(curr, neighbor);
			}
		}

		// bottom edge node
		else if(curr.getY() == height - 1 && curr.getX() > 1 && curr.getX() < width - 1) {
			Node right = grid[curr.getX() + 1][curr.getY()];
			Node left  = grid[curr.getX() - 1][curr.getY()];
			Node up    = grid[curr.getX()][curr.getY() - 1];

			Node upperRight  = grid[curr.getX() + 1][curr.getY() - 1];
			Node upperLeft   = grid[curr.getX() - 1][curr.getY() - 1];
			upperRight.setDiagonal(true);
			upperLeft.setDiagonal(true);

			List<Node> neighbors = new ArrayList<>();
			Collections.addAll(neighbors, right, left, up, upperRight, upperLeft);

			for(Node neighbor : neighbors) {
				if(neighbor.sameLocation(end)) {
					neighbor.setPrevious(curr);
					return neighbor;
				}
				addNeighbor(curr, neighbor);
			}
		}

		// top left corner node
		else if(curr.getX() == 1 && curr.getY() == 1) {
			Node right = grid[curr.getX() + 1][curr.getY()];
			Node down  = grid[curr.getX()][curr.getY() + 1];

			Node bottomRight = grid[curr.getX() + 1][curr.getY() + 1];
			bottomRight.setDiagonal(true);

			List<Node> neighbors = new ArrayList<>();
			Collections.addAll(neighbors, right, down, bottomRight);

			for(Node neighbor : neighbors) {
				if(neighbor.sameLocation(end)) {
					neighbor.setPrevious(curr);
					return neighbor;
				}
				addNeighbor(curr, neighbor);
			}
		}

		// top right corner node
		else if(curr.getX() == width - 1 && curr.getY() == 1) {
			Node down = grid[curr.getX()][curr.getY() + 1];
			Node left = grid[curr.getX() - 1][curr.getY()];

			Node bottomLeft = grid[curr.getX() - 1][curr.getY() + 1];
			bottomLeft.setDiagonal(true);

			List<Node> neighbors = new ArrayList<>();
			Collections.addAll(neighbors, down, left, bottomLeft);

			for(Node neighbor : neighbors) {
				if(neighbor.sameLocation(end)) {
					neighbor.setPrevious(curr);
					return neighbor;
				}
				addNeighbor(curr, neighbor);
			}
		}

		// bottom right corner node
		else if(curr.getX() == width - 1 && curr.getY() == height - 1) {
			Node left = grid[curr.getX() - 1][curr.getY()];
			Node up   = grid[curr.getX()][curr.getY() - 1];

			Node upperLeft = grid[curr.getX() - 1][curr.getY() - 1];
			upperLeft.setDiagonal(true);

			List<Node> neighbors = new ArrayList<>();
			Collections.addAll(neighbors, left, up, upperLeft);

			for(Node neighbor : neighbors) {
				if(neighbor.sameLocation(end)) {
					neighbor.setPrevious(curr);
					return neighbor;
				}
				addNeighbor(curr, neighbor);
			}
		}

		// bottom left corner node
		else if(curr.getX() ==  1 && curr.getY() == height - 1) {
			Node right = grid[curr.getX() + 1][curr.getY()];
			Node up    = grid[curr.getX()][curr.getY() - 1];

			Node upperRight  = grid[curr.getX() + 1][curr.getY() - 1];
			upperRight.setDiagonal(true);

			List<Node> neighbors = new ArrayList<>();
			Collections.addAll(neighbors, right, up, upperRight);

			for(Node neighbor : neighbors) {
				if(neighbor.sameLocation(end)) {
					neighbor.setPrevious(curr);
					return neighbor;
				}
				addNeighbor(curr, neighbor);
			}
		}

		/* sorts by Node's f() value */
		Collections.sort(open, Comparator.comparingInt(Node::f));

		// maybe switch to priorityQueue
		return open.remove(0);
	}

	private void addNeighbor(Node curr, Node neighbor) {
		if(neighbor.isWall() || closed.contains(neighbor))
			return;

		if(neighbor.isDiagonal()) {
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
