import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Actual Backend class which replaces the placeholder to implement BackendInterface
 */
public class Backend implements BackendInterface {
  private IterableSortedCollection<SongInterface> tree = new ISCPlaceholder<SongInterface>();;
  private int minSpeed = -1;
  private IterableSortedCollection<SongInterface> currRange = new ISCPlaceholder<SongInterface>();
  private IterableSortedCollection<SongInterface> filtered = new ISCPlaceholder<SongInterface>();
  private List<SongInterface> songList;
  private List<SongInterface> songFilteredList;

  public Backend(IterableSortedCollection<SongInterface> tree) {
    if (tree != null) {
      this.tree = tree;
    }
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
    File file = new File(filename);
    if (!file.exists()) {
      throw new IOException("Trouble finding/reading the file");
    } else {
      Scanner sc = new Scanner(file);
      sc.nextLine();
      while (sc.hasNext()) {
        String current = sc.nextLine();
        tree.insert(replace(current));
      }
      sc.close();
    }
  }

  /**
   * Helper method to change the long string of songs.csv file into a song type
   * @param curr - the string to be changed into a song 
   * @return a song representation of that string 
   */
  private SongInterface replace(String curr) {
    int quoteCount = 0;
    for (int i = 0; i < curr.length(); i++) {
      char c = curr.charAt(i);
      if (c == '"') {
        quoteCount++;
      }
      if (quoteCount % 2 == 0 && c == ',') {
        curr = curr.substring(0, i) + "SPACESTRING" + curr.substring(i + 1);
      }
    }
    String[] parsed = curr.split("SPACESTRING");
    String title = parsed[0];
    if (title.charAt(0) == '"') {
      title = title.substring(1, title.length() - 1);
    }
    for (int i = 0; i < title.length(); i++) {
      if (title.charAt(i) == '"' && title.charAt(i + 1) == '"') {
        title = title.substring(0, i) + title.substring(i + 1, title.length());
      }
    }
    String artist = parsed[1];
    String genre = parsed[2];
    int year = Integer.parseInt(parsed[3]);
    int BPM = Integer.parseInt(parsed[4]);
    int energy = Integer.parseInt(parsed[5]);
    int danceability = Integer.parseInt(parsed[6]);
    int loudness = Integer.parseInt(parsed[7]);
    int liveness = Integer.parseInt(parsed[8]);
    SongInterface song =
        new Song(title, artist, genre, year, BPM, energy, danceability, loudness, liveness);
    return song;

  }

  /**
   * Retrieves a list of song titles for songs that have a Danceability within the specified range
   * (sorted by Danceability in ascending order). If a minSpeed filter has been set using
   * filterFastSongs(), then only songs with speed greater than or equal to that minSpeed should be
   * included in the list returned by this method. Note that either this danceability range, or the
   * resulting unfiltered list of songs should be saved for later use by the other methods defined
   * in this class.
   * @param low   is the minimum danceability of songs in the returned list
   * @param hight is the maximum danceability of songs in the returned list
   * @return List of titles for all songs in specified range
   */
  @Override
  public List<String> getRange(int low, int high) {
    List<String> songTree1 = new ArrayList<String>();
    for (SongInterface s : tree) {
      if (s.getDanceability() >= low && s.getDanceability() <= high) {
        if (minSpeed != -1) {
          if (s.getBPM() > minSpeed) {
            currRange.insert(s);
            songTree1.add(s.getTitle());
          }
        } else {
          currRange.insert(s);
          songTree1.add(s.getTitle());
        }
      }
    }
    return songTree1;
  }

  /**
   * Filters the list of songs returned by future calls of getRange() and fiveMostEnergetic() to
   * only include Fast songs. If getRange() was previously called, then this method will return a
   * list of song titles (sorted in ascending order by Danceability) that only includes songs that
   * as fast or faster than minSpeed. If getRange() was not previusly called, then this method
   * should return an empty list.
   *
   * Note that this minSpeed threshold should be saved for later use by the other methods defined in
   * this class.
   *
   * @param minSpeed is the minimum speed in bpm of returned songs
   * @return List of song titles, empty if getRange was not previously called
   */
  public List<String> filterFastSongs(int minSpeed) {
    this.minSpeed = minSpeed;
    List<String> songTree2 = new ArrayList<String>();
    filtered.clear();
    if (currRange.isEmpty()) {
      return new ArrayList<String>();
    }
    for (SongInterface s : currRange) {
      if (s.getBPM() >= minSpeed) {
        songTree2.add(s.getTitle());
        filtered.insert(s);
      }
    }
    return songTree2;
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

  
  /**
   * This method makes use of the attribute range specified by the most recent call to getRange().
   * If a minSpeed threshold has been set by filterFastSongs() then that will also be utilized by
   * this method. Of those songs that match these criteria, the five most energetic will be returned
   * by this method as a List of Strings in increasing order of danceability. Each string contains
   * the energy rating followed by a colon, a space, and then the song's title. If fewer than five
   * such songs exist, return all of them.
   * @return List of five most energetic song titles and their energies
   * @throws IllegalStateException when getRange() was not previously called.
   */
  @Override
  public List<String> fiveMostEnergetic() {
    List<String> songTree3 = new ArrayList<String>();
    SongInterface[] max = new SongInterface[5];
    max[0] = new Song("", "", "", 0, 0, -1, 0, 0, 0);
    max[1] = new Song("", "", "", 0, 0, -1, 0, 0, 0);
    max[2] = new Song("", "", "", 0, 0, -1, 0, 0, 0);
    max[3] = new Song("", "", "", 0, 0, -1, 0, 0, 0);
    max[4] = new Song("", "", "", 0, 0, -1, 0, 0, 0);
    if (currRange != null && !currRange.isEmpty()) {
      filterFastSongs(minSpeed);
    } else {
      throw new IllegalArgumentException();
    }
    for (int i = 0; i < 5; i++) {
      for (SongInterface s : currRange) {
        if (s.getEnergy() > max[i].getEnergy()) {
          boolean isRep = true;
          for (int j = i; j >= 0; j--) {
            if (max[j] == s) {
              isRep = false;
            }
          }
          if (isRep) {
            max[i] = s;
          }
        }
      }
    }
    for (int i = 0; i < 5; i++) {
      songTree3.add(max[i].getEnergy() + ": " + max[i].getTitle());
    }
    return songTree3;
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


}
