import java.io.FileWriter;
import java.io.IOException;
//import java.util.Scanner;
public class TestWriter {
	public static void main(String[] args) {
		
		try {
			FileWriter _test = new FileWriter("MacAndJuiceTunes.txt", true);
			// ^ ADDING TRUE TO THE CONSTRUCTOR DID IT!!
			_test.write("Rosanna, Toto" +  "\n");
			_test.close();
			System.out.println("Ya did it!");
			
		} catch (IOException e) {
			System.out.println("Nope :(");
			e.printStackTrace();
		}
		
		
	}
}