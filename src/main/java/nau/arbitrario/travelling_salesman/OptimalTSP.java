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

package nau.arbitrario.travelling_salesman;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * @author Dyangelo Grullon (dag4202)
 *         Part 1 of the Travelling Salesman Problem. Obtains the optimal path of a completely connected euclidean graph
 *         by finding all permutations of possible paths and determining the lowest distance of all paths
 */
public class OptimalTSP {
  private static DecimalFormat df = new DecimalFormat("0.00");
  private double bestDistance;

  public StringBuilder getBuilder() {
    return builder;
  }

  private StringBuilder builder;

  public OptimalTSP() {
    builder = new StringBuilder();
  }
  /**
   * Permutation algorithm which determines the next lexicographically ordered permutation of a path
   *
   * @param permutation an array of integers representing a particular path
   * @return the next permutation
   */
  static void nextPermutation(int[] permutation) {
    int j = permutation.length - 1;
  /*
   * Finds the first element from the end of 'permutation' where the element to its right
	 * is less than or equal to the element. Stores the index of the element to the right of said
	 * element to 'j'.
	 */
    while (j > 0 && permutation[j - 1] >= permutation[j]) {
      j--;
    }
	
	/*
	 * Finds the first element from the end of 'permutation' that is smaller than the element found
	 * in the last loop. Stores its index at k.
	 */
    int k = permutation.length - 1;
    while (permutation[j - 1] >= permutation[k]) {
      k--;
    }
	
	/*
	 * Swaps these two elements
	 */
    int swap = permutation[j - 1];
    permutation[j - 1] = permutation[k];
    permutation[k] = swap;
	
	/*
	 * Reverses the subset to the right of the first element found (permutation[j-1]) by
	 * iterating from both ends (j to the end of the array) to each other and consistently
	 * swapping elements
	 */
    k = permutation.length - 1;
    while (k > j) {
      swap = permutation[k];
      permutation[k] = permutation[j];
      permutation[j] = swap;
      j++;
      k--;
    }
  }

  /**
   * Given a path, a completely connected euclidean graph and the number of vertices,
   * computes the total distance of the path
   *
   * @param path  is the path excluding vertex 0 on both ends of the path
   * @param graph the representation of a completely connected connected euclidean graph
   * @param N     the number of vertices in the graph
   */
  static double computeDistance(int[] path, Graph graph, int N) {
    double sum = graph.getWeight(0, path[0]); // the sum to be returned is initialized with a base value
    // of the distance between 0 and the first element of path
    int pos; //the position in the path
    if (N <= 5) { //if the number of vertices in the graph is less than or equal to 5, then print a formatted
      //representation of the path and its distance

      System.out.printf("Path: 0 %d", path[0]);
      for (pos = 1; pos < path.length; pos++) {
        sum += graph.getWeight(path[pos - 1], path[pos]); // Accumulates the distance between all elements in path
        //with the previous element in path
        System.out.printf(" %d", path[pos]);
      }
      sum += graph.getWeight(path[pos - 1], 0); //increments the total sum with the distance between the last vertex
      //in path and 0
      System.out.print(" 0  ");
      System.out.printf("distance = %s\n", df.format(sum));
      return sum;
    } else {
      //does the same thing as above but without print statements
      for (pos = 1; pos < path.length; pos++) {
        sum += graph.getWeight(path[pos - 1], path[pos]);
      }
      sum += graph.getWeight(path[pos - 1], 0);
    }
    return sum;
  }

  public Edge[] printEdges(Graph graph) {
    for (Edge e : graph.getEdges()) {
      System.out.println(e.toString());
    }

    return graph.getEdges();
  }

  public void solveGraph(Graph graph) {
    int[] permutations = new int[graph.getN() - 1]; // a new array to represent a path without vertex 0
    int total = 1; //the base value to determine the total number of permutaions.
    //The variable 'total' represents (N-1)!
    for (int i = 0; i < graph.getN() - 1; i++) { //the loop to both create the first path [1,2,3...N-1]) and calculate the number
      //of permutations
      permutations[i] = i + 1;
      total = total * (i + 1);
    }
    if (graph.getN() <= 10) { //if the number of vertices is less than 10, then print the vertices and the matrix
      graph.printVertices();
      graph.printMatrix();
    }

    printEdges(graph);
    long start = System.currentTimeMillis(); //record start time of the permutations algorithm
    double distance = computeDistance(permutations, graph, graph.getN());  //compute distance of first path
    double best = distance; //determine base first path to be the total distance of the path 0-1-2-...-(N-1)-0
    int[] bestPath = Arrays.copyOf(permutations, permutations.length); //stores best path in an array called bestPath
    for (int i = 0; i < total - 1; i++) { //for every possible permutation
      nextPermutation(permutations); //compute next permutation by changing 'permutations'
      distance = computeDistance(permutations, graph, graph.getN()); //compute the total distance of the path that was just
//															computed
      if (distance < best) { //if the recently calculated distance is lower than the recorded best distance:
        best = distance; //set the new best distance
        bestPath = Arrays.copyOf(permutations, permutations.length);//copy the new best path
      }
    }

    System.out.printf("\nOptimal distance: %s for path 0", df.format(best));
    builder.append("\nOptimal distance: ").append(best).append(" for path 0\n");
    setBestDistance(best);

    for (int i = 0; i < graph.getN() - 1; i++) {
      System.out.printf(" %d", bestPath[i]);
      builder.append(" ").append(bestPath[i]);
    }
    System.out.println(" 0");
    builder.append(" 0\n");
    long end = System.currentTimeMillis();
    System.out.printf("Runtime for optimal TSP   : %d milliseconds\n\n", end - start);
    builder.append("Runtime for optimal TSP   : ").append(end-start).append(" milliseconds\n\n");
  }

  public double getBestDistance() {
    return Double.valueOf(new DecimalFormat("#.##").format(bestDistance));
  }

  public void setBestDistance(double bd) {
    bestDistance = bd;
  }
}
