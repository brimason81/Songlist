/**
 *  Is this a JavaBean?  No serialization
 */

public class Artist {
    private int ArtistId;
    private String ArtistName;
    private String ArtistSong;

public void setArtistId(int ArtistId) {
    this.ArtistId = ArtistId;
}
public int getArtistId() {
    return ArtistId;
}
public void setArtistName(String ArtistName) {
    this.ArtistName = ArtistName;
}
public String getArtistName() {
    return ArtistName;
}
public void setArtistSong(String ArtistSong) {
    this.ArtistSong = ArtistSong;
}
public String getArtistSong() {
    return ArtistSong;
}
}