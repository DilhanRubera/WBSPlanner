package edu.curtin.app;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("PMD.ConfusingTernary")
public class App {

    /* Initializing the tasks map which is a TreeMap. */
    public static Map<String, Task> tasks = new TreeMap<>();

    // Creating an instance of the MainMenu class.
    public static MainMenu mainMenu = new MainMenu();

    // Creating a logger object.
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        String fileName;

        // Checking if the user has entered the correct number of arguments.
        if (args.length != 1) {
            System.out.println("Usage: java App <filename>");
            System.exit(1);
        } else {

            // Getting the file name from the command line arguments.
            fileName = args[0];

            logger.log(Level.INFO, () -> "File name: " + fileName);

            // Loading the tasks from the file.
            tasks = FileIO.loadFile(tasks, fileName);

            // Running the main menu.
            mainMenu.runMenu(tasks);
        }
    }
}
