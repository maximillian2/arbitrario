import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

/**
 * Created by Maksym Tymoshyk on 11/6/2016.
 */
public class CorrectAlgorithm implements IParameterValidator {
    public void validate(String name, String value) throws ParameterException{
        final int MAX_ALGORITHM_NUMBER = 3;

        int n = Integer.parseInt(value);
        if(n < 1 || n > MAX_ALGORITHM_NUMBER)
            throw new ParameterException("Parameter " + name + " should be in range 1-" + MAX_ALGORITHM_NUMBER +
            " (found " + value + ") ");
    }
}
