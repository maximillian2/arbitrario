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

package nau.arbitrario.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import nau.arbitrario.Settings;
import nau.arbitrario.Util;
import nau.arbitrario.gui.ArbitrarioGUI;
import nau.arbitrario.gui.model.MainModel;
import nau.arbitrario.travelling_salesman.Edge;
import nau.arbitrario.travelling_salesman.GreedyTSP;
import nau.arbitrario.travelling_salesman.MstTSP;
import nau.arbitrario.travelling_salesman.OptimalTSP;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * JavaFX class to handle GUI part.
 *
 * @author Maksym Tymoshyk
 * @version 1.5
 */
public class MainController implements Initializable {

  private MainModel mainModel = new MainModel();
//  private ArbitrarioGUI app;
  private ObservableList<String> algorithmList = FXCollections.observableArrayList();
  private final Logger logger = Logger.getLogger(MainController.class.getName());

  @FXML // fx:id="resultTextArea"
  private TextArea resultTextArea; // Value injected by FXMLLoader

  @FXML // fx:id="solveButton"
  private Button solveButton; // Value injected by FXMLLoader

  @FXML // fx:id="algorithmComboBox"
  private ChoiceBox<String> algorithmComboBox; // Value injected by FXMLLoader

  @FXML // fx:id="selectedFilePathTextField"
  private TextField selectedFilePathTextField; // Value injected by FXMLLoader

  @FXML // fx:id="selectFileButton"
  private Button selectFileButton; // Value injected by FXMLLoader

  @FXML // fx:id="optionsButton"
  private Button optionsButton; // Value injected by FXMLLoader

  @FXML // fx:id="resetAllButton"
  private Button resetAllButton; // Value injected by FXMLLoader

  @FXML // fx:id="helpButton"
  private Button helpButton; // Value injected by FXMLLoader


