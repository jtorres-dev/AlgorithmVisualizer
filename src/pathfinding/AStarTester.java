package pathfinding;

public class AStarTester {

    public static void main(String[] args) {
//        Node start = new Node(2, 3);
//        Node end = new Node(5, 6);
//        int[][] walls =  new int[][] {
//                {2, 5}, {4, 3}, {4, 5}, {4, 6}
//        };

        Node start = new Node(3, 7);
        Node end = new Node(5, 5);
        int[][] walls =  new int[][] {
                {5, 3}, {5, 4}, {4, 4}, {4, 5}, {4, 6}, {4, 7}
        };

        AStar astar = new AStar(7, start, end, walls);

		astar.process();
    }
}
