package pathfinding;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.HashSet;
import java.util.Stack;

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

  private String typeOfNode(Node curr) {
    int width = grid.length;
    int height = grid[0].length;

    if(curr.getX() > 1 && curr.getX() < width - 1 &&
       curr.getY() > 1 && curr.getY() < height - 1)
      return "Center Node";

    else if(curr.getX() == 1 && curr.getY() > 1 &&
            curr.getY() < height - 1)
      return "Left Edge Node";

    else if(curr.getY() == 1 && curr.getX() > 1 &&
            curr.getX() < width - 1)
      return "Top Edge Node";

    else if(curr.getX() == width - 1 && curr.getY() > 1 &&
            curr.getY() < height - 1)
      return "Right Edge Node";

    else if(curr.getY() == height - 1 && curr.getX() > 1 &&
            curr.getX() < width - 1)
      return "Bottom Edge Node";

    else if(curr.getX() == 1 &&
            curr.getY() == 1)
      return "Top Left Corner Node";

    else if(curr.getX() == width - 1 &&
            curr.getY() == 1)
      return "Top Right Corner Node";

    else if(curr.getX() == width - 1 &&
            curr.getY() == height - 1)
      return "Bottom Right Corner Node";

    else return "Bottom Left Corner Node";
  }

  private Node findBestNeighbor(Node curr) {
    Node right, down, left, up, upperRight, bottomRight, bottomLeft, upperLeft;
    List<Node> neighbors = new ArrayList<>();

    closed.add(curr);

    String typeOfNode = typeOfNode(curr);
    switch(typeOfNode) {
      case "Center Node":
        right = grid[curr.getX() + 1][curr.getY()];
        down  = grid[curr.getX()][curr.getY() + 1];
        left  = grid[curr.getX() - 1][curr.getY()];
        up    = grid[curr.getX()][curr.getY() - 1];
        upperRight  = grid[curr.getX() + 1][curr.getY() - 1];
        bottomRight = grid[curr.getX() + 1][curr.getY() + 1];
        bottomLeft  = grid[curr.getX() - 1][curr.getY() + 1];
        upperLeft   = grid[curr.getX() - 1][curr.getY() - 1];

        upperRight.setDiagonal(true);
        bottomRight.setDiagonal(true);
        bottomLeft.setDiagonal(true);
        upperLeft.setDiagonal(true);

        neighbors = Arrays.asList(
          right, down, left, up, upperRight,
          bottomRight, bottomLeft, upperLeft
        );
        break;

      case "Left Edge Node":
        right = grid[curr.getX() + 1][curr.getY()];
        down  = grid[curr.getX()][curr.getY() + 1];
        up    = grid[curr.getX()][curr.getY() - 1];
        upperRight  = grid[curr.getX() + 1][curr.getY() - 1];
        bottomRight = grid[curr.getX() + 1][curr.getY() + 1];

        upperRight.setDiagonal(true);
        bottomRight.setDiagonal(true);
        neighbors = Arrays.asList(right, down, up, upperRight, bottomRight);
        break;

      case "Top Edge Node":
        right = grid[curr.getX() + 1][curr.getY()];
        down  = grid[curr.getX()][curr.getY() + 1];
        left  = grid[curr.getX() - 1][curr.getY()];
        bottomRight = grid[curr.getX() + 1][curr.getY() + 1];
        bottomLeft  = grid[curr.getX() - 1][curr.getY() + 1];

        bottomRight.setDiagonal(true);
        bottomLeft.setDiagonal(true);
        neighbors = Arrays.asList(right, down, left, bottomRight, bottomLeft);
        break;

      case "Right Edge Node":
        down  = grid[curr.getX()][curr.getY() + 1];
        left  = grid[curr.getX() - 1][curr.getY()];
        up    = grid[curr.getX()][curr.getY() - 1];
        bottomLeft  = grid[curr.getX() - 1][curr.getY() + 1];
        upperLeft   = grid[curr.getX() - 1][curr.getY() - 1];

        bottomLeft.setDiagonal(true);
        upperLeft.setDiagonal(true);
        neighbors = Arrays.asList(down, left, up, bottomLeft, upperLeft);
        break;

      case "Bottom Edge Node":
        right = grid[curr.getX() + 1][curr.getY()];
        left  = grid[curr.getX() - 1][curr.getY()];
        up    = grid[curr.getX()][curr.getY() - 1];
        upperRight  = grid[curr.getX() + 1][curr.getY() - 1];
        upperLeft   = grid[curr.getX() - 1][curr.getY() - 1];

        upperRight.setDiagonal(true);
        upperLeft.setDiagonal(true);
        neighbors = Arrays.asList(right, left, up, upperRight, upperLeft);
        break;

      case "Top Left Corner Node":
        right = grid[curr.getX() + 1][curr.getY()];
        down  = grid[curr.getX()][curr.getY() + 1];
        bottomRight = grid[curr.getX() + 1][curr.getY() + 1];

        bottomRight.setDiagonal(true);
        neighbors = Arrays.asList(right, down, bottomRight);
        break;

      case "Top Right Corner Node":
        down  = grid[curr.getX()][curr.getY() + 1];
        left  = grid[curr.getX() - 1][curr.getY()];
        bottomLeft = grid[curr.getX() - 1][curr.getY() + 1];

        bottomLeft.setDiagonal(true);
        neighbors = Arrays.asList(down, left, bottomLeft);
        break;

      case "Bottom Right Corner Node":
        left = grid[curr.getX() - 1][curr.getY()];
        up   = grid[curr.getX()][curr.getY() - 1];
        upperLeft = grid[curr.getX() - 1][curr.getY() - 1];

        upperLeft.setDiagonal(true);
        neighbors = Arrays.asList(left, up, upperLeft);
        break;

      case "Bottom Left Corner Node":
        right = grid[curr.getX() + 1][curr.getY()];
        up    = grid[curr.getX()][curr.getY() - 1];
        upperRight = grid[curr.getX() + 1][curr.getY() - 1];

        upperRight.setDiagonal(true);
        neighbors = Arrays.asList(right, up, upperRight);
        break;
    }

    for(Node neighbor : neighbors) {
      if (neighbor.sameLocation(end)) {
        neighbor.setPrevious(curr);
        return neighbor;
      }
      addNeighbor(curr, neighbor);
    }

    /* sorts by Node's f() value */
    open.sort(Comparator.comparingInt(Node::f));

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
      // if curr is better option, relax edge
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
        System.out.println("Start Node: " + n + "\n");
      }

      else if(n.sameLocation(end))
        System.out.println("End Node: " + n + "\n");

      else {
        System.out.println("Path Node: " + n);
        System.out.println("Cost: " + n.cost());
        System.out.println("Heuristic: " + n.heuristic());
        System.out.println("f: " + n.f() + "\n");
      }
    }
  }
}
