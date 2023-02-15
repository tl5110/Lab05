package simulation;

/**
 * Class that represents a single song and implements comparable
 *
 * @author Tiffany Lee
 */
public class Song implements Comparable<Song>{
    /** the artist of the song */
    private final String artist;
    /** the title of the song */
    private final String title;

    /**
     * Creates a new song with the passed artist and title
     *
     * @param artist the artist
     * @param title the song title
     */
    public Song(String artist, String title) {
        this.artist = artist;
        this.title = title;
    }

    /**
     * Gets the artist of this song
     *
     * @return the song's artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Gets the title of this song
     *
     * @return the song's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns a string representation for a song, which contains its
     * artist and title. For example, the song Firework written by Katy Perry
     * would return
     *      "Artist: Katy Perry, Title: Firework"
     *
     * @return the string described above
     */
    @Override
    public String toString() {
        return "Artist: " + artist + ", Title: " + title;
    }

    /**
     * Two songs are equal if they have the same artists and title
     *
     * @param o the other song to compare to
     * @return whether the two songs are equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Song otherSong) {
            return otherSong.artist.equals(this.artist) && otherSong.title.equals(this.title);
        }
        return false;
    }

    /**
     * Computes the location of the song into the hash table, which is
     * the sum of the song's artist's hashcode and title's hashcode
     *
     * @return location/hashcode of the song in the hash table
     */
    @Override
    public int hashCode() {
        return this.artist.hashCode() + this.title.hashCode();
    }

    /**
     * Orders/Compares songs alphabetically first by artists name, and
     * secondly by song title
     *
     * @param other the other song to compare to
     * @return negative integer, positive integer, or zero if this song comes
     * before, after, or is the same as the passed song
     */
    @Override
    public int compareTo(Song other){
        if(this.getArtist().compareTo(other.getArtist()) == 0){
            return this.getTitle().compareTo(other.getTitle());
        }
        return this.getArtist().compareTo(other.getArtist());
    }

}
