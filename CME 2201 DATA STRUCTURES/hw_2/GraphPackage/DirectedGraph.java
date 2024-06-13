package GraphPackage;

import java.util.Iterator;
import ADTPackage.*; // Classes that implement various ADTs

/**
 * A class that implements the ADT directed graph.
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 5.1
 */
public class DirectedGraph<T> implements GraphInterface<T> {
	private DictionaryInterface<T, VertexInterface<T>> vertices;
	private int edgeCount;
	private int numberOfVerticesVisited;

	public DirectedGraph() {
		vertices = new UnsortedLinkedDictionary<>();
		edgeCount = 0;
		numberOfVerticesVisited = 0;
	} // end default constructor

	public boolean addVertex(T vertexLabel) {
		VertexInterface<T> addOutcome = vertices.add(vertexLabel, new Vertex<>(vertexLabel));
		return addOutcome == null; // Was addition to dictionary successful?
	} // end addVertex

	public boolean addEdge(T begin, T end, double edgeWeight) {
		boolean result = false;
		VertexInterface<T> beginVertex = vertices.getValue(begin);
		VertexInterface<T> endVertex = vertices.getValue(end);
		if ((beginVertex != null) && (endVertex != null))
			result = beginVertex.connect(endVertex, edgeWeight);
		if (result)
			edgeCount++;
		return result;
	} // end addEdge

	public boolean addEdge(T begin, T end) {
		return addEdge(begin, end, 0);
	} // end addEdge

	public boolean hasEdge(T begin, T end) {
		boolean found = false;
		VertexInterface<T> beginVertex = vertices.getValue(begin);
		VertexInterface<T> endVertex = vertices.getValue(end);
		if ((beginVertex != null) && (endVertex != null)) {
			Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
			while (!found && neighbors.hasNext()) {
				VertexInterface<T> nextNeighbor = neighbors.next();
				if (endVertex.equals(nextNeighbor))
					found = true;
			} // end while
		} // end if

		return found;
	} // end hasEdge

	public boolean isEmpty() {
		return vertices.isEmpty();
	} // end isEmpty

	public void clear() {
		vertices.clear();
		edgeCount = 0;
	} // end clear

	public int getNumberOfVertices() {
		return vertices.getSize();
	} // end getNumberOfVertices

	public int getNumberOfEdges() {
		return edgeCount;
	} // end getNumberOfEdges

	protected void resetVertices() {
		Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
		while (vertexIterator.hasNext()) {
			VertexInterface<T> nextVertex = vertexIterator.next();
			nextVertex.unvisit();
			nextVertex.setCost(0);
			nextVertex.setPredecessor(null);
		} // end while
		numberOfVerticesVisited = 0;
	} // end resetVertices

	public StackInterface<T> getTopologicalOrder() {
		resetVertices();

		StackInterface<T> vertexStack = new LinkedStack<>();
		int numberOfVertices = getNumberOfVertices();
		for (int counter = 1; counter <= numberOfVertices; counter++) {
			VertexInterface<T> nextVertex = findTerminal();
			nextVertex.visit();
			vertexStack.push(nextVertex.getLabel());
		} // end for

		return vertexStack;
	} // end getTopologicalOrder

	//##########################################################################################################################
	// Breadth First Search
	public QueueInterface<T> getBreadthFirstTraversal(T origin, T end) {
		// Reset the vertices
		resetVertices();

		// These queues are used to traverse the graph
		QueueInterface<T> traversalOrder = new LinkedQueue<>();
		QueueInterface<T> vertexQueue = new LinkedQueue<>();

		// Enqueue the origin vertex
		vertexQueue.enqueue(origin);

		// Visit the origin vertex
		VertexInterface<T> vertex = vertices.getValue(origin);
		vertex.visit();
		// Increment the number of vertices visited
		numberOfVerticesVisited++;

		// While the queue is not empty every vertex in the queue is being visited
		Boolean flag = false;
		while (!vertexQueue.isEmpty() && !flag) {

			// Get the next vertex in the queue
			while ((vertex = vertices.getValue(vertexQueue.getFront()).getUnvisitedNeighbor()) != null) {
				// Visit the vertex
				vertex.visit();
				// Increment the number of vertices visited
				numberOfVerticesVisited++;
				// Enqueue the vertex
				vertexQueue.enqueue(vertex.getLabel());

				// If the end vertex is found, break out of the loop
				if (vertex.getLabel().equals(end)) {
					flag = true;
					break;
				}
			}

			// Dequeue the vertex and enqueue it to the traversal order
			traversalOrder.enqueue(vertexQueue.dequeue());

			// If the end vertex is found, break out of the loop
			if (flag) {
				traversalOrder.enqueue(vertex.getLabel());
				break;
			}
		}

		return traversalOrder;
	}

