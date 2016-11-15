import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

/*
 * Created by Maksym Tymoshyk on 11/6/16.
 */
public class Main {
    public static void main(String[] args) {
       try {
           System.out.println("Welcome to MAGMA Problem Solver! ");
           CommandParser cp = new CommandParser();
           JCommander jc = new JCommander(cp, args);
           jc.setProgramName("magma");

           if (cp.help) {
               jc.usage();
               return;
           }

           if (cp.isGUI) {
               // launch JavaFx application
               MagmaGUI gui = new MagmaGUI();
               gui.getData(cp);
               gui.run();
           } else {
               // launch CLI application
               MagmaCLI cli = new MagmaCLI();
               cli.getData(cp);
               cli.run();
           }
           // catches exceptions on console parameter setting stage
       } catch (ParameterException e) {
           System.out.println(e.getMessage());
       }
    }
}
