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

import com.beust.jcommander.Parameter;

/**
 * Class that describes command-line keys and associates keys to variables.
 *
 * @author Maksym Tymoshyk
 * @version 1.0
 * @see Parameter
 */
public class CommandParser {
  /**
   * Boolean if set launches GUI mode
   */
  @Parameter(names = "--gui", description = "Launch with graphical interface")
  public boolean isGUI = false;

  /**
   * Parameter describes algorithm number and validates it with class nau.magma.cli.CorrectAlgorithm
   *
   * @see CorrectAlgorithm
   */
  @Parameter(names = {"--algo", "-al"}, description = "Selected algorithm to be solved with: " +
      "1 -> Travelling Salesman; " + "2 -> Vehicle Routing; " + "3 -> Hamiltonian Cycle",
      validateWith = CorrectAlgorithm.class)
  public Integer selectedAlgo;

  /**
   * Parameter if set will save result data to database
   */
  @Parameter(names = "--save", description = "Save results to database for future operations")
  public boolean isSaved = false;

  /**
   * Parameter holds import file path to get values
   */
  @Parameter(names = {"--import", "-i"}, description = "Import input data from external file")
  public String filePath;

  /**
   * Boolean if set prints out help message
   */
  @Parameter(names = {"--help", "--usage"}, description = "Print this message", help = true)
  public boolean help;

}