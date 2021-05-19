/**
	ArrayList 
		trimToSize() - trims capacity to be the list's current size
		remove(int index) = removes element at specified position
		
		Without a database, adding to the Songlist only works while the program runs
			- how to write mySql code in a Java script.
			- how to import a .txt doc into MySql
		Can I make a Multidimensional ArrayList?
			- be able to select songs based on the artist - need db!
**/
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.sql.*;
public class SongsBackup {
	
	/**
	january 31, 2020
		
	*/
	public static void addSongToFile(String _song, String _file) {
		try {
			FileWriter _addSong = new FileWriter(_file, true);
			_addSong.write(_song + "\n");
			_addSong.close();
			//System.out.println("It Worked :)");
		} catch (IOException e) {
			System.out.println("Didn't work :( ");
			e.printStackTrace();
		}
	}
	
	public static void moveSong(String _song,  String _fileTo) {//String _fileFrom,
		try {
			FileWriter _moveSong = new FileWriter(_fileTo, true);
			_moveSong.write(_song + "\n");
			_moveSong.close();
		} catch (IOException e) {
			System.out.println("Didn't work");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) { 
		
	/**	//Loading Drivers
		Class.forName("JDBCDriverClass");
		// DB Connection:
		Connection _connection = DriverManager.getConnection
			("jdbc:mysql://localhost/repetoire", "root", ""); //what to type if no password?
	*/	
		String _songsToFile = "MacAndJuiceTunesOld.txt";
		String _songsFromFile = "MacAndJuiceTunes.txt";
		Songlist _tunes = new Songlist(_songsFromFile);
		_tunes.addSongsFromFile();
		
		// PROMPTS
		String _artistPrompt = "Please enter the artist name:  ";
		String _isNewSong = "Is there a new song(s) to add the list? ";
		String _newSong = "Please enter your new song: ";
		String _howManySongs = "Enter the number of songs you would like to work on this week: ";
		String _tooManySongs = "We don't have that many tunes.";
		
		System.out.print(_isNewSong);
		
		// OTHER VARIABLES
		Scanner _input = new Scanner(System.in);
		String _yesOrNo = _input.nextLine();
		
		while (_yesOrNo.toUpperCase().equals("YES")) {
			
					/*
			// FOR NOW, NOT WORRIED ABOUT STORING SONGS WITH ARTISTS IN DB
			System.out.println(_artistPrompt);
			String _artistName = _input.nextLine(); 
			// CODE TO USE _artistName	TO ACCESS COLUMN IN DB
						*/
			
			// ADD SONG TO SONGLIST & FILE
			System.out.println(_newSong);
			String _song = _input.nextLine();
			addSongToFile(_song, _songsFromFile);
			// If the song is already in the DB
			for (int i = 0; i < _tunes.size(); i++) {
				if (_song.equals(_tunes.get(i))) {
					System.out.println("That song is already in the Database"); // Still puts song into db
					break;
				} 
			}	
			_tunes.insertSong(_song);
			System.out.print(_isNewSong);
			_yesOrNo = _input.nextLine();
		}
		// Shows list after songs are added
		for (int j = 0; j < _tunes.size(); j++) {
			System.out.println(_tunes.get(j));
		}
		System.out.println("---------");
		
		System.out.print(_howManySongs);
		int _numOfSongs = _input.nextInt();
		if (_numOfSongs > _tunes.size()){
			System.out.println(_tooManySongs);
		} else {
			for (int i = 0; i < _numOfSongs; i++) {
				int _randoSong = (int)(Math.random() * _tunes.size());
				System.out.println(_tunes.get(_randoSong));
				moveSong(_tunes.get(_randoSong), _songsToFile);
			}
		}
		// to test that the songs are subtracted from the Songlist
		System.out.println("----------");
		for (int i = 0; i < _tunes.size(); i++) {
			System.out.println(_tunes.get(i));
		}
		
		
		
		
	
	}
}

/**
 *  SAVED PROGRESS FROM 7/4/2020
/**
	ArrayList 
		trimToSize() - trims capacity to be the list's current size
		remove(int index) = removes element at specified position
		
		Without a database, adding to the Songlist only works while the program runs
			- how to write mySql code in a Java script.
			- how to import a .txt doc into MySql
			- create user prompts for new artists 
				--statements to create variables for artist - put songs into correct columns
		Can I make a Multidimensional ArrayList?
			- be able to select songs based on the artist - need db!
			
		What is the package key word?
**/

/*
6/18/2020 - DB CONNECTION WORKED!!!

run command w/ classpath:

java -cp "C:\xampp\htdocs\Songlist\libs\mysql-connector-java-5.1.21-bin.jar"; Songs
*/

/*
	7/1/2020
	used PreparedStatment to accept user input and pull data from DB - use to randomly pull number of songs from DB.
*/

import java.util.Scanner;

//import java.sql.Statement;
//import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Songs {
	
