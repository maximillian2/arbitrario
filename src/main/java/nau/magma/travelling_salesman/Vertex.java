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

package nau.magma.travelling_salesman;

/**
 * The class used to encapsulate vertex data
 *
 * @author Dyangelo Grullon
 */
public class Vertex implements Comparable<Vertex> {
  public int id; //the id of the vertex
  public Double weight; //the weight between parent and vertex id
  public int parent; //the parent of the vertex
  public boolean leftToRight;

  /**
   * The builder method for a nau.magma.travelling_salesman.Vertex object. Instantiates weight to infinity
   * and parent to -1
   *
   * @param id The vertex id to assign
   */
  public Vertex(int id) {
    this.id = id;
    this.weight = Double.POSITIVE_INFINITY;
    this.parent = -1;
  }

  @Override
  public int compareTo(Vertex o) { //already documented
    return o.weight.compareTo(this.weight);
  }

  @Override
  public String toString() { //already documented
    return "Parent of " + String.valueOf(id) + " is " + String.valueOf(parent);

  }

}