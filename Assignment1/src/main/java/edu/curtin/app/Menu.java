package edu.curtin.app;

import java.util.Map;

// Menu class which is extended by the Main Menu and Sub Menu classes
public abstract class Menu {

    // Template method pattern
    public void displayMenu(String[] menuOptions) {
        for (int i = 0; i < menuOptions.length; i++) {
            System.out.println((i + 1) + ". " + menuOptions[i]);
        }
    }

    // Hook method of the Template pattern
    protected abstract void runMenu(Map<String, Task> tasks);
}
