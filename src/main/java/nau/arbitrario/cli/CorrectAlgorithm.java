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

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import nau.arbitrario.Settings;

/**
 * Class to validate algorithm number given as command-line parameter.
 *
 * @author Maksym Tymoshyk
 * @version 1.0
 * @see IParameterValidator
 */
public class CorrectAlgorithm implements IParameterValidator {
  /**
   * Checks parameter and its value to be in valid range
   *
   * @param name  parameter name
   * @param value value of parameter
   * @throws ParameterException if parameter does not fit right value
   */
  public void validate(String name, String value) throws ParameterException {
    try {
      final int MAX_ALGORITHM_NUMBER = Integer.valueOf(Settings.getInstance().getValue("validator.max_algorithm_number"));

      int n = Integer.parseInt(value);
      if (n < 1 || n > MAX_ALGORITHM_NUMBER)
        throw new ParameterException("Parameter " + name + " should be in range 1-" + MAX_ALGORITHM_NUMBER +
            " (found " + value + ") ");
    } catch (
        Exception e)

    {
      e.printStackTrace();
    }
  }
}
