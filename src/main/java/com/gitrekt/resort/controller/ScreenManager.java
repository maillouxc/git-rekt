package com.gitrekt.resort.controller;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * Singleton that helps with the screen switching of the application.
 */
public class ScreenManager {
    
    private static Stage mainStage;
    
    private final FXMLLoader fxmlLoader;
    
    private static ScreenManager instance;
    
    private ScreenManager() {
        fxmlLoader = new FXMLLoader();
    }
    
    /**
     * Returns a reference to the singleton. 
     * 
     * Be sure that initialize has been called before attempting to use the
     * object, else a NPE will be thrown.
     * 
     * @return A reference to the singleton instance.
     */
    public static ScreenManager getInstance() {
        if (instance == null ) {
            instance = new ScreenManager();
        }
        return instance;
    }
    
    /**
     * Before the object can be used, it must be provided a reference to the
     * main stage.
     * 
     * @param mainStage The main stage of the application.
     */
    public void initialize(Stage mainStage) {
        this.mainStage = mainStage;
    }
    
    /**
     * @param fxmlPath The path to the FXML file of the screen to switch to.
     */
    public void switchToScreen(String fxmlPath) {
        Parent newScreenRoot;
        
        try {
            URL pathToFxml = getClass().getResource(fxmlPath);
            newScreenRoot = fxmlLoader.load(pathToFxml);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to load FXML", e);
        }
        
        mainStage.getScene().setRoot(newScreenRoot);
        mainStage.setMaximized(true);
    }
    
    /**
     * @return Whether the singleton is properly initialized and ready for use.
     */
    public boolean isInitialized() {
        return mainStage == null;
    }
    
}
