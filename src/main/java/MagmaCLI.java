import com.beust.jcommander.ParameterException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Maksym Tymoshyk on 11/6/16.
 */
public class MagmaCLI implements IApplicationable {
    private Scanner scanner;

    private String username;
    private Integer algorithmNumber;
    private String filePath = null;
    private Boolean isResultDataSaved;

    // data class of graph abstraction
    private Graph data;
    private double result;

    private Logger log;

    MagmaCLI() {
        scanner = new Scanner(System.in);
        log = Logger.getLogger(MagmaCLI.class.getName());
    }

    public void run() {
        switch (algorithmNumber) {
            case 1:
                OptimalTSP first = new OptimalTSP();
                first.solveGraph(data);
                result = first.getBestDistance();
                break;
            case 2:
                VehicleRouting second = new VehicleRouting();
                second.solveGraph(data);
                result = second.getDistance();
                break;
            case 3:
                HamiltonianCycle third = new HamiltonianCycle();
                third.solveGraph(data);
                result = third.getDistance();
                break;
        }

        System.out.println("\nRESULT IS " + result + "\n");
        saveDataIfNeeded();
    }

    public void getData(CommandParser data) {
        try {

            System.out.print("Enter user name: ");
            username = scanner.nextLine();

            // file validation
            if (data.filePath != null && !data.filePath.isEmpty() && new File(data.filePath).isFile()) {
                this.filePath = data.filePath;
            } else {
                System.out.println("No data files found, switching to autofill mode.");
                this.filePath = null;
//                throw new FileNotFoundException("File not found!");
            }

            if (data.selectedAlgo == null) {
                System.out.print("Enter algorithm number (1-3): ");
                this.algorithmNumber = Integer.parseInt(scanner.nextLine());
            } else {
                this.algorithmNumber = data.selectedAlgo;
            }

            // algorithm validation
            new CorrectAlgorithm().validate("algorithm number", String.valueOf(this.algorithmNumber));

            this.isResultDataSaved = data.isSaved;

            // choose data getting method
            if (this.filePath == null) {
                this.data = getProblemDataFromConsole();
            } else {
                this.data = new Util().getProblemDataFromFilePath(this.filePath);
            }

        } catch (ParameterException e) {
            log.info(e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            log.severe(e.getMessage());
        }
    }

    // only for this class usage in case no import file selected
    private Graph getProblemDataFromConsole() {
        Graph graph;
        try {
//            scanner.nextLine();
            System.out.print("Enter number of vertices: ");
            int n = Integer.parseInt(scanner.nextLine());
            graph = new Graph(n);
        } catch (InputMismatchException | NumberFormatException e) {
            graph = null;
            log.severe(e.getMessage());
        }
        return graph;
    }

    private void saveDataIfNeeded() {
        if (isResultDataSaved) {
            new Util().saveResultToDatabase(this.username, this.algorithmNumber, this.result);
        }
    }
}