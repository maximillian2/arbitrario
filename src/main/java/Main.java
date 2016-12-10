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

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

/**
 * Initial class
 *
 * @author Maksym Tymoshyk
 * @see JCommander
 */
public class Main {
  public static void main(String[] args) {
    try {
      System.out.println("Welcome to MAGMA Problem Solver! ");
      CommandParser cp = new CommandParser();
      JCommander jc = new JCommander(cp, args);
      jc.setProgramName("magma");

      if (cp.help) {
        jc.usage();
        return;
      }

      if (cp.isGUI) {
        // launches JavaFx application
        MagmaGUI gui = new MagmaGUI();
        gui.getData(cp);
        gui.run();
      } else {
        // launches CLI application
        MagmaCLI cli = new MagmaCLI();
        cli.getData(cp);
        cli.run();
      }
      // catches exceptions on console parameter setting stage
    } catch (ParameterException e) {
      System.out.println(e.getMessage());
    }
  }
}
