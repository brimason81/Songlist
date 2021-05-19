import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InputHelper {
    
    public static String getInput(String prompt) {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        System.out.print(prompt);
        System.out.flush(); // clears input stream; no parameters

        try {
            return stdin.readLine();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    public static int getIntInput(String prompt) throws NumberFormatException { //Is thrown when trying to a String to numeric value but the String format won't allow; ex:  boolean, null
        String input = getInput(prompt);
        return Integer.parseInt(input);
    }
}