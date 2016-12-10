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

package nau.magma.cli;

import com.beust.jcommander.ParameterException;
import com.sun.istack.internal.Nullable;
import nau.magma.IApplicationable;
import nau.magma.Util;
import nau.magma.travelling_salesman.Graph;
import nau.magma.travelling_salesman.HamiltonianCycle;
import nau.magma.travelling_salesman.OptimalTSP;
import nau.magma.travelling_salesman.VehicleRouting;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Class for command-line interface part of program.
 *
 * @author Maksym Tymoshyk
 * @version 1.0
 */
public class MagmaCLI implements IApplicationable {
  private Scanner scanner;

  private String username;
  private Integer algorithmNumber;
  private String filePath = null;
  private Boolean isResultDataSaved;

  private Graph data;
  private double result;

  private Logger log;

  public MagmaCLI() {
    scanner = new Scanner(System.in);
    log = Logger.getLogger(MagmaCLI.class.getName());
  }

  /**
   * Interface method to launch CLI part
   *
   * @see IApplicationable
   */
  public void run() {
    switch (algorithmNumber) {
      case 1:
        OptimalTSP first = new OptimalTSP();
        first.solveGraph(data);
        result = first.getBestDistance();
        break;
      case 2:
        VehicleRouting second = new VehicleRouting();
        second.solveGraph(data);
        result = second.getDistance();
        break;
      case 3:
        HamiltonianCycle third = new HamiltonianCycle();
        third.solveGraph(data);
        result = third.getDistance();
        break;
    }

    System.out.println("\nRESULT IS " + result + "\n");
    saveDataIfNeeded();
  }

  /**
   * Interface method to collect data from command-line parser
   *
   * @see IApplicationable
   * @see CommandParser
   */
  public void getData(CommandParser data) {
    try {

      System.out.print("Enter user name: ");
      username = scanner.nextLine();

      // file validation
      if (data.filePath != null && !data.filePath.isEmpty() && new File(data.filePath).isFile()) {
        this.filePath = data.filePath;
      } else {
        System.out.println("No data files found, switching to autofill mode.");
        this.filePath = null;
      }

      if (data.selectedAlgo == null) {
        System.out.print("Enter algorithm number (1-3): ");
        this.algorithmNumber = Integer.parseInt(scanner.nextLine());
      } else {
        this.algorithmNumber = data.selectedAlgo;
      }

      // algorithm validation
      new CorrectAlgorithm().validate("algorithm number", String.valueOf(this.algorithmNumber));

      this.isResultDataSaved = data.isSaved;

      // choose data getting method
      if (this.filePath == null) {
        this.data = getProblemDataFromConsole();
      } else {
        this.data = new Util().getProblemDataFromFilePath(this.filePath);
      }

    } catch (ParameterException e) {
      log.info(e.getMessage());
      System.exit(1);
    } catch (Exception e) {
      log.severe(e.getMessage());
    }
  }

  /**
   * Method to generate data from console input based on user pick in case no import file selected.
   *
   * @return {@link Graph}
   */
  @Nullable
  private Graph getProblemDataFromConsole() {
    Graph graph;
    try {
      System.out.print("Enter number of vertices: ");
      int n = Integer.parseInt(scanner.nextLine());
      graph = new Graph(n);
    } catch (InputMismatchException | NumberFormatException e) {
      graph = null;
      log.severe(e.getMessage());
    }
    return graph;
  }

  private void saveDataIfNeeded() {
    if (isResultDataSaved) {
      new Util().saveResultToDatabase(this.username, this.algorithmNumber, this.result);
    }
  }
}
