import com.beust.jcommander.Parameter;
import com.beust.jcommander.internal.Lists;

import java.util.List;

/*
 * Created by Maksym Tymoshyk on 11/6/2016.
 */
public class CommandParser {
    @Parameter
    public List<String> parameters = Lists.newArrayList();

    @Parameter(names = "--gui", description = "Launch with graphical interface")
    public boolean isGUI = false;

    @Parameter(names = { "--algo", "-al"}, description = "Selected algorithm to be solved with:\n" +
                                                         "\t1 -> Travelling Salesman; " +
                                                         "2 -> Vehicle Routing; " +
                                                         "3 -> Hamiltonian Cycle",
                                                         validateWith = CorrectAlgorithm.class)
    public Integer selectedAlgo;

    @Parameter(names = "--save", description = "Save results to database for future operations")
    public boolean isSaved = false;

    // make validation class for files
    @Parameter(names = {"--import", "-i"}, description = "Import input data from external file")
    public String filePath;

    @Parameter(names = {"--help", "--usage"}, description = "Print this message", help = true)
    public boolean help;

}
