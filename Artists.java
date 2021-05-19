import java.sql.ResultSet;
import java.sql.SQLException;


public class Artists {
    static Songlist _tunes = new Songlist();    
    public static Songlist Songlist(ResultSet rs) throws SQLException{
        while (rs.next()) {
            _tunes.insertSong(rs.getString("artistSongs"));
        }
        return _tunes;
    }
    public static void displayData(ResultSet rs) throws SQLException{
       
       while (rs.next()) {
            StringBuffer buffer = new StringBuffer();

            buffer.append("Artist " + rs.getInt("artistId") + ": "); //getObject(columnName, DataType)
            buffer.append(rs.getString("artistName") + ", ");
            buffer.append(rs.getString("artistSongs"));
            System.out.println(buffer.toString());

        }
    }
    
}