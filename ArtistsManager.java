import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ArtistsManager {
    
    public static Artist getRow(int artistId) throws SQLException {
        String sql = "SELECT * FROM artists WHERE ArtistId = ?";
        ResultSet rs = null;

        try ( 
            Connection conn = DBUtil.getConnection(DBType.MYSQL);
            PreparedStatement stmt = conn.prepareStatement(sql);
            ){
            stmt.setInt(1, artistId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Artist bean = new Artist();
                bean.setArtistId(artistId);
                bean.setArtistName(rs.getString("ArtistName"));
                bean.setArtistSong(rs.getString("ArtistSongs"));
                return bean;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println(e);
            return null;
        } finally {
            if (rs != null) {
                rs.close();
            } 
        }
    }
      
    public static boolean insertArtist(Artist bean) throws SQLException {
        String sql = "INSERT INTO artists (ArtistName, ArtistSongs) " +
            "VALUES(?, ?)";
        ResultSet _keys = null;
        try (
            Connection conn = DBUtil.getConnection(DBType.MYSQL);
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            
            stmt.setString(1, bean.getArtistName());
            stmt.setString(2, bean.getArtistSong());
            int _affected = stmt.executeUpdate();

            if (_affected == 1) {
                _keys = stmt.getGeneratedKeys();
                _keys.next();
                int _newKey = _keys.getInt(1);
                bean.setArtistId(_newKey);
            } else {
                System.err.println("No Rows Affected.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            if (_keys != null) {
                _keys.close();
            }
        }
    return true;
    }

    public static boolean updateArtist(Artist bean) throws SQLException {
        String sql = "UPDATE artists Set " +
                    "ArtistName = ?, ArtistSongs = ?" +
                    "WHERE ArtistId = ?";
        try (
            Connection conn = DBUtil.getConnection(DBType.MYSQL);
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
        stmt.setString(1, bean.getArtistName());
        stmt.setString(2, bean.getArtistSong());
        stmt.setInt(3, bean.getArtistId());

        int affected = stmt.executeUpdate();
        if (affected == 1) {
            return true;
        } else {
            return false;
        }
        
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    public static boolean deleteArtist(int artistId) throws SQLException { // Exception - change throws in Songs Main Method??
        String sql = "DELETE FROM artists WHERE ArtistId = ?";
        try (
            Connection conn = DBUtil.getConnection(DBType.MYSQL);
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, artistId);
            int affected = stmt.executeUpdate();

            if (affected == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
}