	// Depth First Search
	public QueueInterface<T> getDepthFirstTraversal(T origin, T end) {
		// Reset the vertices
		resetVertices();

		// Traversal Order is the final result and rest of the stacks are being used for traversal
		QueueInterface<T> traversalOrder = new LinkedQueue<>();
		StackInterface<T> vertexStack = new LinkedStack<>();
		StackInterface<T> recoveryStack = new LinkedStack<>();

		// Push the origin vertex
		vertexStack.push(origin);

		// Visit the origin vertex
		VertexInterface<T> vertex = vertices.getValue(origin);
		vertex.visit();
		// Increment the number of vertices visited
		numberOfVerticesVisited++;

		// While the queue is not empty every vertex in the queue is being visited
		Boolean flag = false;
		while (!flag) {

			// Get the next vertex in the stack
			while ((vertex = vertices.getValue(vertexStack.peek()).getUnvisitedNeighbor()) != null) {
				// Visit the vertex
				vertex.visit();
				// Increment the number of vertices visited
				numberOfVerticesVisited++;
				// Push the vertex
				vertexStack.push(vertex.getLabel());

				// If the end vertex is found, break out of the loop
				if (vertex.getLabel().equals(end)) {
					flag = true;
					break;
				}
			}

			// If the end vertex is found, break out of the loop
			if (flag) {

				// Because data type is stack and we want to return queue, we need to reverse the order of the stack
				while (!vertexStack.isEmpty()) {
					recoveryStack.push(vertexStack.pop());
				}
				while (!recoveryStack.isEmpty()) {
					traversalOrder.enqueue(recoveryStack.pop());
				}
				break;
			} else { // If the end vertex is not found, pop the vertex
				vertexStack.pop();
			}
		}

		return traversalOrder;
	}

	// Shortest Path
	public int getShortestPath(T begin, T end, StackInterface<T> path) {
		// Reset the vertices
		resetVertices();

		// The queue is used to store the vertices
		QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<>();

		// Start and ending vertices
		VertexInterface<T> startVertex = vertices.getValue(begin);
		VertexInterface<T> endVertex = vertices.getValue(end);
		
		startVertex.visit();
		// Increment the number of vertices visited
		numberOfVerticesVisited++;

		// Adding the first vertex to the queue
		vertexQueue.enqueue(startVertex);

		// While the queue is not empty every vertex in the queue is being visited
		while (!vertexQueue.isEmpty()) {
			VertexInterface<T> frontVertex = vertexQueue.dequeue();
			Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
			while (neighbors.hasNext()) {
				VertexInterface<T> nextNeighbor = neighbors.next();
				if (1 + frontVertex.getCost() < nextNeighbor.getCost() || !nextNeighbor.isVisited()) {
					
					nextNeighbor.visit();
					// Increment the number of vertices visited
					numberOfVerticesVisited++;

					// Increasing the cost due to find the shortest path
					nextNeighbor.setCost(1 + frontVertex.getCost());
					nextNeighbor.setPredecessor(frontVertex);
					vertexQueue.enqueue(nextNeighbor);
				} 
			}
		}

		// Path cost to end vertex
		int pathLength = (int)endVertex.getCost();
		// Pushing the end vertex to the stack
		path.push(endVertex.getLabel());
		VertexInterface<T> vertex = endVertex;
		// Getting the path from the end vertex to the start vertex
		while (vertex.hasPredecessor()) {
			vertex = vertex.getPredecessor();
			path.push(vertex.getLabel());
		}

		return pathLength;
	}

	// Chepest Path
	public double getCheapestPath(T begin, T end, StackInterface<T> path) {
		// Reset the vertices
		resetVertices();

		// The queue is used to store the vertices
		QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<>();

		// Start and ending vertices
		VertexInterface<T> startVertex = vertices.getValue(begin);
		VertexInterface<T> endVertex = vertices.getValue(end);
		
		startVertex.visit();
		// Increment the number of vertices visited
		numberOfVerticesVisited++;

		// Adding the first vertex to the queue
		vertexQueue.enqueue(startVertex);

		// While the queue is not empty every vertex in the queue is being visited
		while (!vertexQueue.isEmpty()) {
			VertexInterface<T> frontVertex = vertexQueue.dequeue();
			Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
			Iterator<Double> weights = frontVertex.getWeightIterator();
			while (neighbors.hasNext() && weights.hasNext()) {
				VertexInterface<T> nextNeighbor = neighbors.next();
				double weight = weights.next();
				if (weight + frontVertex.getCost() < nextNeighbor.getCost() || !nextNeighbor.isVisited()) {
					
					nextNeighbor.visit();
					// Increment the number of vertices visited
					numberOfVerticesVisited++;

					// Increasing the cost due to find the shortest path
					nextNeighbor.setCost(weight + frontVertex.getCost());
					nextNeighbor.setPredecessor(frontVertex);
					vertexQueue.enqueue(nextNeighbor);
				}
			}
		}

		// Path cost to end vertex
		double pathLength = endVertex.getCost();
		// Pushing the end vertex to the stack
		path.push(endVertex.getLabel());
		VertexInterface<T> vertex = endVertex;
		// Getting the path from the end vertex to the start vertex
		while (vertex.hasPredecessor()) {
			vertex = vertex.getPredecessor();
			path.push(vertex.getLabel());
		}

		return pathLength;
	}

