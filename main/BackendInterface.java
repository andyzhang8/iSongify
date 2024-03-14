import java.util.List;
import java.io.IOException;

/**
 * BackendInterface - CS400 Project 1: iSongify
 */
public interface BackendInterface {

    //public BackendPlaceholder(IterableSortedCollection<SongInterface> tree);

    /**
     * Loads data from the .csv file referenced by filename.
     * @param filename is the name of the csv file to load data from
     * @throws IOException when there is trouble finding/reading file
     */
    public void readData(String filename) throws IOException;

    /**
     * Retrieves a list of song titles for songs that have a Speed (BPM)
     * within the specified range (sorted by BPM in ascending order).  If 
     * a maxYear filter has been set using filterOldSongs(), then only songs
     * on Billboard during or before that maxYear should be included in the 
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
    public List<String> getRange(int low, int high);

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
    public List<String> filterOldSongs(int maxYear);


    /**
     * This method makes use of the attribute range specified by the most
     * recent call to getRange().  If a maxYear threshold has been set by
     * filterOldSongs() then that will also be utilized by this method.
     * Of those songs that match these criteria, the five most danceable will 
     * be returned by this method as a List of Strings in increasing order of 
     * danceability.  Each string contains the danceability followed by a 
     * colon, a space, and then the song's title.
     * If fewer than five such songs exist, return all of them.
     *
     * @return List of five most danceable song titles and their danceabilities
     * @throws IllegalStateException when getRange() was not previously called.
     */
    public List<String> fiveMostDanceable();
    
    /**
     * This method makes use of the attribute range specified by the most
     * recent call to getRange().  If a minSpeed threshold has been set by
     * filterFastSongs() then that will also be utilized by this method.
     * Of those songs that match these criteria, the five most energetic will
     * be  returned by this method as a List of Strings in increasing order of 
     * danceability.  Each string contains the energy rating followed by a 
     * colon, a space, and then the song's title.
     * If fewer than five such songs exist, return all of them.
     *
     * @return List of five most energetic song titles and their energies
     * @throws IllegalStateException when getRange() was not previously called.
     */
    public List<String> fiveMostEnergetic();
}
