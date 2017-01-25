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

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * JavaFX class that describes user auth window GUI values of parameters.
 *
 * @author Maksym Tymoshyk
 * @version 1.0
 */

// TODO: make authorization window
public class AuthModel {
  private final StringProperty userName;
  private final StringProperty institutionAbbrv;
  private final IntegerProperty groupNumber;

  public String getUserName() {
    return userName.get();
  }

  public StringProperty userNameProperty() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName.set(userName);
  }

  public String getInstitutionAbbrv() {
    return institutionAbbrv.get();
  }

  public StringProperty institutionAbbrvProperty() {
    return institutionAbbrv;
  }

  public void setInstitutionAbbrv(String institutionAbbrv) {
    this.institutionAbbrv.set(institutionAbbrv);
  }

  public int getGroupNumber() {
    return groupNumber.get();
  }

  public IntegerProperty groupNumberProperty() {
    return groupNumber;
  }

  public void setGroupNumber(int groupNumber) {
    this.groupNumber.set(groupNumber);
  }


  public AuthModel() {
    userName = new SimpleStringProperty("");
    groupNumber = new SimpleIntegerProperty(0);
    institutionAbbrv = new SimpleStringProperty("");
  }
}
