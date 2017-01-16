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
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class to handle GUI part.
 * Controller in JavaFx architecture.
 *
 * @author Maksym Tymoshyk
 * @version 1.0
 */
public class DataController implements Initializable {

  private MainProgramModel mainProgramModel = new MainProgramModel();
  private MagmaGUI app;
  // TODO: move to config file
  private ObservableList<String> algorithmList = FXCollections.observableArrayList("Optimal TSP", "Greedy TSP", "Mst TSP");

  @FXML
  private Button solveButton;

  @FXML
  private TextArea resultTextArea;

  @FXML
  private ChoiceBox<String> algorithmComboBox;

  @FXML
  private Button selectFileButton;

  @FXML
  private Button optionsButton;

  @FXML
  private Button resetAllButton;

  @FXML
  private Button helpButton;

  @FXML
  private TextField selectedFilePathTextField;

  // Called first when creating object, so it doesn't see any FXML notations
  // which are being populated right after that
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
    StringBuilder builder = new StringBuilder();
//    builder.append("User name: ").append(usernameField.getText()).append("\n");
    builder.append("Picked algorithm: ").append(algorithmComboBox.getSelectionModel().getSelectedItem()).append("\n");

    resultTextArea.appendText(builder.toString());

//    mainProgramModel.setUsername(usernameField.getText());
//    mainProgramModel.setIsResultSaved(checkbox.isSelected());
//
//    if (!spinner.isDisabled())
//      mainProgramModel.setSpinnerVertices((int) spinner.getValue());
//
    if(algorithmComboBox.getSelectionModel().isSelected(0))
//    if (algorithmComboBox.getSelectionModel().getSelectedItem().equals("traveling salesman")) {
//      mainProgramModel.setPickedAlgorithm(1);
//    } else if (algorithmComboBox.getSelectionModel().getSelectedItem().equals("vehicle routing")) {
//      mainProgramModel.setPickedAlgorithm(2);
//    } else if (algorithmComboBox.getSelectionModel().getSelectedItem().equals("Hamiltonian cycle")) {
//      mainProgramModel.setPickedAlgorithm(3);
//    }
    solveProblem();
//    resetStates();
  }

  @FXML
  private void handleHelpButton() {
    Stage helpWindow = new Stage();
    helpWindow.initModality(Modality.APPLICATION_MODAL);

    VBox vb = new VBox(new Button("OK"));
    helpWindow.setScene(new Scene(vb));
    helpWindow.show();

  }

  @FXML
  private void handleSelectFileButton() {
    FileChooser chooser = new FileChooser();
    File selectedFile = chooser.showOpenDialog(null);

    if (selectedFile != null) {
      selectedFilePathTextField.setText(selectedFile.getName());
      selectFileButton.setDisable(true);

      solveButton.setDisable(false);
      solveButton.setDefaultButton(true);
      solveButton.requestFocus();

//      mainProgramModel.setFilepath(selectedFile.getAbsolutePath());
    } else {
      selectedFilePathTextField.setText("File selection cancelled.");
    }
  }

  @FXML
  private void resetStates() {

    resultTextArea.clear();
    solveButton.setDisable(true);
    solveButton.setDefaultButton(false);
    selectFileButton.setDisable(false);
    selectFileButton.requestFocus();

    selectedFilePathTextField.setText("No file imported.");
    algorithmComboBox.getSelectionModel().select(0);
//    spinner.setDisable(false);
//    mainProgramModel.setFilepath(null);
  }

  @FXML
  private void handleSpinnerMouseEvents() {
//    importFileButton.setDisable(true);
//    solveButton.setDisable(false);
  }

  /**
   * Is called by the main application to give a reference back to itself.
   *
   * @param mainApp object of {@link javafx.application.Application}
   */
  public void setMainApp(MagmaGUI mainApp) {
    this.app = mainApp;
  }

  /**
   * Method calls corresponding algorithm realization to get solution while printing info in status area
   */
  private void solveProblem() {
//    Graph graph;
//    if (importFileButton.isDisabled()) {
//      graph = new Graph(mainProgramModel.getSpinnerVertices());
//      status.appendText("Random generated vertices mode\n");
//    } else {
//      graph = new Util().getProblemDataFromFilePath(mainProgramModel.getFilepath());
//      status.appendText("Import data from file mode\n");
//    }

//    switch (mainProgramModel.getPickedAlgorithm()) {
//      case 1:
//        OptimalTSP first = new OptimalTSP();
//        first.solveGraph(graph);
//        for (Edge e : first.printEdges(graph)) {
//          status.appendText(e.toString() + "\n");
//        }
//        mainProgramModel.setResult(first.getBestDistance());
//        break;
//      case 2:
//        GreedyTSP second = new GreedyTSP();
//        second.solveGraph(graph);
//        for (Edge e : second.getEdges(graph)) {
//          status.appendText(e.toString() + "\n");
//        }
//        mainProgramModel.setResult(second.getDistance());
//        break;
//      case 3:
//        MstTSP third = new MstTSP();
//        third.solveGraph(graph);
//        for (Edge e : third.getEdges(graph)) {
//          status.appendText(e.toString() + "\n");
//        }
//        mainProgramModel.setResult(third.getDistance());
//        break;
  }

  // Called after constructor and FXML notation population
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    algorithmComboBox.setItems(algorithmList);
    algorithmComboBox.getSelectionModel().select(0);

    selectedFilePathTextField.setEditable(false);
    selectedFilePathTextField.setDisable(true);
    selectedFilePathTextField.setText("No file imported.");

    resultTextArea.setEditable(false);

    solveButton.setDisable(true);
  }

  // after solving save to results.db if checked
//    if (mainProgramModel.isResultSaved()) {
//      new Util().saveResultToDatabase(mainProgramModel.getUsername(), mainProgramModel.getPickedAlgorithm(), mainProgramModel.getResult());
//      status.appendText("Successfully saved to database\n");
//    }
//
//    status.appendText("Result is " + mainProgramModel.getResult() + "\n");
//  }

}
