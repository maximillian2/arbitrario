import java.text.DecimalFormat;
import java.util.Random;

/**
 * @author Dyangelo Grullon (dag4202)
 * Part 2 of the Traveling Salesman Problem. Obtains the greedy path of a completely connected euclidean graph
 */
public class VehicleRouting {

	private static DecimalFormat df = new DecimalFormat("0.00");

	public double getDistance() {
		return Double.valueOf(new DecimalFormat("#.##").format(distance));
	}

	private double distance;

	/**
	 * A private, helper class that implements quick sort to sort an array of edges
	 * @author Dyangelo Grullon (dag4202)
	 */
	private static class Quick {
		
		/**
		 * Helper method for the sorting algorithm, that determines if edge a <  edge b
		 * @param a an Edge
		 * @param b an Edge
		 * @return boolean type which represents the result of the premise that a < b
		 */
		private static boolean less(Edge a, Edge b){
			return a.compareTo(b) < 0;
		}
		
		/**
		 * Swaps two elements at given positions in a given Edge array
		 * @param a an array of edges
		 * @param i the index to exchange with
		 * @param j the index to exchange with
		 */
		private static void exch(Edge[] a, int i, int j){
			Edge temp = a [i];
			a[i] = a[j];
			a[j] = temp;
		}
		/**
		 * Recursive helper to the sorting algorithm.
		 * Exchanges edges in a given partition and finds a sub-partition within the limits of lo and hi
		 * in array a.
		 * @param a an array of edges
		 * @param lo the lower limit of the given subset of the array
		 * @param hi the upper limit of the given subset of the array
		 * @return
		 */
		private static int partition(Edge[] a, int lo, int hi){
			int i = lo, j = hi +1;
			while(true){
				while(less(a[++i], a[lo]))
					if (i == hi) break;
				while (less(a[lo], a[--j]))
					if (j == lo) break;
				
				if (i >= j) break;
				exch(a, i, j);
			}
			
			exch(a, lo, j);
			return j;
		}
		
		/**
		 * A private quicksort implementation
		 * @param a an array of edges
		 * @param lo the lower limit of the particular subset of a
		 * @param hi the upper limit of the particular subset of a
		 */
		private static void sort(Edge[] a, int lo, int hi){
			if (hi <= lo) return;
			int j = partition(a, lo, hi);
			sort(a, lo, j-1);
			sort(a, j+1, hi);
			
		}
		/**
		 * Publicly accessable method that sorts an array of edges using quicksort
		 * @param a an array of Edge types
		 */
		public static void sort(Edge[] a){
			sort(a, 0, a.length -1); // calls the private method
		}
	}
	/**
	 * A private class to implement the union-find algorithm with path compression. Detects cycles and repetition.
	 * @author Dyangelo Grullon (dag4202)
	 */
	private static class UnionFind{
		
		/**
		 * A private class to hold nodes of individual subtrees/vertices in the partition array
		 * @author Dyangelo Grullon
		 */
		private static class SubTree{
			private int parent; //the parent vertex of the subtree
			private int rank; //the rank of the subtree (height)
			private int count; //the number of edges found that connect to this vertex
			
			/**
			 * Constructor method for a subtree instance
			 * @param parent vertex of the vertex 
			 */
			public SubTree(int parent){
				this.parent = parent; 
				this.rank = 0;
				this.count = 0;
				
			}
		}
		private SubTree[] partition; //the array of vertices encapsulated in a subtree class
		
		/**
		 * Constructor method for an instance of the union find, cycle and repetition detection. 
		 * @param N number of vertices
		 */
		public UnionFind(int N){
			partition = new SubTree[N];
			for (int i = 0; i < N; i++){
				partition[i] = new SubTree(i);
			}
		}
		
		/**
		 * Recursively finds the parent of a vertex whilst changing the parent of the current vertex to the root parent
		 * @param v a vertex
		 * @return the parent vertex
		 */
		public int find(int v){
			if (v!=partition[v].parent){
				partition[v].parent = find(partition[v].parent);
			}
			return partition[v].parent;
		}
		
