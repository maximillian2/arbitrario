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

import javafx.beans.property.*;

/**
 * Created by Maksym Tymoshyk on 11/9/16.
 */

// JavaFx
// class (model) to hold values to be shown on screen and to be saved
public class Data {
    private final StringProperty username;
    private final BooleanProperty isResultSaved;
    private final DoubleProperty result;

    private final IntegerProperty pickedAlgorithm;
    private final IntegerProperty spinnerVertices;
    private final StringProperty inputData;
    private final StringProperty filepath;

    public double getResult() {
        return result.get();
    }

    public void setResult(double result) {
        this.result.set(result);
    }

    public int getPickedAlgorithm() {
        return pickedAlgorithm.get();
    }


    public void setPickedAlgorithm(int pickedAlgorithm) {
        this.pickedAlgorithm.set(pickedAlgorithm);
    }

    public String getFilepath() {
        return filepath.get();
    }


    public void setFilepath(String filepath) {
        this.filepath.set(filepath);
    }

    public int getSpinnerVertices() {
        return spinnerVertices.get();
    }


    public void setSpinnerVertices(int spinnerVertices) {
        this.spinnerVertices.set(spinnerVertices);
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public boolean isResultSaved() {
        return isResultSaved.get();
    }

    public void setIsResultSaved(boolean isResultSaved) {
        this.isResultSaved.set(isResultSaved);
    }

    public Data() {
        this.username = new SimpleStringProperty("sample user");
        this.isResultSaved = new SimpleBooleanProperty(false);
        this.pickedAlgorithm = new SimpleIntegerProperty(0);
        this.inputData = new SimpleStringProperty("");
        this.filepath = new SimpleStringProperty("");
        this.spinnerVertices = new SimpleIntegerProperty(0);
        this.result = new SimpleDoubleProperty(0);
    }


}
