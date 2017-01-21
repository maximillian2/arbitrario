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

/**
 * @author Dyangelo Grullon (dag4202)
 *         The class used to encapsulate edge data
 */
public class Edge implements Comparable<Edge> {

  private Integer row; // the first vertex
  private Integer col; // the second vertex
  private Double weight; //the distance between both points

  /**
   * Builder function for an nau.arbitrario.travelling_salesman.Edge type representing an edge in a graph
   *
   * @param row
   * @param col
   * @param weight
   */
  public Edge(int row, int col, double weight) {
    this.row = row;
    this.col = col;
    this.weight = weight;
  }

  @Override
  public int compareTo(Edge o) { //already documented
    int result = weight.compareTo(o.weight);
    if (result == 0) {
      result = row.compareTo(o.row);
      if (result == 0) {
        result = col.compareTo(o.col);
      }
    }
    return result;
  }

  /**
   * Retrieves the row of the edge in a matrix
   *
   * @return the first vertex of the edge
   */
  public int getRow() {
    return row;
  }

  /**
   * Retrieves the col of the edge in a matrix
   *
   * @return the second vertex of the edge
   */
  public int getCol() {
    return col;
  }

  /**
   * Retrieves the weight of the edge between both vertices
   *
   * @return the weight of the edge
   */
  public double getWeight() {
    return weight;
  }

  @Override
  public String toString() { //Already documented
    DecimalFormat df = new DecimalFormat("0.00");
    return String.valueOf(row) + " " + String.valueOf(col) + " weight = " + df.format(weight);

  }
}
