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

package nau.arbitrario.cli;

import nau.arbitrario.Util;
import nau.arbitrario.travelling_salesman.Graph;
import nau.arbitrario.travelling_salesman.GreedyTSP;
import nau.arbitrario.travelling_salesman.MstTSP;
import nau.arbitrario.travelling_salesman.OptimalTSP;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Class for command-line interface part of program.
 *
 * @author Maksym Tymoshyk
 * @version 1.0
 */
public class ArbitrarioCLI {
  private Scanner scanner;

  private String username;
  private Integer algorithmNumber;
  private Boolean resultSaved;

  private Graph data;
  private double result;

  private final Logger logger = Logger.getLogger(ArbitrarioCLI.class.getName());

  public ArbitrarioCLI() {
    System.out.println("Welcome to Magma CLI!");
    scanner = new Scanner(System.in);

    try {
      LogManager.getLogManager().readConfiguration(ArbitrarioCLI.class.getResourceAsStream("/config.properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method to launch CLI mode
   */
  public void run(CommandParser commandParser) {
    this.getData(commandParser);
    switch (algorithmNumber) {
      case 1:
        OptimalTSP first = new OptimalTSP();
        first.solveGraph(data);
        result = first.getBestDistance();
        break;
      case 2:
        GreedyTSP second = new GreedyTSP();
        second.solveGraph(data);
        result = second.getDistance();
        break;
      case 3:
        MstTSP third = new MstTSP();
        third.solveGraph(data);
        result = third.getDistance();
        break;
    }
    System.out.println("\nRESULT IS " + result + "\n");
    logger.info("Result value: " + result);
  }

  /**
   * Method to collect data from command-line parser
   *
   * @see CommandParser
   */
  private void getData(CommandParser data) {
    try {
      System.out.print("Enter user name: ");
      username = scanner.nextLine();
      logger.info("Username: " + username);

      // getting working data
      if (isFilePathValid(data.importFilePath)) {
        System.out.println("Found file " + data.importFilePath + "...");
        logger.info("File found: " + data.importFilePath);
        this.data = Util.getProblemDataFromFilePath(data.importFilePath);
      } else {
        System.out.println("No data files found, switching to semi-auto fill mode.");
        logger.info("No file found, auto mode.");
        this.data = getProblemDataFromConsole();
      }

      // getting algorithm number
      if (data.algorithmNumber == null) {
        // TODO: show number of algorithms in help message before any actions
        System.out.print("Enter algorithm number: ");
        this.algorithmNumber = Integer.parseInt(scanner.nextLine());
      } else {
        this.algorithmNumber = data.algorithmNumber;
      }
      new CorrectAlgorithm().validate("algorithm number", String.valueOf(this.algorithmNumber)); // pesky?

      // default = false
      this.resultSaved = data.resultSaved;
    } catch (Exception e) {
      logger.severe(e.getMessage());
      System.exit(1);
    }
  }

  private boolean isFilePathValid(String filePath) {
    return filePath != null && !filePath.isEmpty() && new File(filePath).isFile();
  }

  /**
   * Method to generate data from console input based on user pick in case no import file selected.
   *
   * @return {@link Graph}
   */
  @javax.annotation.Nullable
  private Graph getProblemDataFromConsole() {
    logger.info("Method entry.");
    boolean inputScanned = false;
    Graph graph = null;
    do {
      try {
        System.out.print("Enter number of vertices: ");
        int n = Integer.parseInt(scanner.nextLine());
        // TODO: make magic with "magic" numbers
        // TODO: make fully "automated" input mode with random vertices number
        // TODO: if input is abrupted make randomised graph
        if (n < 0 || n > 13) {
          throw new BigVerticesNumberException("Violated vertices number (got " + n + ")");
        }
        graph = new Graph(n);
        inputScanned = true;
      } catch (InputMismatchException | NumberFormatException | BigVerticesNumberException e) {
        logger.severe(e.getMessage());
      }
    } while (!inputScanned);
    logger.info("Method success.");
    return graph;
  }

//    private void saveDataIfNeeded() {
//        if (resultSaved) {
//            new Util().saveResultToDatabase(this.username, this.algorithmNumber, this.result);
//        }
//    }
}
