import java.util.List;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * BackendPlaceholder - CS400 Project 1: iSongify
 *
 * This class doesn't really work.  The methods are hardcoded to output values
 * as placeholders throughout development.  It demonstrates the architecture
 * of the Backend class that will be implemented in a later week.
 */
public class BackendPlaceholder implements BackendInterface {
	private IterableSortedCollection<SongInterface> tree;
    private List<SongInterface> songList;
    private List<SongInterface> songFilteredList;
    public BackendPlaceholder(IterableSortedCollection<SongInterface> tree) {
    	this.tree = tree;
    	this.songList = new ArrayList<SongInterface>();//this is to keep all songs backed up
    	this.songFilteredList = new ArrayList<SongInterface>();//this is the updated song list based off filters
    }

    /**
     * Loads data from the .csv file referenced by filename.
     * @param filename is the name of the csv file to load data from
     * @throws IOException when there is trouble finding/reading file
     */
    @Override
    public void readData(String filename) throws IOException {
	// Note: this placeholder doesn't need to output anything,
	// it will be implemented by the backend developer in P105.
    	try (Scanner scanner = new Scanner(new File(filename))) {
            scanner.nextLine(); //skip songs csv first line with headers
    		while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = parseCSVLine(line);
                SongInterface song = new Song(data[0], data[1], data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Integer.parseInt(data[7]), Integer.parseInt(data[8]));
                tree.insert(song);
                songList.add(song);
            }
        }
    }
    //helper method for readData that parses a single csv line
    private String[] parseCSVLine(String line) {
        List<String> attributes = new ArrayList<>();
        String attribute = "";//csv "" contain needed fixes
        int quoteCount = 0;
        int commaCount = 0;
        //tracking the edge cases
        for (char c : line.toCharArray()) {
        	//need to count quotes due to csv syntax
            if (c == '"') {
                quoteCount++;
            }
            if (c== ',') {
            	commaCount++;
            }
            if (quoteCount>=2 && commaCount <1) {
            	//are we inside of double double quotes
            	attribute+=c;
            }else{ //is this the end of the attribute
            	if (c == ',' && quoteCount % 2 == 0) {
            		attributes.add(attribute);
            		attribute = ""; //reset attribute
            		quoteCount = 0;//reset both counts
            		commaCount = 0;
            	} else {
            		attribute += c;
            	}
            }
        }
        attributes.add(attribute);

        return attributes.toArray(new String[0]);
            
        
    
    }

    /**
     * Retrieves a list of song titles for songs that have a Speed (BPM)
     * within the specified range (sorted by BPM in ascending order).  If 
     * a maxYear filter has been set using filterOldSongs(), then only songs
     * on Billboard durring or before that maxYear should be included in the 
     * list that is returned by this method.
     *
     * Note that either this bpm range, or the resulting unfiltered list
     * of songs should be saved for later use by the other methods defined in 
     * this class.
     *
     * @param low is the minimum Speed (BPM) of songs in the returned list
     * @param hight is the maximum Speed (BPM) of songs in the returned list
     * @return List of titles for all songs in specified range 
     */
    @Override
    public List<String> getRange(int low, int high) {
	// placeholder just returns a hard coded list of songs
    	List<String> songsInRange = new ArrayList<>();
        for (SongInterface song : songList) {
            if (song.getBPM() >= low && song.getBPM() <= high) {
                songsInRange.add(song.getTitle());
            }
        }
        //this updates songFilteredList with the songs in range
        songFilteredList.clear();
        for (SongInterface song : songList) {
            if (songsInRange.contains(song.getTitle())) {
                songFilteredList.add(song);
            }
        }
        return songsInRange;
    }

    /**
     * Filters the list of songs returned by future calls of getRange() and 
     * fiveMostDanceable() to only include older songs.  If getRange() was 
     * previously called, then this method will return a list of song titles
     * (sorted in ascending order by Speed BPM) that only includes songs on
     * Billboard on or before the specified maxYear.  If getRange() was not 
     * previously called, then this method should return an empty list.
     *
     * Note that this maxYear threshold should be saved for later use by the 
     * other methods defined in this class.
     *
     * @param maxYear is the maximum year that a returned song was on Billboard
     * @return List of song titles, empty if getRange was not previously called
     */
    @Override
    public List<String> filterOldSongs(int maxYear) {
    	List<String> titles = new ArrayList<>();
    	if(songFilteredList.isEmpty()) return titles;
    	//copy was needed for iteration due to some issues found when removing
    	List<SongInterface> songFilteredListCopy = new ArrayList<>(songFilteredList);
        for (SongInterface song : songFilteredListCopy) {
            if (song.getYear() <= maxYear) {
                titles.add(song.getTitle());
            }else {
            	//this keeps filtered list updated
            	songFilteredList.remove(song);
            	}
        }
        return titles;
    }

    /**
     * This method makes use of the attribute range specified by the most
     * recent call to getRange().  If a maxYear threshold has been set by
     * filterOldSongs() then that will also be utilized by this method.
     * Of those songs that match these criteria, the five most danceable will 
     * be returned by this method as a List of Strings in increasing order of 
     * speed (bpm).  Each string contains the danceability followed by a 
     * colon, a space, and then the song's title.
     * If fewer than five such songs exist, display all of them.
     *
     * @return List of five most danceable song titles and their danceabilities
     * @throws IllegalStateException when getRange() was not previously called.
     */
    @Override
    public List<String> fiveMostDanceable() {
    	//this will throw if getRange wasn't called previously
    	if(songFilteredList.isEmpty()) throw new IllegalStateException("getRange was not previously called");
    	//filteredList sorting
    	songFilteredList.sort(Comparator.comparingInt(SongInterface::getDanceability).reversed());
    	//to be returned
        List<String> result = new ArrayList<>();
        //take largest 5
        for (int i = 0; i < Math.min(5, songFilteredList.size()); i++) {
            SongInterface song = songFilteredList.get(i);
            result.add(song.getDanceability() + ": " + song.getTitle());
        }
        return result;
    
    }    
}