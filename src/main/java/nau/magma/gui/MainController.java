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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nau.magma.Util;
import nau.magma.travelling_salesman.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class to handle GUI part.
 * Controller in JavaFx architecture.
 *
 * @author Maksym Tymoshyk
 * @version 1.5
 */
public class MainController implements Initializable {

  private MainModel mainModel = new MainModel();
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
  public MainController() {
  }

  /**
   * Method handle solveButton onClick event,
   * collects information about user picks in GUI, stacks it in status variable.
   * Then calls solveProblem() to set result in model
   * and resetStates() to make GUI reusable
   */
  @FXML
  private void handleSolveProblem() {
    resultTextArea.appendText("Using " + algorithmComboBox.getSelectionModel().getSelectedItem() + " algorithm\n");

    /*
     * 0 - Optimal TSP
     * 1 - Greedy TSP
     * 2 - Mst TSP
     * TODO: add fourth from pack
     */
    mainModel.setAlgorithmNumber(algorithmComboBox.getSelectionModel().getSelectedIndex() + 1);
    solveProblem();
  }

  /**
   * Method calls corresponding algorithm realization to get solution while printing info in status area
   */
  private void solveProblem() {
    Graph graph = new Util().getProblemDataFromFilePath(selectedFilePathTextField.getText());
    resultTextArea.appendText("Import data from file mode\n");

    switch (mainModel.getAlgorithmNumber()) {
      case 1:
        OptimalTSP first = new OptimalTSP();
        first.solveGraph(graph);
        for (Edge e : first.printEdges(graph)) {
          resultTextArea.appendText(e.toString() + "\n");
        }
        mainModel.setResultValue(first.getBestDistance());
        break;
      case 2:
        GreedyTSP second = new GreedyTSP();
        second.solveGraph(graph);
        for (Edge e : second.getEdges(graph)) {
          resultTextArea.appendText(e.toString() + "\n");
        }
        mainModel.setResultValue(second.getDistance());
        break;
      case 3:
        MstTSP third = new MstTSP();
        third.solveGraph(graph);
        for (Edge e : third.getEdges(graph)) {
          resultTextArea.appendText(e.toString() + "\n");
        }
        mainModel.setResultValue(third.getDistance());
        break;
    }

    resultTextArea.appendText("RESULT: " + mainModel.getResultValue() + "\n");
  }

  @FXML
  private void handleHelpButton() {
    HelpController helpWindow = new HelpController();
    helpWindow.showHelp();
    selectFileButton.requestFocus();
  }

  @FXML
  private void handleSelectFileButton() {
    FileChooser chooser = new FileChooser();
    File selectedFile = chooser.showOpenDialog(solveButton.getScene().getWindow());

    if (selectedFile != null) {
      selectedFilePathTextField.setText(selectedFile.getAbsolutePath());
      selectFileButton.setDisable(true);

      solveButton.setDisable(false);
      solveButton.setDefaultButton(true);
      solveButton.requestFocus();

      // TODO: save file data not path (it's useless)
      mainModel.setFilePath(selectedFile.getAbsolutePath());
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
  }

  /**
   * Is called by the main application to give a reference back to itself.
   * <p>
   * //   * @param mainApp object of {@link javafx.application.Application}
   */
  // TODO: wtf this working while commented
  public void setMainApp(MagmaGUI mainApp) {
    this.app = mainApp;
  }

  // This method is called by the FXMLLoader when initialization is complete
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
}
