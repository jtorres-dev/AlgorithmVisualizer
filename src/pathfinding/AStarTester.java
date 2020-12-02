package pathfinding;

public class AStarTester {

    public static void main(String[] args) {
        Node start = new Node(2, 3);
        Node end = new Node(5, 6);
        int[][] walls =  new int[][] {
                {2, 5}, {4, 3}, {4, 5}, {4, 6}
        };

        AStar astar = new AStar(7, start, end, walls);

		astar.process();
    }
}
