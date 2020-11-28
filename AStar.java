import java.util.Queue;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;

public class AStar {
    private static final int DIAGONAL_COST = 14;
    private static final int V_H_COST = 10;

    private Node start, end;
    
    //grid made of nodes
    private Node[][] grid;

    private Queue<Node> open;
    private List<Node> closed;


    public AStar(Node start, Node end, int width, int height, int[][] walls) {
        this.start = start;
        this.end = end;

        grid = new Node[width][height];

        open = new PriorityQueue<Node>((Node n1, Node n2) -> {
            return n1.cost() < n2.cost() ? -1 : n1.cost() > n2.cost() ? 1 : 0;
        });
        closed = new ArrayList<Node>();


        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Node(i, j); //x and y coords
                grid[i][j].isInPath(false);
                calcHeuristic(grid[i][j]);
            }
        }

        start.setCost(0);

        /* ?? */
        //we put the walls on the grid
        for(int i = 0; i < walls.length; i++) {
            setWall(walls[i][0], walls[i][1]);
        }
    }

    public void setWall(int x, int y) {
        grid[x][y] = null;
    }

    public void calcHeuristic(Node current) {
        int dx = Math.abs(current.getX() - end.getX()); 
        int dy = Math.abs(current.getY() -  end.getY());

        int heuristic = (int) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

        current.setHeuristic(heuristic);
    }

    public void updateCost(Node current, Node next, int cost) {
        if(next == null || closed.contains(next))
            return;

        int finalCost = next.heuristic() + cost;

        if(!open.contains(next) || finalCost < next.cost()) {
            next.setCost(finalCost);
            next.setPrevious(current);
        }

        if(!open.contains(next))
            open.add(next);
    }

    public void process() {
        Node current;
        Node next;

        /*refine ugly*/
        // we add the start location to open list
        open.add(grid[start.getX()][start.getY()]);

        while(true) {
            current = open.poll();
            
            if(current == null)
                break;

            closed.add(current);
            
            /*refine ugly*/
            if(current.equals(grid[end.getX()][end.getY()]))
                return;
            
            

            if(current.getX() - 1 >= 0) {
                next = grid[current.getX() - 1][current.getY()];
                updateCost(current, next, current.cost() + V_H_COST);

                if(current.getY() - 1 >= 0) {
                    next = grid[current.getX() - 1][current.getY() - 1];
                    updateCost(current, next, current.cost() + DIAGONAL_COST);
                }

                if(current.getY() + 1 < grid[0].length) {
                    next = grid[current.getX() - 1][current.getY() + 1];
                    updateCost(current, next, current.cost() + DIAGONAL_COST);
                }
            }

            if(current.getY() - 1 >= 0) {
                next = grid[current.getX()][current.getY() - 1];
                updateCost(current, next, current.cost() + V_H_COST);
            }

            if(current.getY() + 1 < grid[0].length) {
                next = grid[current.getX()][current.getY() + 1];
                updateCost(current, next, current.cost() + V_H_COST);
            }
            

            if(current.getX() + 1 < grid.length) {
                next = grid[current.getX() + 1][current.getY()];
                updateCost(current, next, current.cost() + V_H_COST);

                if(current.getY() - 1 >= 0) {
                    next = grid[current.getX() + 1][current.getY() - 1];
                    updateCost(current, next, current.cost() + DIAGONAL_COST);
                }

                if(current.getY() + 1 < grid[0].length) {
                    next = grid[current.getX() + 1][current.getY() + 1];
                    updateCost(current, next, current.cost() + DIAGONAL_COST);
                }
            }
        }
    }

    public void display() {
        System.out.println("Grid: ");

        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                
                if(i == start.getX() && j == start.getY())
                    System.out.print("SC  "); // Source Node
                
                else if(i == end.getX() && j == end.getY())
                    System.out.print("EC  "); // End Node
                
                else if(grid[i][j] != null)
                    System.out.printf("%-3d ", 0);
                
                else
                    System.out.print("BL  "); // Block Node
            }

            System.out.println();
        }

        System.out.println();
    }

    public void displayScores() {
        System.out.println("\nScores for nodes: ");

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] != null)
                    System.out.printf("%-3d ", grid[i][j].cost());   
                else
                    System.out.print("BL  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void displaySolution() {
        /*refine ugly*/
        if(closed.contains(grid[end.getX()][end.getY()])) {
            System.out.print("Path: \n -> ");
            
            /*refine ugly*/
            Node current = grid[end.getX()][end.getY()];
            
            System.out.println(current);
            
            current.isInPath(true);

            while(current.previous() != null) {
                System.out.println(" -> " + current.previous());
                current.previous().isInPath(true);
                current = current.previous();
            }

            System.out.println("\n");

            for(int i = 0; i < grid.length; i++) {
                for(int j = 0; j < grid[i].length; j++) {
                    
                    if(i == start.getX() && j == start.getY())
                        System.out.print("SC  "); // Source Node
                    
                    else if(i == end.getX() && j == end.getY())
                        System.out.print("EC  "); // End Node
                    
                    else if(grid[i][j] != null)
                        System.out.printf("%-3s ", grid[i][j].solution() ? "X" : "O");
                    
                    else
                        System.out.print("BL  "); // Block Node
                }

                System.out.println();
            }

            System.out.println();

        }

        else
            System.out.println("No possible path.");
    }

    public void tester() {

        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] != null)
                    System.out.print(grid[i][j].solution() + " ");
            }
        }
    }

    public static void main(String[] args) {
        Node start = new Node(0, 0);
        Node end = new Node(3, 2);


        //AStar(Node start, Node end, int width, int height, int[][] walls)
        AStar  aStar = new AStar(start, end, 5, 5, 
            new int[][] { 
                {0,4}, {2,2}, {3,1}, {3,3}, {2,1}, {2,3}    
            }
        );
        
        aStar.display();
        aStar.process(); // Apple A* Algorithm
        aStar.displayScores(); // Display Scores on grid
        aStar.displaySolution(); // display solution path-
        //Star.tester();
    }

}