  // Called first when creating object, so it doesn't see any FXML notations
  // which are being populated right after that
  public MainController() {
    try {
      LogManager.getLogManager().readConfiguration(MainController.class.getResourceAsStream("/config.properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    algorithmList.addAll(Settings.getInstance().getValue("main.algorithms").split(", "));
    logger.info("Read algorithms from properties file.");
  }

  /**
   * Method handle solveButton onClick event,
   * collects information about user picks in GUI, stacks it in status variable.
   * Then calls solveProblem() to set result in model
   * and resetStates() to make GUI reusable
   */
  @FXML
  private void handleSolveProblem() {
    logger.info("Button clicked.");

    resultTextArea.appendText("Using " + algorithmComboBox.getSelectionModel().getSelectedItem() + " algorithm\n");
    logger.info("Combobox: " + algorithmComboBox.getSelectionModel().getSelectedItem());
    /*
     * 0 - Optimal TSP
     * 1 - Greedy TSP
     * 2 - Mst TSP
     * TODO: add fourth from pack
     */
    mainModel.setAlgorithmNumber(algorithmComboBox.getSelectionModel().getSelectedIndex() + 1);
    logger.info("Model algorithm number: " + mainModel.getAlgorithmNumber());
    solveProblem();
  }

  /**
   * Method calls corresponding algorithm realization to get solution while printing info in status area
   */
  private void solveProblem() {
    mainModel.setData(Util.getProblemDataFromFilePath(mainModel.getFilePath()));
    logger.info("File data set success.");
    resultTextArea.appendText("Import data from file mode\n");

    switch (mainModel.getAlgorithmNumber()) {
      case 1:
        OptimalTSP first = new OptimalTSP();
        first.solveGraph(mainModel.getData());
        for (Edge e : first.printEdges(mainModel.getData())) {
          resultTextArea.appendText(e.toString() + "\n");
        }
        resultTextArea.appendText(first.getBuilder().toString());
        mainModel.setResultValue(first.getBestDistance());
        break;
      case 2:
        GreedyTSP second = new GreedyTSP();
        second.solveGraph(mainModel.getData());
        for (Edge e : second.getEdges(mainModel.getData())) {
          resultTextArea.appendText(e.toString() + "\n");
        }
        resultTextArea.appendText(second.getBuilder().toString());
        mainModel.setResultValue(second.getDistance());
        break;
      case 3:
        MstTSP third = new MstTSP();
        third.solveGraph(mainModel.getData());
        for (Edge e : third.getEdges(mainModel.getData())) {
          resultTextArea.appendText(e.toString() + "\n");
        }
        resultTextArea.appendText(third.getBuilder().toString());
        mainModel.setResultValue(third.getDistance());
        break;
    }

    resultTextArea.appendText("RESULT: " + mainModel.getResultValue() + "\n");
    logger.info("Result value: " + mainModel.getResultValue());
  }

  @FXML
  private void handleHelpButton() {
    logger.info("Button clicked.");
    HelpController helpWindow = new HelpController();
    helpWindow.showHelp();
    logger.fine("Close success.");
    selectFileButton.requestFocus();
  }

  @FXML
  private void handleSelectFileButton() {
    logger.info("Button clicked.");
    FileChooser chooser = new FileChooser();
    File selectedFile = chooser.showOpenDialog(solveButton.getScene().getWindow());

    if (selectedFile != null) {
      logger.info("Successfully selected file: " + selectedFile);
      selectedFilePathTextField.setText(selectedFile.getAbsolutePath());
      selectFileButton.setDisable(true);

      solveButton.setDisable(false);
      solveButton.setDefaultButton(true);
      solveButton.requestFocus();

      // TODO: validate selected file
      mainModel.setFilePath(selectedFile.getAbsolutePath());
      logger.info("Model file path: " + mainModel.getFilePath());
    } else {
      logger.warning("File selection cancelled.");
      selectedFilePathTextField.setText("File selection cancelled.");
    }
  }

  @FXML
  private void resetStates() {
    logger.info("Method called.");
    resultTextArea.clear();
    solveButton.setDisable(true);
    solveButton.setDefaultButton(false);
    selectFileButton.setDisable(false);
    selectFileButton.requestFocus();

    selectedFilePathTextField.setText("No file imported.");
    algorithmComboBox.getSelectionModel().select(0);
    logger.fine("Reset states success.");
  }

//  /**
//   * Is called by the main application to give a reference back to itself.
//   * <p>
//   * //   * @param mainApp object of {@link javafx.application.Application}
//   */
//  public void setMainApp(ArbitrarioGUI mainApp) {
//    this.app = mainApp;
//  }

  // This method is called by the FXMLLoader when initialization is complete
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    logger.info("Method called.");
    algorithmComboBox.setItems(algorithmList);
    algorithmComboBox.getSelectionModel().select(0);

    selectedFilePathTextField.setEditable(false);
    selectedFilePathTextField.setDisable(true);
    selectedFilePathTextField.setText("No file imported.");

    resultTextArea.setEditable(false);

    solveButton.setDisable(true);

    assert resultTextArea != null : "fx:id=\"resultTextArea\" was not injected: check your FXML file 'mainWindow.fxml'.";
    assert solveButton != null : "fx:id=\"solveButton\" was not injected: check your FXML file 'mainWindow.fxml'.";
    assert algorithmComboBox != null : "fx:id=\"algorithmComboBox\" was not injected: check your FXML file 'mainWindow.fxml'.";
    assert selectedFilePathTextField != null : "fx:id=\"selectedFilePathTextField\" was not injected: check your FXML file 'mainWindow.fxml'.";
    assert selectFileButton != null : "fx:id=\"selectFileButton\" was not injected: check your FXML file 'mainWindow.fxml'.";
    assert optionsButton != null : "fx:id=\"optionsButton\" was not injected: check your FXML file 'mainWindow.fxml'.";
    assert resetAllButton != null : "fx:id=\"resetAllButton\" was not injected: check your FXML file 'mainWindow.fxml'.";
    assert helpButton != null : "fx:id=\"helpButton\" was not injected: check your FXML file 'mainWindow.fxml'.";
    logger.fine("Initialize success.");
  }
}
