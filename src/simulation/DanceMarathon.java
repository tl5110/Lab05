package simulation;

import java.util.*;
import java.lang.Math;
import java.io.File;
import java.util.Random;
import java.io.FileNotFoundException;
import java.util.stream.Collectors;

/**
 * The main program is run on the command line with a tracks file:
 *
 * Usage: java DanceMarathon filename
 *
 * 1. The songs are read from a file into both a hashmap/jukebox, with
 *      the song's artist and title as a key and the value as the amount
 *      of time it's played, and a list too. Displays the total number
 *      of songs loaded into the hashmap/jukebox
 * 2. The songs are randomly picked to be played. Until a song is replayed,
 *      each song is added to a hashset and the value of the time a song is
 *      played is increased in the hashmap. This process is repeated 100000
 *      times. The first five songs played and the total time in seconds it
 *      took to run the entire simulation are displayed.
 * 3. Some statistics are displayed about the amount of times the simulation
 *      is run, the total number of songs played throughout the entire
 *      simulation, the average number of songs played to get a duplicate
 *      across the entire simulation, the song that was played the most, and
 *      a list of all the songs by the most played song's artist in
 *      alphabetical order with the total number of times each song was played
 *
 * @author Tiffany Lee
 */
public class DanceMarathon {
    /**
     * the jukebox that keeps track of how
     * many times each song has been played
     */
    private final static HashMap<Song,Integer> jukebox = new HashMap<>();
    /** the list of all the songs in the jukebox */
    private final static List<Song> playlist = new ArrayList<>();

    /**
     * Reads the data file of songs and loads them all into the jukebox.
     * Displays the first and last song in the jukebox.
     *
     * @param filename name of the file
     * @throws FileNotFoundException if the file is not found
     */
    public DanceMarathon(String filename) throws FileNotFoundException {
        Scanner tracks = new Scanner(new File(filename));

        System.out.println("Loading the jukebox with songs:");
        System.out.println("\tReading songs from " + filename + " into jukebox...");

        while(tracks.hasNext()){
            String[] oneSong = tracks.nextLine().split("<SEP>");
            Song song = new Song(oneSong[2], oneSong[3]);
            jukebox.put(song, 0);
        }
        playlist.addAll(jukebox.keySet());

        System.out.println("\tJukebox is loaded with " + jukebox.size() + " songs");
        System.out.println("\tFirst song in jukebox: " + playlist.get(0));
        System.out.println("\tLast song in jukebox: " + playlist.get(playlist.size()-1));
    }

    /**
     * Randomly accesses songs in the jukebox.
     * If a song is played its value of plays in the jukebox increases
     * and is continuously added to hash-set until a song has been replayed.
     * This process is repeated 100,000 times.
     * Prints out when the simulation is running, the first five songs played,
     * and the amount of time the simulation took in seconds.
     */
    public void simulation(){
        System.out.println("Running the simulation.  The jukebox starts rockin'!");
        List<Song> five = new ArrayList<>();
        Random songIndex = new Random(42);
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < 100000; i++){
            HashSet<Song> replay = new HashSet<>();
            Song a = playlist.get(songIndex.nextInt(playlist.size()));
            while(!replay.contains(a)){
                if(five.size()!=5){
                    five.add(a);
                }
                replay.add(a);
                jukebox.put(a, jukebox.get(a)+1);
                a = playlist.get(songIndex.nextInt(playlist.size()));
            }
        }
        long endTime = System.currentTimeMillis();
        int totalTime = (int)(endTime - startTime) / 1000;
        System.out.println("\tPrinting first 5 songs played...");
        five.forEach(song -> System.out.println("\t\t" + song));
        System.out.println("\tSimulation took " + totalTime + " second/s to run");
    }

    /**
     * Determines and displays the number of simulation runs,
     * total number of songs played throughout the simulation,
     * average amount of plays it took to get a duplicate across
     * the entire simulation, finds the most played song,and all
     * the other songs, and amount of time they are played, by
     * the artist of the most played song in alphabetical order.
     */
    public void statistics(){
        int totalPlays = jukebox.values().stream().reduce(0, Integer::sum);
        int avgPlays = (int) Math.ceil((double)totalPlays/100000);
        Song mostPlayed = Collections.max(jukebox.entrySet(), HashMap.Entry.comparingByValue()).getKey();
        TreeSet<Song> topArtist = jukebox.keySet().stream().
                filter(song -> song.getArtist().equals(mostPlayed.getArtist())).
                collect(Collectors.toCollection(TreeSet::new));

        System.out.println("Displaying simulation statistics:");
        System.out.println("\tNumber of simulations run: 100000");
        System.out.println("\tTotal number of songs played: " + totalPlays);
        System.out.println("\tAverage number of songs played per simulation to get duplicate: " + avgPlays);
        System.out.println("\tMost played song: \""
                + mostPlayed.getTitle() + "\" by \""
                + mostPlayed.getArtist() + "\"");
        System.out.println("\tAll songs alphabetically by \"" + mostPlayed.getArtist() +"\":");
        topArtist.forEach(song -> System.out.println("\t\t\"" + song.getTitle()
                + "\" with " + jukebox.get(song) + " plays"));
    }

    /**
     * The main method that gets the filename of a file of songs from the
     * command line arguments, creates a jukebox from file of songs,
     * runs a simulation of it, and calculates its statistics.
     *
     * @param args command line arguments that contains filename
     *             of a file of songs
     * @throws FileNotFoundException if the file is not found
     */
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            System.out.println("Usage: java DanceMarathon filename");
        }
        DanceMarathon danceMarathon = new DanceMarathon(args[0]);
        danceMarathon.simulation();
        danceMarathon.statistics();
    }
}
