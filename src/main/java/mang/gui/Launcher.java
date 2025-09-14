package mang.gui;

import javafx.application.Application;

/**
 * A launcher class to work around classpath issues.
 * This is the entry point of the JavaFX application.
 */
public class Launcher {
    public static void main(String[] args) {
        // Launches the Main class which extends javafx.application.Application
        Application.launch(Main.class, args);
    }
}
