import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ADTPackage.LinkedStack;
import ADTPackage.QueueInterface;
import ADTPackage.StackInterface;
import GraphPackage.DirectedGraph;

/**
 * test
 */
public class Test {

    // Creating a 2D array for the maze
    private static char[][] maze;
    // Creating directed weighted graph
    private static DirectedGraph<String> graphWeighted;
    // Creating directed unweighted graph
    private static DirectedGraph<String> graphUnweighted;
    // To change the maze, change the file name here
    private static String fileName = "maze2.txt";
    

    public static void main(String[] args) {

        // Resetting the variables
        maze = null;
        graphWeighted = null;
        graphUnweighted = null;

        // Creating a buffered reader to count the number of lines in the file
        BufferedReader inputStreamCounter = null;
        // Creating a buffered reader to read the file
        BufferedReader inputStream = null;
        // Creating a string to store the line
        String line;

        // Try-Catch block for errors while reading the file
        try {

            // Counting the number of lines in the file called 'mazes'
            inputStreamCounter = new BufferedReader(new FileReader(fileName));
            int lineCount = 0;
            while ((line = inputStreamCounter.readLine()) != null) {
                lineCount++;
            }

            // Creating a 2D array for the maze
            maze = new char[lineCount][];

            // Reading the maze from the file
            inputStream = new BufferedReader(new FileReader(fileName));
            lineCount = 0;
            while ((line = inputStream.readLine()) != null) {
                maze[lineCount] = line.toCharArray(); // Converting the string to char array
                lineCount++; // Increasing the line count
            }

            // Creating two graphs
            // First one is for DFS, BFS, and shortestPath
            // Second one is for cheapestPath
            graphWeighted = new DirectedGraph<String>();
            graphUnweighted = new DirectedGraph<String>();

            // Adding the vertices to the graphs
            for (int x = 0; x < maze.length; x++) {
                for (int y = 0; y < maze[x].length; y++) {
                    if (maze[x][y] == ' ') {
                        graphWeighted.addVertex(x + "-" + y);
                        graphUnweighted.addVertex(x + "-" + y);
                    }
                }
            }

            // Adding the edges to the graphs
            for (int x = 0; x < maze.length; x++) {
                for (int y = 0; y < maze[x].length; y++) {
                    if (maze[x][y] == ' ') {

                        // Creating a random number between 1 and 4
                        double randomNumber = (double) 1 + (int) (Math.random() * 4);
                        //System.out.println(randomNumber); // For testing

                        if (x + 1 < maze.length && maze[x + 1][y] == ' ') {
                            graphUnweighted.addEdge(x + "-" + y, (x + 1) + "-" + y);
                            graphWeighted.addEdge(x + "-" + y, (x + 1) + "-" + y, randomNumber);
                        }
                        if (x - 1 >= 0 && maze[x - 1][y] == ' ') {
                            graphUnweighted.addEdge(x + "-" + y, (x - 1) + "-" + y);
                            graphWeighted.addEdge(x + "-" + y, (x - 1) + "-" + y, randomNumber);
                        }
                        if (y + 1 < maze[x].length && maze[x][y + 1] == ' ') {
                            graphUnweighted.addEdge(x + "-" + y, x + "-" + (y + 1));
                            graphWeighted.addEdge(x + "-" + y, x + "-" + (y + 1), randomNumber);
                        }
                        if (y - 1 >= 0 && maze[x][y - 1] == ' ') {
                            graphUnweighted.addEdge(x + "-" + y, x + "-" + (y - 1));
                            graphWeighted.addEdge(x + "-" + y, x + "-" + (y - 1), randomNumber);
                        }
                    }
                }
            }

            // Before solving the maze
            // printMaze(maze); // For testing

            // Printing the properties of the maze
            printProperties();

            // Solving the maze by BFS
            printBFSMaze();

            // Solving the maze by DFS
            printDFSMaze();

            // Solving the maze by shortest path
            printShortestPathMaze();

            // Solving the maze by cheapest path
            printCheapestPathMaze();

            System.out.println();
        } catch (IOException e) {
            System.out.println(e);
            System.out.printf("Problem accourt while %s file is opening.\n", fileName);
            System.out.println("Please check the file name and try again.\n");
            System.exit(0);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    inputStreamCounter.close();
                } catch (IOException e) {
                    System.out.println(e);
                    System.out.printf("Problem accourt while %s file is closing.\n", fileName);
                    e.printStackTrace();
                    System.exit(1);
                }
            }

        }
    }

    // This function prints maze
    public static void printMaze(char[][] maze) {
        // Print Maze
        for (int x = 0; x < maze.length; x++) {
            for (int y = 0; y < maze[x].length; y++) {
                System.out.print(maze[x][y]);
            }
            System.out.println();
        }
        System.out.println();
    }

    // Print properties of the maze
    public static void printProperties(){

        // Printing name of the maze and start and end points
        System.out.println("\nProperties of the Maze:");
        System.out.println("Maze: " + fileName);
        System.out.println("Start: 0-1 End: " + (maze.length - 2) + "-" + (maze[maze.length - 2].length - 1));

        // Printing the number of vertices and edges
        System.out.println("Number of Vertices: " + graphUnweighted.getNumberOfVertices());
        System.out.println("Number of Edges: " + graphUnweighted.getNumberOfEdges());
        System.out.println();

        // Adjeacency list of the maze
        System.out.println("Adjacency List of the Maze:");
        System.out.println();
        // Adjeacency matrix of the maze
        graphUnweighted.printAdjacencyMatrix();

        // Displaying adjency list of the maze
        graphWeighted.displayEdges();
        System.out.println();
    }
 
    // This function creates a unique copy of maze array
    public static char[][] createCopyMaze(){
        // Creating a copy of maze
        char[][] copyMaze = new char[maze.length][];
        for (int i = 0; i < copyMaze.length; i++) {
            copyMaze[i] = new char[maze[i].length];
            System.arraycopy(maze[i], 0, copyMaze[i], 0, maze[i].length);
        }
        return copyMaze;
    }

    // Print breath first maze
    public static void printBFSMaze() {

        // Printing the name of the algorithm
        System.out.println("Breadth First Traversal");

        // Getting the traversal path
        QueueInterface<String> path = graphUnweighted.getBreadthFirstTraversal("0-1",
                (maze.length - 2) + "-" + (maze[maze.length - 2].length - 1));

        // Printing the number of vertices visited
        System.out.println("Number of vertices visited: " + graphUnweighted.getNumberOfVerticesVisited());

        // Creating a copy of maze
        char[][] copyMaze = createCopyMaze();
        
        // Counting the length of the path
        int counter = 0;
        // Implementing the path to copyMaze and printing the traversal path           
        System.out.print("Traversal path: ");
        while (!path.isEmpty()) {
            String vertexName = path.dequeue();
            System.out.print(vertexName + " -> ");
            String[] temp = vertexName.split("-");
            copyMaze[Integer.parseInt(temp[0])][Integer.parseInt(temp[1])] = '.';
            counter++;
        }
        System.out.println("\nLength of the path: " + counter);
        System.out.println("\nTraversal path on the maze:");

        // Printing the new maze
        printMaze(copyMaze);
    }

    // Print depth first maze
    public static void printDFSMaze() {

        // Printing the name of the algorithm
        System.out.println("Depth First Traversal");

        // Getting the traversal path
        QueueInterface<String> path = graphUnweighted.getDepthFirstTraversal("0-1",
                (maze.length - 2) + "-" + (maze[maze.length - 2].length - 1));

        // Printing the number of vertices visited
        System.out.println("Number of vertices visited: " + graphUnweighted.getNumberOfVerticesVisited());
        
        // Creating a copy of maze
        char[][] copyMaze = createCopyMaze();

        // Counting the length of the path
        int counter = 0;
        // Implementing the path to copyMaze and printing the traversal path
        System.out.print("Traversal path: ");
        while (!path.isEmpty()) {
            String vertexName = path.dequeue();
            System.out.print(vertexName + " -> ");
            String[] temp = vertexName.split("-");
            copyMaze[Integer.parseInt(temp[0])][Integer.parseInt(temp[1])] = '.';
            counter++;
        }
        System.out.println("\nLength of the path: " + counter);
        System.out.println("\nTraversal path on the maze:");

        // Printing the new maze
        printMaze(copyMaze);
    }

    // Print shortest path maze
    public static void printShortestPathMaze() {

        // Printing the name of the algorithm
        System.out.println("Shortest Path");

        // Creating an empty stack to store the path
        // Than getting the length of the path and path in stack
        StackInterface<String> stackPath = new LinkedStack<>();
        int lenghtOfPath = graphUnweighted.getShortestPath("0-1",
                (maze.length - 2) + "-" + (maze[maze.length - 2].length - 1), stackPath);

        // Printing the number of vertices visited
        System.out.println("Number of vertices visited: " + graphUnweighted.getNumberOfVerticesVisited());

        // Creating a copy of maze
        char[][] copyMaze = createCopyMaze();

        // Printing the cost of the path
        System.out.println("Cost of shortest path is: " + lenghtOfPath);

        // Printing the traversal path
        System.out.print("Traversal path: ");

        // Counting the length of the path
        int counter = 0;
        // Printing the traversal path and implementing the path to copyMaze
        while (!stackPath.isEmpty()) {
            String vertexName = stackPath.pop();
            System.out.print(vertexName + " -> ");
            String[] temp = vertexName.split("-");
            copyMaze[Integer.parseInt(temp[0])][Integer.parseInt(temp[1])] = '.';
            counter++;
        }
        System.out.println("\nLength of the path: " + counter);
        System.out.println("\nTraversal path on the maze:");

        // Printing the new maze
        printMaze(copyMaze);
    }

    // print cheapest path maze
    public static void printCheapestPathMaze() {
        
        // Printing the name of the algorithm
        System.out.println("Cheapest Path");

        // Creating an empty stack to store the path
        // Than getting the length of the path and path in stack
        StackInterface<String> stackPath = new LinkedStack<>();
        double costOfPath = graphWeighted.getCheapestPath("0-1",
                (maze.length - 2) + "-" + (maze[maze.length - 2].length - 1), stackPath);

        // Printing the number of vertices visited
        System.out.println("Number of vertices visited: " + graphWeighted.getNumberOfVerticesVisited());

        // Creating a copy of maze
        char[][] copyMaze = createCopyMaze();

        // Printing the cost of the path
        System.out.println("Cost of cheapest path is: " + costOfPath);

        // Counting the length of the path
        int counter = 0;
        // Printing the traversal path and implementing the path to copyMaze
        System.out.print("Traversal path: ");
        while (!stackPath.isEmpty()) {
            String vertexName = stackPath.pop();
            System.out.print(vertexName + " -> ");
            String[] temp = vertexName.split("-");
            copyMaze[Integer.parseInt(temp[0])][Integer.parseInt(temp[1])] = '.';
            counter++;
        }
        System.out.println("\nLength of the path: " + counter);
        System.out.println("\nTraversal path on the maze:");

        // Printing the new maze
        printMaze(copyMaze);
    }
}