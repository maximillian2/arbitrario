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

    public DoubleProperty resultProperty() {
        return result;
    }

    public void setResult(double result) {
        this.result.set(result);
    }

    public int getPickedAlgorithm() {
        return pickedAlgorithm.get();
    }

    public IntegerProperty pickedAlgorithmProperty() {
        return pickedAlgorithm;
    }

    public void setPickedAlgorithm(int pickedAlgorithm) {
        this.pickedAlgorithm.set(pickedAlgorithm);
    }

    public String getFilepath() {
        return filepath.get();
    }

    public StringProperty filepathProperty() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath.set(filepath);
    }

    public void setInputData(String inputData) {
        this.inputData.set(inputData);
    }

    public int getSpinnerVertices() {
        return spinnerVertices.get();
    }

    public IntegerProperty spinnerVerticesProperty() {
        return spinnerVertices;
    }

    public void setSpinnerVertices(int spinnerVertices) {
        this.spinnerVertices.set(spinnerVertices);
    }

    public StringProperty getInputData() {
        return inputData;
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
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