	// Returns the number of vertices visited
	public int getNumberOfVerticesVisited() {
		return numberOfVerticesVisited;
	}

	// Returns an integer adjacency matrix for the graph
	public void printAdjacencyMatrix() {
		int numberOfVertices = getNumberOfVertices();
		int[][] adjacencyMatrix = new int[numberOfVertices][numberOfVertices];

		Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
		for (int i = 0; i < numberOfVertices; i++) {
			VertexInterface<T> nextVertex = vertexIterator.next();
			Iterator<VertexInterface<T>> neighbors = nextVertex.getNeighborIterator();
			while (neighbors.hasNext()) {
				VertexInterface<T> neighbor = neighbors.next();
				int neighborIndex = findIndexOfVerticle(neighbor);
				adjacencyMatrix[i][neighborIndex] = 1;
			} // end while
		} // end for

		// Print the matrix
		Iterator<T> vertexLabelIterator = vertices.getKeyIterator();
		while (vertexLabelIterator.hasNext()) {
			System.out.print("\t(" + vertexLabelIterator.next() + ")");
		}
		System.out.println();

		vertexLabelIterator = vertices.getKeyIterator();
		for (int i = 0; i < numberOfVertices; i++) {
			System.out.print("(" + vertexLabelIterator.next() + ")\t");
			for (int j = 0; j < numberOfVertices; j++) {
				System.out.print(adjacencyMatrix[i][j] + "\t");
			}
			System.out.println();
		}
	} 

	// Find and returns given verticle index in dictionary
	public int findIndexOfVerticle(VertexInterface<T> verticle) {
		int index = 0;
		Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
		while (vertexIterator.hasNext()) {
			VertexInterface<T> nextVertex = vertexIterator.next();
			if (nextVertex.equals(verticle)) {
				return index;
			}
			index++;
		}
		return -1;
	}
	//############################################################################################################

	protected VertexInterface<T> findTerminal() {
		boolean found = false;
		VertexInterface<T> result = null;

		Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();

		while (!found && vertexIterator.hasNext()) {
			VertexInterface<T> nextVertex = vertexIterator.next();

			// If nextVertex is unvisited AND has only visited neighbors)
			if (!nextVertex.isVisited()) {
				if (nextVertex.getUnvisitedNeighbor() == null) {
					found = true;
					result = nextVertex;
				} // end if
			} // end if
		} // end while

		return result;
	} // end findTerminal

	// Used for testing
	public void displayEdges() {
		System.out.println("\nEdges exist from the first vertex in each line to the other vertices in the line.");
		System.out.println("(Edge weights are given; weights are zero for unweighted graphs):\n");
		Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
		while (vertexIterator.hasNext()) {
			((Vertex<T>) (vertexIterator.next())).display();
		} // end while
	} // end displayEdges

	private class EntryPQ implements Comparable<EntryPQ> {
		private VertexInterface<T> vertex;
		private VertexInterface<T> previousVertex;
		private double cost; // cost to nextVertex

		private EntryPQ(VertexInterface<T> vertex, double cost, VertexInterface<T> previousVertex) {
			this.vertex = vertex;
			this.previousVertex = previousVertex;
			this.cost = cost;
		} // end constructor

		public VertexInterface<T> getVertex() {
			return vertex;
		} // end getVertex

		public VertexInterface<T> getPredecessor() {
			return previousVertex;
		} // end getPredecessor

		public double getCost() {
			return cost;
		} // end getCost

		public int compareTo(EntryPQ otherEntry) {
			// Using opposite of reality since our priority queue uses a maxHeap;
			// could revise using a minheap
			return (int) Math.signum(otherEntry.cost - cost);
		} // end compareTo

		public String toString() {
			return vertex.toString() + " " + cost;
		} // end toString
	} // end EntryPQ
} // end DirectedGraph
