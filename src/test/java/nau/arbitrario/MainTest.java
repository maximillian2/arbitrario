/*
 * MIT License
 *
 * Copyright (c) 2017 Maksym Tymoshyk
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

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class MainTest {
  private Launcher launcher;

  @Before
  public void setup() {
    launcher = Mockito.mock(Launcher.class);
  }

  @Test
  public void runWithHelpArgument() {
    String[] helpArg = new String[]{"--help"};

    int result = launcher.run(helpArg);
    Mockito.verify(launcher).run(helpArg);
  }

  @Test
  public void runWithArguments() {
    String[] sampleArgs = new String[]{"-al", "2"};

    int result = launcher.run(sampleArgs);
    Mockito.verify(launcher).run(sampleArgs);
  }

  @Test
  public void runWithoutArguments() {
    String[] emptyArgs = new String[]{};

    int result = launcher.run(emptyArgs);
    Mockito.verify(launcher).run(emptyArgs);
  }
}
