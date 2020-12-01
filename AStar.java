import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Stack;


public class AStar {
	private static List<Node> open = new ArrayList<>();
	private static Set<Node> closed = new HashSet<>();

	private static Node start, end;	
	
	// sqrt(2) * 10
	private static final int COST = 10;
	private static final int DIAGONAL_COST = 14;

	private static Node[][] grid;
	/*	
	    createGrid(7):

	    x-axis
		    1   2   3   4   5   6  
	y-axis    +-----------------------+
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
	public static void createGrid(int size) {
		grid = new Node[size][size];
		for(int i = 1; i < grid.length; i++) {
			for(int j = 1; j < grid[0].length; j++) {
				grid[i][j] = new Node(i, j);
			}
		}
	}

	public static void setWallNodes(int[][] coords) {
		for(int[] coord : coords)
			grid[coord[0]][coord[1]].setWall();
	}

	// Manhattan format
	public static void calculateHeuristics(Node end) {
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
				// System.out.println("Node: " + grid[i][j] + " Heuristic: " + grid[i][j].heuristic());
				// System.out.println();
			}
		}
	}

	/* 
		I'll assume there is a more elegant way of doing this instead of hand declaring all the edge cases. 

		TODO:
		 - Improvements: maybe a double for loop? recursion? ...	
	*/
	public static Node findBestNeighbor(Node curr) {
		int rLen = grid.length;
		int cLen = grid[0].length;
		
		closed.add(curr);

		// center
		if(curr.getX() > 1 && curr.getX() < rLen - 1 &&
		   curr.getY() > 1 && curr.getY() < cLen - 1) { 
			addNeighbor(curr, grid[curr.getX() + 1][curr.getY()], false); // right
			addNeighbor(curr, grid[curr.getX() - 1][curr.getY()], false); // left
			addNeighbor(curr, grid[curr.getX()][curr.getY() + 1], false); // down
			addNeighbor(curr, grid[curr.getX()][curr.getY() - 1], false); // up
			addNeighbor(curr, grid[curr.getX() + 1][curr.getY() + 1], true); // bot right  
			addNeighbor(curr, grid[curr.getX() + 1][curr.getY() - 1], true); // upper right
			addNeighbor(curr, grid[curr.getX() - 1][curr.getY() + 1], true); // bot left
			addNeighbor(curr, grid[curr.getX() - 1][curr.getY() - 1], true); // upper left	        
		} 

		// left edge
		else if(curr.getX() == 1 && curr.getY() > 1 && curr.getY() < cLen - 1) {
			addNeighbor(curr, grid[curr.getX() + 1][curr.getY()], false); // right
			addNeighbor(curr, grid[curr.getX()][curr.getY() + 1], false); // down
			addNeighbor(curr, grid[curr.getX()][curr.getY() - 1], false); // up
			addNeighbor(curr, grid[curr.getX() + 1][curr.getY() + 1], true); // bot right
			addNeighbor(curr, grid[curr.getX() + 1][curr.getY() - 1], true); // upper right
		}

		// top edge
		else if(curr.getY() == 1 && curr.getX() > 1 && curr.getX() < rLen - 1) { 
			addNeighbor(curr, grid[curr.getX() + 1][curr.getY()], false); // right
			addNeighbor(curr, grid[curr.getX() - 1][curr.getY()], false); // left
			addNeighbor(curr, grid[curr.getX()][curr.getY() + 1], false); // down
			addNeighbor(curr, grid[curr.getX() + 1][curr.getY() + 1], true); // bot right
			addNeighbor(curr, grid[curr.getX() - 1][curr.getY() + 1], true); // bot left
		}

		// right edge
		else if(curr.getX() == rLen - 1 && curr.getY() > 1 && curr.getY() < cLen - 1) {
			addNeighbor(curr, grid[curr.getX() - 1][curr.getY()], false); // left
			addNeighbor(curr, grid[curr.getX()][curr.getY() + 1], false); // down
			addNeighbor(curr, grid[curr.getX()][curr.getY() - 1], false); // up
			addNeighbor(curr, grid[curr.getX() - 1][curr.getY() + 1], true); // bot left
			addNeighbor(curr, grid[curr.getX() - 1][curr.getY() - 1], true); // upper left        	
		}

		// bottom edge
		else if(curr.getY() == cLen - 1 && curr.getX() > 1 && curr.getX() < rLen - 1) {
			addNeighbor(curr, grid[curr.getX() + 1][curr.getY()], false); // right
			addNeighbor(curr, grid[curr.getX() - 1][curr.getY()], false); // left
			addNeighbor(curr, grid[curr.getX()][curr.getY() - 1], false); // up
			addNeighbor(curr, grid[curr.getX() + 1][curr.getY() - 1], true); // upper right
			addNeighbor(curr, grid[curr.getX() - 1][curr.getY() - 1], true); // upper left
		}

		// top left corner
		else if(curr.getX() == 1 && curr.getY() == 1) {
			addNeighbor(curr, grid[curr.getX() + 1][curr.getY()], false); // right
			addNeighbor(curr, grid[curr.getX()][curr.getY() + 1], false); // down
			addNeighbor(curr, grid[curr.getX() + 1][curr.getY() + 1], true); // bot right
		}

		// top right corner
		else if(curr.getX() == rLen - 1 && curr.getY() == 1) {
			addNeighbor(curr, grid[curr.getX() - 1][curr.getY()], false); // left
			addNeighbor(curr, grid[curr.getX()][curr.getY() + 1], false); // down
			addNeighbor(curr, grid[curr.getX() - 1][curr.getY() + 1], true); // bot left
		}

		// bottom right corner
		else if(curr.getX() == rLen - 1 && curr.getY() == cLen - 1) {
			addNeighbor(curr, grid[curr.getX() - 1][curr.getY()], false); // left
			addNeighbor(curr, grid[curr.getX()][curr.getY() - 1], false); // up
			addNeighbor(curr, grid[curr.getX() - 1][curr.getY() - 1], true);  // upper left
		}

		// bottom left corner
		else if(curr.getX() ==  1 && curr.getY() == cLen - 1) {
			addNeighbor(curr, grid[curr.getX()][curr.getY() - 1], false); // up
			addNeighbor(curr, grid[curr.getX() + 1][curr.getY()], false); // right
			addNeighbor(curr, grid[curr.getX() + 1][curr.getY() - 1], true); // upper right
		}

		Collections.sort(open, (n1, n2) -> {
			return n1.f() - n2.f();
		});

		// maybe switch to priorityQueue
		return open.remove(0);
	}

	public static void addNeighbor(Node curr, Node neighbor, boolean diagonal) {
		if(neighbor.isWall() || closed.contains(neighbor))
			return;

		if(diagonal) { 
			// if previous neighbors are better options
			if(open.contains(neighbor) && neighbor.cost() < DIAGONAL_COST + curr.cost()) 
				neighbor.setPrevious(curr);
			
			neighbor.setCost(DIAGONAL_COST + curr.cost());
		}

		else {
			// if previous neighbors are better options
			if(open.contains(neighbor) && neighbor.cost() < COST + curr.cost()) 
				neighbor.setPrevious(curr);
			
			neighbor.setCost(COST + curr.cost());
		}

		if(!open.contains(neighbor))
			neighbor.setPrevious(curr);

	    open.add(neighbor);
	}

	public static void process(Node start) {
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

	public static void main(String[] args) {
		start = new Node(2, 3);
		end = new Node(5, 6);

		createGrid(7);
		setWallNodes(new int[][] {
			{2, 5}, {4, 3}, {4, 5}, {4, 6}
		});

		calculateHeuristics(end);
		process(start);
	}
}
