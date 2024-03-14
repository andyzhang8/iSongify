import java.util.Scanner;

/**
 * FrontendPlaceholder - CS400 Project 1: iSongify
 *
 * This class doesn't really work.  The methods are hardcoded to output values
 * as placeholders throughout development.  It demonstrates the architecture
 * of the Frontend class that will be implemented in a later week.
 */
public class FrontendPlaceholder implements FrontendInterface {

    private String min = "min";
    private String max = "max";
    private String year = "none";
    
    public FrontendPlaceholder(Scanner in, BackendInterface backend) {}

    /**
     * Repeated gives the user an opportunity to issue new commands until
     * they select Q to quit.
     */
    public void runCommandLoop() {
	displayMainMenu();
	System.out.println("R"); // user entered command
	readFile();

	displayMainMenu();
	System.out.println("G"); // user entered command
	getValues();

	displayMainMenu();
	System.out.println("F"); // user entered command
	setFilter();

	displayMainMenu();
	System.out.println("D"); // user entered command
	topFive();

    	displayMainMenu();
	System.out.println("Q"); // user entered command
    }

    /**
     * Displays the menu of command options to the user.
     */
    public void displayMainMenu() {
		
	String menu = """
	    
	    ~~~ Command Menu ~~~
	        [R]ead Data
	        [G]et Songs by Speed BPM [min - max]
	        [F]ilter Old Songs (by Max Year: none)
	        [D]isplay Five Most Danceable
	        [Q]uit
	    Choose command:""";
	menu=menu.replace("min",min).replace("max",max).replace("none",year);
	System.out.print(menu + " ");
    }

    /**
     * Provides text-based user interface and error handling for the 
     * [R]ead Data command.
     */
    public void readFile() {
	System.out.print("Enter path to csv file to load: ");
	
	System.out.println("mySongData.csv"); // user entered command

	System.out.println("Done reading file.");
    }
    
    /**
     * Provides text-based user interface and error handling for the 
     * [G]et Songs by Speed BPM command.
     */
    public void getValues() {
	System.out.print("Enter range of values (MIN - MAX): ");

	System.out.println("80 - 90"); // user entered command
	min="80";
	max="90";
	
	System.out.println("""
			   5 songs found between 80 - 90:
			       Baby
			       Dynamite
			       Secrets
			       Empire State of Mind (Part II) Broken Down
			       Only Girl (In The World)""");
    }

    /**
     * Provides text-based user interface and error handling for the 
     * [F]ilter Old Songs (by Max Year) command.
     */
    public void setFilter() {
	System.out.print("Enter maximum year: ");
	System.out.println(2019);
	year="2019";
	
	System.out.println("""
			   2 songs found between 80 - 90 from 2019:
			       Baby
			       Only Girl (In The World)""");
    }

    /**
     * Provides text-based user interface and error handling for the 
     * [D]isplay Five Most Danceable command.
     */
    public void topFive() {
	System.out.println("""
			   Top Five songs found between 80 - 90 from 2019:
			       -5: Baby
			       -4: Only Girl (In The World)""");
    }

}
