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

package nau.magma.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import nau.magma.Util;
import nau.magma.travelling_salesman.*;

import java.io.File;

/**
 * Class to handle GUI part.
 * Controller in JavaFx architecture.
 *
 * @author Maksym Tymoshyk
 * @version 1.0
 */
public class DataController {

  private Data dataModel = new Data();
  private SpinnerValueFactory<Integer> valueFactory;
  private MainApp app;
  private ObservableList<String> algorithms = FXCollections.observableArrayList("traveling salesman", "vehicle routing", "Hamiltonian cycle");

  @FXML
  private TextField usernameField;

  // Combobox holds algorithms' names from ObservableList
  @FXML
  private ComboBox comboBox;

  // Checkbox will save result to database if checked
  @FXML
  private CheckBox checkbox;

  // Text area that logs information while program is running
  @FXML
  private TextArea status;

  // Spinner used to choose number of vertices to generate data automatically a.k.a. auto mode
  @FXML
  private Spinner spinner;

  @FXML
  private Button importFileButton;

  @FXML
  private Button solveButton;

  public DataController() {
  }

  /**
   * Method handle solveButton onClick event,
   * collects information about user picks in GUI, stacks it in status variable.
   * Then calls solveProblem() to set result in model
   * and resetStates() to make GUI reusable
   */
  @FXML
  private void handleSolveProblem() {
    status.clear();
    StringBuilder builder = new StringBuilder();
    builder.append("User name: ").append(usernameField.getText()).append("\n");
    builder.append("Picked algorithm: ").append(comboBox.getSelectionModel().getSelectedItem()).append("\n");

    status.appendText(builder.toString());

    dataModel.setUsername(usernameField.getText());
    dataModel.setIsResultSaved(checkbox.isSelected());

    if (!spinner.isDisabled())
      dataModel.setSpinnerVertices((int) spinner.getValue());

    if (comboBox.getSelectionModel().getSelectedItem().equals("traveling salesman")) {
      dataModel.setPickedAlgorithm(1);
    } else if (comboBox.getSelectionModel().getSelectedItem().equals("vehicle routing")) {
      dataModel.setPickedAlgorithm(2);
    } else if (comboBox.getSelectionModel().getSelectedItem().equals("Hamiltonian cycle")) {
      dataModel.setPickedAlgorithm(3);
    }
    solveProblem();
    resetStates();
  }

  private void resetStates() {
    spinner.setDisable(false);
    valueFactory.setValue(1);
    importFileButton.setDisable(false);
    solveButton.setDisable(true);
    dataModel.setFilepath(null);
  }

  @FXML
  private void handleSpinnerMouseEvents() {
    importFileButton.setDisable(true);
    solveButton.setDisable(false);
  }

  @FXML
  private void handleImportEvents() {
    spinner.setDisable(true);
    solveButton.setDisable(false);


    FileChooser chooser = new FileChooser();
    File selectedFile = chooser.showOpenDialog(null);

    if (selectedFile != null) {
      status.setText("File selected: " + selectedFile.getName());
      dataModel.setFilepath(selectedFile.getAbsolutePath());
    } else {
      status.setText("File selection cancelled.");
    }
  }

  @FXML
  private void initialize() {
    valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 10, 1);
    spinner.setValueFactory(valueFactory);
    usernameField.setText("sample user");
    comboBox.setItems(algorithms);
    comboBox.getSelectionModel().selectFirst();
    resetStates();
  }

  /**
   * Is called by the main application to give a reference back to itself.
   *
   * @param mainApp object of {@link javafx.application.Application}
   */
  public void setMainApp(MainApp mainApp) {
    this.app = mainApp;
  }

  /**
   * Method calls corresponding algorithm realization to get solution while printing info in status area
   */
  private void solveProblem() {
    Graph graph;
    if (importFileButton.isDisabled()) {
      graph = new Graph(dataModel.getSpinnerVertices());
      status.appendText("Random generated vertices mode\n");
    } else {
      graph = new Util().getProblemDataFromFilePath(dataModel.getFilepath());
      status.appendText("Import data from file mode\n");
    }

    switch (dataModel.getPickedAlgorithm()) {
      case 1:
        OptimalTSP first = new OptimalTSP();
        first.solveGraph(graph);
        for (Edge e : first.printEdges(graph)) {
          status.appendText(e.toString() + "\n");
        }
        dataModel.setResult(first.getBestDistance());
        break;
      case 2:
        GreedyTSP second = new GreedyTSP();
        second.solveGraph(graph);
        for (Edge e : second.getEdges(graph)) {
          status.appendText(e.toString() + "\n");
        }
        dataModel.setResult(second.getDistance());
        break;
      case 3:
        MstTSP third = new MstTSP();
        third.solveGraph(graph);
        for (Edge e : third.getEdges(graph)) {
          status.appendText(e.toString() + "\n");
        }
        dataModel.setResult(third.getDistance());
        break;
    }

    // after solving save to results.db if checked
    if (dataModel.isResultSaved()) {
      new Util().saveResultToDatabase(dataModel.getUsername(), dataModel.getPickedAlgorithm(), dataModel.getResult());
      status.appendText("Successfully saved to database\n");
    }

    status.appendText("Result is " + dataModel.getResult() + "\n");
  }

}