	private static final String SQL = 
		"SELECT * FROM artists LIMIT ?";
	//private static final String SIZE = 
	//	"SELECT * FROM artists";
	public static void main(String[] args) throws SQLException{ 
		
		// PROMPTS - USE PREPARED STATEMENTS??
		String _artistPrompt = "Please enter the artist name:  ";
		String _isNewSong = "Is there a new song(s) to add the list? ";
		String _newSong = "Please enter your new song: ";
		String _howManySongs = "Enter the number of songs you would like to work on this week: ";
		String _tooManySongs = "We don't have that many tunes.";


		
		int _numOfSongs;
		//int _size;
		try {
			_numOfSongs = InputHelper.getIntInput(_howManySongs); // WRONG PROMPT FOR ARTIST ID, BUT WORKS FOR NOW
		} catch (NumberFormatException e) {
			System.err.println("error:  Invalid Number");
			return;  // fix flow of program so _numOfSongs doesn't need to be initialized
		}
		ResultSet rs = null;
		try (
			Connection conn = DBUtil.getConnection(DBType.MYSQL);
			PreparedStatement stmt = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			//PreparedStatement stmt2 = conn.prepareStatement(SIZE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		) {
			//stmt2.setInt(1, _numOfSongs);
			stmt.setInt(1, _numOfSongs);
			rs = stmt.executeQuery();//"SELECT * FROM artists"
			System.out.println("You've finally done it!!!");
			Artists.displayData(rs); //Each table has own class
		} catch (SQLException e) {
			DBUtil.processException(e);
		} 
		
		String _songsFromFile = "MacAndJuiceTunes.txt";
		Songlist _tunes = new Songlist(_songsFromFile);
		//Songlist _tunes = new Songlist(SIZE);
		_tunes.addSongsFromFile();
		
		System.out.print(_isNewSong);
		
		// OTHER VARIABLES
		Scanner _input = new Scanner(System.in);
		String _yesOrNo = _input.nextLine();
		
		while (_yesOrNo.toUpperCase().equals("YES")) {
			// FOR NOW, NOT WORRIED ABOUT STORING SONGS WITH ARTISTS IN DB
			/*
			System.out.println(_artistPrompt);
			String _artistName = _input.nextLine(); 
			*/
			// CODE TO USE _artistName	TO ACCESS COLUMN IN DB
			System.out.println(_newSong);
			String _song = _input.nextLine();
			// If the song is already in the DB
			for (int i = 0; i < _tunes.size(); i++) {
				if (_song.equals(_tunes.get(i))) {
					System.out.println("That song is already in the Database"); // Still puts song into db
					break;
				} else {
					//_statement.executeUpdate
						//("INSERT INTO artists('ARTIST') VALUE ('_song') WHERE ArtistName = '_OTHER_VARIABLE' ");
				} //ELSE STATEMENT W/ SQL COMMAND MAY NOT WORK
			}	
			_tunes.insertSong(_song);
			System.out.print(_isNewSong);
			_yesOrNo = _input.nextLine();
		}
		// Shows list after songs are added
		for (int j = 0; j < _tunes.size(); j++) {
			System.out.println(_tunes.get(j));
		}
		System.out.println("---------");
		
		System.out.print(_howManySongs);
		//int _numOfSongs = _input.nextInt();  7/1 CHANGE FOR PREPARED STATEMENT
		/*if (_numOfSongs > _tunes.size()){
			System.out.print(_tooManySongs);
		} else {
			for (int i = 0; i < _numOfSongs; i++) {
				int _randoSong = (int)(Math.random() * _tunes.size());
				System.out.println(_tunes.get(_randoSong));
				_tunes.takeSongOut(_randoSong);
			}
		} 
		if (_numOfSongs > _size) {
			System.out.println(_tooManySongs);
		} else {
			for (int x = 0; x < _numOfSongs; x++) {
				int _randoSong = (int)(Math.random() * _size);
				System.out.println(_tunes.get(_randoSong));
			}
		}*/
		// to test that the songs are subtracted from the Songlist
		System.out.println("----------");
		for (int i = 0; i < _tunes.size(); i++) {
			System.out.println(_tunes.get(i));
		}
		
		
		
		
	
	}
}*/