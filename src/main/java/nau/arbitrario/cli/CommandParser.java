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
    public boolean guiEnabled = false;

    /**
     * Parameter describes algorithm number and validates it with class nau.arbitrario.cli.CorrectAlgorithm
     * <p><ul>
     * <li>1 - OptimalTSP</li>
     * <li>2 - GreedyTSP</li>
     * <li>3 - MstTSP</li>
     * </ul></p>
     *
     * @see CorrectAlgorithm
     */
    @Parameter(names = {"--algorithm", "-al"}, validateWith = CorrectAlgorithm.class)
    Integer algorithmNumber;

    /**
     * Parameter if set will save result data to database
     */
    @Parameter(names = {"--save", "-s"}, description = "Save results to database for future operations")
    boolean resultSaved = false;

    /**
     * Parameter holds import file path to get values
     */
    @Parameter(names = {"--import", "-i"}, description = "Import data from external file")
    String importFilePath;

    /**
     * Boolean if set prints out help message
     */
    @Parameter(names = {"--help", "--usage"}, description = "Print this message", help = true)
    public boolean usagePrinted;

}
