package simulation;

/**
 * An interface for the public behaviors of Song
 *
 * @param <E> The type of data Comparable will hold
 * @author Tiffany Lee
 */
public interface Comparable<E> {
    /**
     * Orders/Compares songs alphabetically first by artists name, and
     * secondly by song title
     *
     * @param other the other song to compare to
     * @return negative integer, positive integer, or zero if this song comes
     * before, after, or is the same as the passed song
     */
    int compareTo(E other);
}
