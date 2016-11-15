import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Created by Maksym Tymoshyk on 11/9/16.
 */
public class DataController {

    private Data dataModel = new Data();
    private SpinnerValueFactory<Integer> valueFactory;
    private MainApp app;
    private ObservableList<String> algorithms = FXCollections.observableArrayList("traveling salesman", "vehicle routing", "Hamiltonian cycle");

    @FXML
    private TextField usernameField;

    @FXML
    private ComboBox comboBox;

    @FXML
    private CheckBox checkbox;

    @FXML
    private TextArea status;

    @FXML
    private Spinner spinner;

    @FXML
    private Button importFileButton;

    @FXML
    private Button solveButton;

    public DataController() {
    }

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
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.app = mainApp;
    }

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
                VehicleRouting second = new VehicleRouting();
                second.solveGraph(graph);
                for (Edge e : second.getEdges(graph)) {
                    status.appendText(e.toString() + "\n");
                }
                dataModel.setResult(second.getDistance());
                break;
            case 3:
                HamiltonianCycle third = new HamiltonianCycle();
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
