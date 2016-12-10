/*
 * MIT License
 *
 * Copyright (c) 2016 Maksym Tymoshyk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.text.DecimalFormat;

/**
 * @author Dyangelo Grullon (dag4202)
 * Part 3 of the Traveling Salesman Problem. 
 * Determines an approximation of an optimal path using a minimum spanning tree
 * derived from prim's algorithm.
 */
public class HamiltonianCycle {
	private static DecimalFormat df = new DecimalFormat("0.00");

	public double getDistance() {
		return Double.valueOf(new DecimalFormat("#.##").format(distance));
	}

	private double distance;
	/**
	 * @author Dyangelo Grullon
	 *
	 * Private helper class to implement a priority queue using a binary heap
	 */
	private static class PriorityQueue{
		private Vertex heap[]; //the actual pq
		private int N; //the number of elements in the pq
		private int qp[]; //the array of vertex locations in pq (heap[])
		
		/**
		 * Constructor for the priority queue class
		 * @param N size of priority queue
		 */
		public PriorityQueue(int N){
			this.heap = new Vertex[N + 1];
			this.N = N;
			this.qp = new int[N + 1];
			heap[0] = null;
			for (int i = 1; i <=N; i++){ 
				heap[i] = new Vertex(i);
				qp[i] = i;
			}
			qp[0] = -1;
		}
		
		/**
		 * Private helper method that preforms sink operations on the priority queue
		 * @param k the position of the element in the priority queue
		 */
		private void sink(int k){
			while(2*k <= N){
				int j = 2 * k;
				if(j < N && less(j, j+1)) j++;
				if(!less(k, j)) break;
				exch(k, j);
				k = j;
			}
		}
		
		/**
		 * Determines if an element in the priority queue has less priority than
		 * another
		 * @param i The element in question
		 * @param j The other
		 * @return
		 */
		private boolean less(int i, int j){
			return heap[i].compareTo(heap[j]) == -1; 
		}
		
		/**
		 * Swaps two elements in the priority queue
		 * @param i
		 * @param j
		 */
		private void exch(int i, int j){
			Vertex temp = heap[i];
			heap[i] = heap[j];
			heap[j] = temp;
			qp[heap[i].id] = i;
			qp[heap[j].id] = j;
		}
		
		/**
		 * Deletes and returns the element with the highest priority in the pq
		 * @return The vertex with the lowest priority (weight)
		 */
		public Vertex delMin(){
			Vertex min = heap[1];
			exch(1, N--);
			sink(1);
			heap[N + 1] = null;
			qp[min.id] = -1;
			return min;
		}
		
		/**
		 * Assures that the heap is in proper format
		 */
		public void heapify(){
			int k = N/2;
			while (k > 0) sink(k--);
		}
		
		/**
		 * Determines if the priority queue is empty
		 * @return a boolean, true if the qp is empty, false otherwise
		 */
		public boolean isEmpty(){
			return N == 0;
		}
		
		/**
		 * Determines if a vertex is in the priority queue
		 * @param v The vertex id
		 * @return a boolean, true if the vertex is in the qp, false otherwise
		 */
		public boolean inPQ(int v){
			return qp[v] != -1;
		}
		
		/**
		 * Gets the priority (weight) of a vertex
		 * @param v the vertex id
		 * @return The weight of the vertex
		 */
		public double getPriority(int v){
			return heap[qp[v]].weight;
		}
		
		/**
		 * Modifies a vertex with a new priority (weight) and a new parent vertex id
		 * @param v The vertex id
		 * @param weight The new priority (weight)
		 * @param parent The new parent vertex id
		 */
		public void updateVertex(int v, double weight, int parent){
			heap[qp[v]].weight = weight;
			heap[qp[v]].parent = parent;
		}
		
	}

	public Edge[] getEdges(Graph graph) {
		return  graph.getEdges();
	}

	public void solveGraph(Graph graph){
		long start = System.currentTimeMillis(); // start measuring time starting at prims
		PriorityQueue pq = new PriorityQueue(graph.getN() - 1); //instantiate the priority queue
		Vertex[] vertices = new Vertex[graph.getN()]; //stores the vertices for future reference
		int[] vPos = new int[graph.getN()]; //stores the positions of each vertex with id in index
		Vertex u = new Vertex(0); //creates the first vertex, 0
		Graph mst = new Graph(graph.getN()); //instantiates the mst
		int count = 1; 
		vertices[0] = u;
		vPos[0] = 0;
		double total = 0.0;
		do {
			for(int v = 1; v < graph.getN(); v++){ //for all neighbors (completely connected graph)
				if (v == u.id) continue; //except itself
				if (pq.inPQ(v)){ //assure that the vertex in the pq
					double weight = graph.getWeight(u.id, v); //get the weight between neighbor and vertex
					if (weight < pq.getPriority(v)){ //if the weight is less than priority at v
						pq.updateVertex(v, weight, u.id); //update the vertex and weight of v
					}
				}
			}
			pq.heapify(); //heapify the pq
			u = pq.delMin(); //grab the highest priority element in pq
			mst.updateGraph(u); //add it to the mst
			vPos[u.id] = count; //store and save position in vertices array
			vertices[count++] = u;
			total += mst.getWeight(u.id, u.parent); //update the mst total weight
		} while (!pq.isEmpty()); //continue doing this until pq is empty
		long end = System.currentTimeMillis(); //stop measuring time, prim is done
		long elapsed = end - start;
		if(graph.getN() <= 10){  //print all info needed
			System.out.print("Minimum Spanning Tree:");
			mst.printMatrix();
			System.out.printf("Total weight of mst: %s\n\n", df.format(total));
			System.out.println("Pre-order traversal: ");
		}
		start = System.currentTimeMillis(); //after printing is done, measure again
		int[] path = mst.DFS(0); //use dfs to get the preorder path
		distance = 0.0;
		end = System.currentTimeMillis(); //stop measuring once dfs is done
		for (int i = 0; i < graph.getN(); i++){
			if (graph.getN() <= 10)System.out.println(vertices[vPos[path[i]]].toString());
			distance += graph.getWeight(path[i], path[i+1]);
		}
		if (graph.getN() <= 10) System.out.println();
		elapsed += end - start;
		System.out.printf("Distance using mst: %s for path ", df.format(distance));
		for (int i = 0; i <= graph.getN() ; i++){
			System.out.printf("%d ", path[i]);
		}

	}
}
