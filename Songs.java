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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Songs {
	
	private static final String SQL = 
		"SELECT * FROM artists LIMIT ?";
	private static final String COUNT = 
		"SELECT * FROM artists";
	public static void main(String[] args) throws SQLException{  // ust Exception??
		
		// PROMPTS - USE PREPARED STATEMENTS??
		String _artistInfoPrompt = "Would you like to search Artist info?  Please enter Yes or No: ";
		String _isNewArtistPrompt = "Is there a new Artist to add?  Please enter Yes or No: ";
		String _updateArtistPrompt = "Would you like to update an Artist in the DB?  Please enter Yes or No: ";
		String _deleteArtistPrompt = "Would you like to remove an Artist from the DB?  Yes or No: ";
		String _isNewSong = "Is there a new song(s) to add the list? ";
		String _newSong = "Please enter your new song: ";
		String _howManySongs = "Enter the number of songs you would like to work on this week: ";
		String _tooManySongs = "We don't have that many tunes.";


		Songlist _tunes = new Songlist();
		int _numOfSongs;
		
		try {
			_numOfSongs = InputHelper.getIntInput(_howManySongs); 
		} catch (NumberFormatException e) {
			System.err.println("error:  Invalid Number");
			return;  // fix flow of program so _numOfSongs doesn't need to be initialized
		}
		ResultSet rs = null;
		ResultSet rs2 = null;
		try (
			Connection conn = DBUtil.getConnection(DBType.MYSQL);
			PreparedStatement stmt = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			PreparedStatement count_stmt = conn.prepareStatement(COUNT, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
		) {
			stmt.setInt(1, _numOfSongs);
			rs = stmt.executeQuery();
			rs2 = count_stmt.executeQuery();
			System.out.println("You've finally done it!!!");
			Artists.displayData(rs); //Each table has own class
			_tunes = Artists.Songlist(rs2);
		} catch (SQLException e) {
			DBUtil.processException(e);
		} 
		
		for (int j = 0; j < _tunes.size(); j++) {
			System.out.println(_tunes.get(j));
		}
			
		System.out.println("---------");
		
		//Random Song Selection
		System.out.println("This week's tunes: ");
		for (int x = 0; x < _numOfSongs; x++) {
			int _randoSong = (int)(Math.random() * _tunes.size());
			System.out.println(_tunes.get(_randoSong));
			_tunes.takeSongOut(_randoSong);
		}

		System.out.println("---------");
		System.out.println("Songlist after remove(): ");
		for (int j = 0; j < _tunes.size(); j++) {
			System.out.println(_tunes.get(j));
		}
		
		// CODE TO GET SINGLE ROW OF INFO FROM artists:
		String _artistInfo = InputHelper.getInput(_artistInfoPrompt);
		if (_artistInfo.equals("Yes")) {
			int _artistId = InputHelper.getIntInput("Select a row: ");

			Artist _bean = ArtistsManager.getRow(_artistId);

			if (_bean == null) {
			System.err.println("No rows were found.");
		} else {
			System.out.println("Artist Id: " + _bean.getArtistId());
			System.out.println("Artist Name: " + _bean.getArtistName());
			System.out.println("Artist Song: " + _bean.getArtistSong());	
			}
		}
		
		
		// CODE TO INSERT NEW ARTIST INFO INTO artists:
		// CREATE IF STATEMENT
		String isArtist = InputHelper.getInput(_isNewArtistPrompt);
		if (isArtist.equals("Yes")) {
			Artist _notherBean = new Artist();
			_notherBean.setArtistName(InputHelper.getInput("Enter Artist Name: "));
			_notherBean.setArtistSong(InputHelper.getInput("Enter Artist Song: "));

			boolean result = ArtistsManager.insertArtist(_notherBean);

			if (result) {
				System.out.println("New Row With Primary Key " + _notherBean.getArtistId() + " was inserted");
			} 
		}
		
		// CODE TO UPDATE INFO IN artists:
		String _updateArtist = InputHelper.getInput(_updateArtistPrompt);
		if (_updateArtist.equals("Yes")) {
			int _artistId = InputHelper.getIntInput("Please enter Artist Id: ");
			Artist _beanName = ArtistsManager.getRow(_artistId);
			Artist _beanSong = ArtistsManager.getRow(_artistId);
			if (_beanName == null) {
				System.err.println("Row not found.");
				return;
			}

			String _artistName = InputHelper.getInput("Enter NEW artist name: ");
			String _artistSong = InputHelper.getInput("Enter NEW artist song: ");
			_beanName.setArtistName(_artistName);
			_beanSong.setArtistSong(_artistSong);

			if ((ArtistsManager.updateArtist(_beanName)) && (ArtistsManager.updateArtist(_beanSong))){
				System.out.println("Success");
			} else {
				System.out.println("whoops");
			}
		} 
		
	
		

		// CODE TO DELETE ARTISTS FROM artists:
		
		String _deleteArtist = InputHelper.getInput(_deleteArtistPrompt);
		if (_deleteArtist.equals("Yes")) {
			int _artistId = InputHelper.getIntInput("Please enter Artist Id");
			if (ArtistsManager.deleteArtist(_artistId)) {
				System.out.println("It's gone!");
			} else {
				System.err.println("Ain't nothin' there!");
			}
		}
		
	}
}


// ------------------------------------------------------------------------------------------------------------------




/*
String _songsFromFile = "MacAndJuiceTunes.txt";
		//Songlist _tunes = new Songlist(_songsFromFile);
		
		//_tunes.addSongsFromFile();  // 7/4/2020  NEED METHOD TO ADD SONGS FROM DB
				
		// OTHER VARIABLES
		Scanner _input = new Scanner(System.in);
		String _yesOrNo = _input.nextLine();
		
		while (_yesOrNo.toUpperCase().equals("YES")) {*/
			// FOR NOW, NOT WORRIED ABOUT STORING SONGS WITH ARTISTS IN DB
			/*
			System.out.println(_artistPrompt);
			String _artistName = _input.nextLine(); 
			*/
			// CODE TO USE _artistName	TO ACCESS COLUMN IN DB
			/*System.out.println(_newSong);
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
		}*/
		// Shows list after songs are added
		
		
		
		//System.out.print(_howManySongs);
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
		// THIS DOESNT WORK YET:
		for (int x = 0; x < _count; x++) {
			int _randoSong = (int) (Math.random() * _count);
			System.out.println(_tunes.get(_randoSong));
		}*/
		
		// to test that the songs are subtracted from the Songlist
		/*System.out.println("----------");
		for (int i = 0; i < _tunes.size(); i++) {
			System.out.println(_tunes.get(i));
		}*/
		