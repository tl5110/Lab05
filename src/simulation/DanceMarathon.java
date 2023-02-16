package simulation;

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.*;
import java.io.File;
import java.util.stream.Collectors;
import java.util.TreeSet;


public class DanceMarathon {
    private final HashMap<Song,Integer> jukebox = new HashMap<>();
    private final List<Song> playlist = new ArrayList<>();
    private final Random songIndex = new Random(42);

    public DanceMarathon(String filename) throws FileNotFoundException {
        Scanner tracks = new Scanner(new File(filename));
        System.out.println("Loading the jukebox with songs:" +
                "\n\tReading songs from " + filename + " into jukebox...");
        while(tracks.hasNext()){
            String[] oneSong = tracks.nextLine().split("<SEP>");
            Song song = new Song(oneSong[2], oneSong[3]);
            this.jukebox.put(song, 0);
        }
        this.playlist.addAll(jukebox.keySet());
        System.out.println("\tJukebox is loaded with " + jukebox.size() + " songs");
        System.out.println("\tFirst song in jukebox: " + playlist.get(0));
        System.out.println("\tLast song in jukebox: " + playlist.get(playlist.size()-1));
    }

    public void simulation(){
        System.out.println("Running the simulation.  The jukebox starts rockin'!");
        List<Song> five = new ArrayList<>();
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
        int totalTime = (int)(endTime - startTime);
        System.out.println("\tPrinting first 5 songs played...");
        five.forEach(song -> System.out.println("\t\t" + song));
        System.out.println("\tSimulation took: " + totalTime + " second/s to run");
    }

    public void statistics(){
        int totalPlays = jukebox.values().stream().reduce(0, Integer::sum);
        int avgPlays = (totalPlays/100000);
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

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            System.out.println("Usage: java DanceMarathon filename");
        }
        DanceMarathon danceMarathon = new DanceMarathon(args[0]);
        danceMarathon.simulation();
        danceMarathon.statistics();
    }
}
