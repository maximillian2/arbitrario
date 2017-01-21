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

package nau.arbitrario.gui.model;

import javafx.beans.property.*;
import nau.arbitrario.travelling_salesman.Graph;

/**
 * Class that describes main window GUI values of parameters.
 * Model in JavaFx architecture.
 *
 * @author Maksym Tymoshyk
 * @version 1.5
 */
public class MainModel {
  private final DoubleProperty resultValue;
  private final IntegerProperty algorithmNumber;
  private final StringProperty filePath;
  private final ObjectProperty<Graph> data;

  public Graph getData() {
    return data.get();
  }

  public ObjectProperty<Graph> dataProperty() {
    return data;
  }

  public void setData(Graph data) {
    this.data.set(data);
  }

  public double getResultValue() {
    return resultValue.get();
  }

  public void setResultValue(double resultValue) {
    this.resultValue.set(resultValue);
  }

  public int getAlgorithmNumber() {
    return algorithmNumber.get();
  }

  public void setAlgorithmNumber(int algorithmNumber) {
    this.algorithmNumber.set(algorithmNumber);
  }

  public String getFilePath() {
    return filePath.get();
  }

  public void setFilePath(String filePath) {
    this.filePath.set(filePath);
  }

  // initialize variables to avoid NullPointer exception
  public MainModel() {
    this.algorithmNumber = new SimpleIntegerProperty(0);
    this.filePath = new SimpleStringProperty("");
    this.resultValue = new SimpleDoubleProperty(0);
    data = new SimpleObjectProperty<>();
  }


}
