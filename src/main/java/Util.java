import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Maksym Tymoshyk on 11/9/16.
 */
public class Util {
    private Logger logger = Logger.getLogger(Util.class.getName());

    public Graph getProblemDataFromFilePath(String filePath) {
        List<String> found = new ArrayList<>();
        List<Edge> fileEdges = new ArrayList<>();

        // try-with-resources technique (java 1.7+)
        try (Scanner scanner = new Scanner(new File(filePath))) {
            String currentLine;
            Boolean startLineFound = false, endLineFound = false;
            while (scanner.hasNextLine()) {
                currentLine = scanner.nextLine();
                System.out.println(currentLine);

                if (startLineFound) {
                    if (!endLineFound) {
                        found.add(currentLine);
                    }
                }

                if (currentLine.startsWith("DATA"))
                    startLineFound = true;

                if (currentLine.startsWith("EOF"))
                    endLineFound = true;
            }
        } catch (Exception e) {
            logger.severe(e.getLocalizedMessage());
        }

        // delete last line containing EOF
        found.remove(found.size() - 1);
        for (String s : found) {
            String parsed[] = s.split(" ");
            logger.fine("Splitted: " + parsed[0] + " " + parsed[1] + " " + parsed[2]);
            fileEdges.add(new Edge(Integer.parseInt(parsed[0]), Integer.parseInt(parsed[1]), Double.parseDouble(parsed[2])));
        }
        Graph graph = new Graph(fileEdges.size());
        logger.fine("Created graph with size " + fileEdges.size());
        graph.updateGraph(Arrays.copyOf(fileEdges.toArray(), fileEdges.size(), Edge[].class));
        logger.fine("Filled with values");
        return graph;
    }

    public void saveResultToDatabase(String username, int algorithmNumber, double result) {
        try {
            Class.forName("org.sqlite.JDBC");
            String connection = "jdbc:sqlite:results.db";
            logger.info("Connection: " + connection);
            Connection c = DriverManager.getConnection(connection);
            c.setAutoCommit(false);
            logger.info("Opened database successfully");
            PreparedStatement stmt = c.prepareStatement("insert into sample(username, algo, result) values(?,?,?)");
            stmt.setString(1, username);
            stmt.setInt(2, algorithmNumber);
            stmt.setDouble(3, result);
            stmt.executeUpdate();
            logger.info("Records created successfully");

            c.commit();
            c.close();
        } catch (Exception e) {
            logger.severe(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
