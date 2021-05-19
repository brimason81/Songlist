/**
	Author:		Brian Mason	
	Date:		September 15,2018
	Desc:		This code creates the Songlist class and all of its methods.  
*/

/*
	INTERESTING THINGS:
	The methods size() and remove() don't extend to Songlist Objects even though a Songlist is an ArrayList.  
		To have the same functionality of the (parent?) class, the methods need to be redefined in 
		the new class.
		
	I'm beginning to see the importance of creating my own classes:  I may have been able to 
		write a script with everything included - Arraylist, methods, main method, etc., but 
		creating a class enables me to have all the class variables viewable throughout the class and 
		makes the songlist Object reusable.
*/
import java.util.ArrayList; // why not have one class of arrays with all methods and capabilities?
import java.util.Scanner;
//import java.io.FileWriter;
import java.io.File;
//import java.io.PrintWriter;
import java.io.IOException;
//import java.sql.*;
public class Songlist {
	private ArrayList<String> _songlist;
	private String _textFile;
	//private String _textFileTwo;  // 1/31/2020
	//private int _index;
	//private String _song;
	
	// No Args Constructor
	Songlist() {
		this._songlist = new ArrayList<String>();
	}
	
	// Constructor w/ Parameter
	Songlist(String _textFile) {
		this._textFile = _textFile;
		this._songlist = new ArrayList<String>();
	}
	
	/**
	**/
	public void insertSong(String _song) {
		this._songlist.add(_song);
	}
	
	/**
	**/
	public void addSongsFromFile() {
		try {
			Scanner _input = new Scanner(new File(this._textFile));
			while (_input.hasNextLine()) {
				String _song = _input.nextLine();
				this._songlist.add(_song);
			}
		} catch (IOException _ex){
			System.out.println(_ex);
		}
	}
	
	
	/**
	
	*/
	public void takeSongOut(int _index) { // REWRITE METHOD TO ADD REMOVE SONGS AND ADD TO ANOTHER FILE 1/31/2020
		this._songlist.remove(_index);
		
	}
	
	/**
	
	public void takeSongOut(int _index, String _textFile) {
		try {
			FileWriter _moveSong = new FileWriter(this._textFile, true);
			_moveSong.write(this._songlist.get(_index) + "\n");
			_moveSong.close();
		} catch (IOException e) {
			System.out.println("Didn't work");
			e.printStackTrace();
		}
	}*/
	/*
		getter methods (and probably setters, too) need to be included because the Songlist 
		class won't inherit size() and get() from ArrayList
	*/
	
	/**
	*/
	public int size() {
		return this._songlist.size();
	}
	
	/**
	*/
	public String get(int _index) {
		String _song = this._songlist.get(_index);
		return _song;
	}
	
}