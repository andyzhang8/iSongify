import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * FrontendInterface - CS400 Project 1: iSongify
 */
public class Frontend implements FrontendInterface {
  public String min = "min";
  public String max = "max";
  public String year = "none";
  public String csv = ".";
  public Scanner scan;
  BackendInterface backend;

  public Frontend(Scanner in, BackendInterface backend) {
    scan = in;
    this.backend = backend;
  }

  /**
   * Repeated gives the user an opportunity to issue new commands until
   * they select Q to quit.
   */
@Override
public void runCommandLoop() {
  String input;
  boolean quit = false;
  while (!quit) {
    displayMainMenu();
    input = scan.nextLine().trim();
    /*if (input.isEmpty()) {
      continue;
    }
    input = scan.nextLine().trim();*/
    switch (input.toUpperCase()) {
      case "R":
        readFile();
        break;
      case "G":
        getValues();
        break;
      case "F":
        setFilter();
        break;
      case "D":
        topFive();
        break;
      case "E":
        mostEnergy();
        break;
      case "Q":
        quit = true;
        System.out.println("Exiting...");
        break;
      default:
        System.out.println("Invalid input. Please enter a valid command.");
        break;
    }
  }
}
 

  /**
   * Displays the menu of command options to the user.
   */
  @Override
  public void displayMainMenu() {
    String menu = """

        ~~~ Command Menu ~~~
            [R]ead Data
            [G]et Songs by Speed BPM [min - max]
            [F]ilter Old Songs (by Max Year: none)
            [D]isplay Five Most Danceable
            [E]nergetic Songs
            [Q]uit
        Choose command:""";
    menu = menu.replace("min", min).replace("max", max).replace("none", year);
    System.out.print(menu + " ");
  }

  /**
   * Provides text-based user interface and error handling for the [R]ead Data command.
   */
  @Override
  public void readFile() {
    System.out.print("Enter path to csv file to load: ");
    String name = scan.nextLine();
    Scanner file = new Scanner(name);
    file.useDelimiter(",");
    try {
      backend.readData(name);
    } catch (IOException e) {
      e.printStackTrace();
    }
    file.close();
    System.out.println("Done reading file.");
  }

  /**
   * Provides text-based user interface and error handling for the [G]et Songs by Speed BPM command.
   */
  @Override
  public void getValues() {
    System.out.print("Enter minimum Speed (BPM) of songs: ");
    int minimum = scan.nextInt();
    System.out.print("Enter maximum Speed (BPM) of songs: ");
    int maximum = scan.nextInt();
    min = String.valueOf(minimum);
    max = String.valueOf(maximum);
    scan.nextLine();
    List<String> titles = backend.getRange(minimum, maximum);
    for (int i = 0; i < titles.size(); i++) {
      System.out.println((i + 1) + ". " + titles.get(i));
    }
  }

  /**
   * Provides text-based user interface and error handling for the [F]ilter Old Songs (by Max Year)
   * command.
   */
  @Override
  public void setFilter() {
    System.out.print("Enter maximum year: ");
    int year = scan.nextInt();
    this.year = String.valueOf(year);
    List<String> filteredList = backend.filterOldSongs(year);
    for (int i = 0; i < filteredList.size(); i++) {
      System.out.println((i + 1) + ". " + filteredList.get(i));
    }
  }

  /**
   * Provides text-based user interface and error handling for the [D]isplay Five Most Danceable
   * command.
   */
  @Override
  public void topFive() {
    List<String> mostDancable = backend.fiveMostDanceable();
    System.out.println("Five Most Danceable:");
    for (int i = 0; i < mostDancable.size(); i++) {
      System.out.println((i + 1) + ". " + mostDancable.get(i));
    }
  }
  /**
   * Provides text-based user interface and error handling for the [E]nergetic Songs
   * command.
   */
  private void mostEnergy() {
    List<String> mostEnergy = backend.fiveMostEnergetic();
    System.out.println("Five Most Energetic:");
    for (int i = 0; i < mostEnergy.size(); i++) {
      System.out.println((i + 1) + ". " + mostEnergy.get(i));
    }
  }
}
