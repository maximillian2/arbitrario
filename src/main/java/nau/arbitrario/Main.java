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

package nau.arbitrario;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import nau.arbitrario.cli.ArbitrarioCLI;
import nau.arbitrario.cli.CommandParser;
import nau.arbitrario.gui.ArbitrarioGUI;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Initial class
 *
 * @author Maksym Tymoshyk
 * @see JCommander
 */
public class Main {
  private String[] args;
  private final Logger logger = Logger.getLogger(Main.class.getName());

  public Main(String[] args) {
    this.args = args;
    try {
      LogManager.getLogManager().readConfiguration(Main.class.getResourceAsStream("/config.properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    for(String s : args) {
      System.out.println(s);
    }
    new Main(args).startProgram();
  }

  private void startProgram() {
    logger.info("Program start");
    try {
      CommandParser cp = new CommandParser();
      JCommander jc = new JCommander(cp, args);
      jc.setProgramName("arbitrario");
      logger.info("Name set");

      if (cp.usagePrinted) {
        jc.usage();
        return;
      }

      if (cp.guiEnabled) {
        // launches JavaFx application
        logger.info("GUI mode");
        System.out.println("GUI mode");
//        javafx.application.Application.launch(ArbitrarioGUI.class);
      } else {
        // launches CLI application
        logger.info("CLI mode");
        System.out.println("CLI mode");
//        new ArbitrarioCLI().run(cp);
      }
      // catches exceptions on console parameter setting stage
    } catch (ParameterException e) {
      logger.severe(e.getMessage());
      e.printStackTrace();
    }

  }
}
