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

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.logging.Logger;

/**
 * Class describes methods that are used by several parts of program
 *
 * @author Maksym Tymoshyk
 */
public class Util {
  private Logger logger = Logger.getLogger(Util.class.getName());

  /**
   * Parses data from file
   *
   * @param filePath - absolute or relative file path
   * @return Graph
   * @see Graph
   */
  public Graph getProblemDataFromFilePath(String filePath) {
    List<String> found = new ArrayList<>();
    List<Edge> fileEdges = new ArrayList<>();

    // try-with-resources technique (java 1.7+)
    try (Scanner scanner = new Scanner(new File(filePath))) {
      String currentLine;
      Boolean startLineFound = false, endLineFound = false;
      while (scanner.hasNextLine()) {
        currentLine = scanner.nextLine();
        System.out.println(currentLine);

        if (startLineFound) {
          if (!endLineFound) {
            found.add(currentLine);
          }
        }

        if (currentLine.startsWith("DATA"))
          startLineFound = true;

        if (currentLine.startsWith("EOF"))
          endLineFound = true;
      }
    } catch (Exception e) {
      logger.severe(e.getLocalizedMessage());
    }

    // delete last line containing EOF
    found.remove(found.size() - 1);
    for (String s : found) {
      String parsed[] = s.split(" ");
      logger.fine("Splitted: " + parsed[0] + " " + parsed[1] + " " + parsed[2]);
      fileEdges.add(new Edge(Integer.parseInt(parsed[0]), Integer.parseInt(parsed[1]), Double.parseDouble(parsed[2])));
    }
    Graph graph = new Graph(fileEdges.size());
    logger.fine("Created graph with size " + fileEdges.size());
    graph.updateGraph(Arrays.copyOf(fileEdges.toArray(), fileEdges.size(), Edge[].class));
    logger.fine("Filled with values");
    return graph;
  }

  /**
   * Saves necessary result data to database
   *
   * @param username        - current program's user name
   * @param algorithmNumber - used algorithm number
   * @param result          - float result value
   * @see Connection
   * @see PreparedStatement
   */
  public void saveResultToDatabase(String username, int algorithmNumber, double result) {
    try {
      Class.forName("org.sqlite.JDBC");
      String connection = "jdbc:sqlite:results.db";
      logger.info("Connection: " + connection);
      Connection c = DriverManager.getConnection(connection);
      c.setAutoCommit(false);
      logger.info("Opened database successfully");
      PreparedStatement stmt = c.prepareStatement("insert into sample(username, algo, result) values(?,?,?)");
      stmt.setString(1, username);
      stmt.setInt(2, algorithmNumber);
      stmt.setDouble(3, result);
      stmt.executeUpdate();
      logger.info("Records created successfully");

      c.commit();
      c.close();
    } catch (Exception e) {
      logger.severe(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }
}
