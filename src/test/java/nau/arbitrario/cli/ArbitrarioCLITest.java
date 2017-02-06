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

package nau.arbitrario.cli;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ArbitrarioCLITest {

  private CommandParser parser;
  private ArbitrarioCLI cli;

  @Before
  public void setup() {
    parser = Mockito.mock(CommandParser.class);
    cli = Mockito.mock(ArbitrarioCLI.class);
  }

  @Test
  public void testRun() {
    parser.algorithmNumber = 2;
    Assert.assertEquals(2, parser.algorithmNumber.intValue());

    Mockito.when(cli.run(parser)).thenReturn(2.65);

    double result = cli.run(parser);
    Mockito.verify(cli).run(parser);

    Assert.assertEquals(2.65, result, 0.001);
  }
}
