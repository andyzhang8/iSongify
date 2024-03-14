/*
 * this class represents one song and all
 * the attributes it holds
 */
public class Song implements SongInterface{
private String title, artist, genre;
private int bpm, year, energy, danceability, loudness, liveness;
//constructor of Song object)
public Song(String title,String artist,String genre, int year, int bpm, int energy, int danceability, int loudness, int liveness){
	this.title = title;
	this.artist = artist;
	this.genre = genre;
	this.bpm = bpm;
	this.year = year;
	this.energy = energy;
	this.danceability = danceability;
	this.loudness = loudness;
	this.liveness = liveness;
	
	}
//getter methods
	@Override
	public String getTitle() {
		return title;
		}
	@Override
    public String getArtist() {
        return artist;
    }

    @Override
    public String getGenres() {
        return genre;
    }
	@Override
	public int compareTo(SongInterface s) {
		return 0;
	}
	@Override
	public int getYear() {
		return year;
	}
	@Override
	public int getBPM() {
		return bpm;
	}
	@Override
	public int getEnergy() {
		return energy;
	}
	@Override
	public int getDanceability() {
		return danceability;
	}
	@Override
	public int getLoudness() {
		return loudness;
	}
	@Override
	public int getLiveness() {
		return liveness;
	}
	
}


