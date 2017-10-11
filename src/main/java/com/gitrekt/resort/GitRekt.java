package com.gitrekt.resort;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main application class shell.
 */
public class GitRekt extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent homeScreenRoot = FXMLLoader.load(
                getClass().getResource("/fxml/HomeScreen.fxml")
        );
        
        Scene homeScreen = new Scene(homeScreenRoot);
        
        stage.setMaximized(true);
        stage.setScene(homeScreen);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