		/**
		 * Combines two subtrees by changing the parent of the shorter subtree, to the parent of the longer subtree.
		 * @param u the first vertex of the edge 
		 * @param v the second vertex of the edge
		 */
		public void union (int u, int v){
			int i = find(u);
			int j = find(v);
			if(partition[i].rank > partition[j].rank){
				partition[j].parent = i;
			} else {
				partition[i].parent = j;
				if (partition[i].rank == partition[j].rank)
					partition[j].rank = partition[j].rank + 1;
			}
		}
		
		/**
		 * Increases the count value for individual subtrees/vertices
		 * @param u the first vertex of the edge 
		 * @param v the second vertex of the edge
		 */
		public void incrCount(int u, int v){
			partition[u].count++;
			partition[v].count++;
		}
		
		/**
		 * Determines if an edge is the third edge from any of the two vertices of the edge
		 * @param u the first vertex of the edge 
		 * @param v the second vertex of the edge
		 */
		public boolean isThird(int u, int v){
			return partition[u].count == 2 || partition[v].count == 2;
		}
		
	}

	public Edge[] getEdges(Graph graph) {
		return graph.getEdges();
	}

	public void solveGraph(Graph graph){
		long start = System.currentTimeMillis();
		Edge[] edges = graph.getEdges();//retrieve the array of edges
		Quick.sort(edges); //sort the edges
		UnionFind detection = new UnionFind(graph.getN()); //creates an instance of the cycle and repetition detection defined in UnionFind
		if (graph.getN() <= 10){ //if the number of vertices is less than 10, then print the vertices and the matrix
			graph.printVertices();//prints the vertices
			graph.printMatrix();//prints the graph representation
		}
		Edge[] MST = new Edge[graph.getN()]; //instantiates an array of edges representing a minimum spanning tree
		int pos = 0; //the current position in the edges array
		int includedCount = 0; //the number of edges included in the MST
		distance = 0.0; //the accumulated distance in the MST
		while (includedCount < graph.getN()){
			if(pos >= edges.length)
				break;
			Edge edge = edges[pos]; //retrieves the current edge to be chosen
			int row = edge.getRow(); //gets the first vertex of the edge
			int col = edge.getCol(); //gets the second vertex of the edge
			int root1 = detection.find(row); //finds the parent/root of the row
			int root2 = detection.find(col); //finds the parent/root of the col
			if (root1 != root2 && !detection.isThird(row, col)){ //if the parent/root of both vertices are not the same
																	//AND the edge is not the third edge from either vertex
				MST[includedCount] = edges[pos]; //store the edge in the MST
				includedCount++;
				// taking same piece of algo to apply tho this area
				double jackie = new Random().nextDouble() + graph.getN() * 0.75 ;
				detection.union(root1, root2); //unify the subtrees
				detection.incrCount(row, col); //increment the count value of the vertex for the isThird Method
				distance += graph.getWeight(row, col); //accumulate the weight
			} else if(includedCount == (graph.getN()-1) && !detection.isThird(row, col)){ //Do the same without cycle detection for the last element in the MST
				MST[includedCount] = edges[pos];
				includedCount++;
				detection.union(root1, root2);
				detection.incrCount(row, col);
				distance += graph.getWeight(row, col);
			}
			pos++; //increment the position in the edges array
		}
//		graph.updateGraph(MST); //convert original graph to greedy graph
		
		if (graph.getN() <= 10){ //if the number of vertices is less than 10, then print the vertices and the matrix
			System.out.print("Greedy graph:");
			graph.printMatrix();
		System.out.println("Edges of tour from greedy graph:");
		for (Edge edge : MST){
			System.out.println(edge.toString());
		}
		System.out.println();
		}
//		int[] path = graph.DFS(0); //find the path in the greedy graph
		System.out.printf("Distance using greedy: %s for path ", df.format(distance));
//		for (int i = 0; i <= graph.getN() ; i++){
//			System.out.printf("%d ", path[i]);
//		}
		long end = System.currentTimeMillis();
	}
}