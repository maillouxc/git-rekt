package com.gitrekt.resort.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The JavaFX controller class for the home screen.
 *
 * The home screen is shown when the application first starts, and allows the user to select which
 * mode to use the program in: guest or staff.
 */
public class HomeScreenController implements Initializable {

    @FXML
    private Button staffModeButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Intentionally blank.
    }

    /**
     * Displays the pop-up dialog which prompts staff members to log in to the system.
     *
     * @throws IOException
     */
    @FXML
    public void onStaffModeButtonClicked() throws IOException {
        Stage staffLoginDialogStage = new Stage();
        Parent staffLoginDialogRoot = FXMLLoader.load(
            getClass().getResource("/fxml/StaffLoginDialog.fxml")
        );
        Scene staffLoginDialog = new Scene(staffLoginDialogRoot);

        staffLoginDialogStage.getIcons().add(new Image("images/Logo.png"));
        staffLoginDialogStage.setScene(staffLoginDialog);
        staffLoginDialogStage.initModality(Modality.APPLICATION_MODAL);
        staffLoginDialogStage.initOwner(staffModeButton.getScene().getWindow());
        staffLoginDialogStage.setResizable(false);
        staffLoginDialogStage.setTitle("Authentication Required");
        staffLoginDialogStage.centerOnScreen();
        staffLoginDialogStage.show();
    }

    /**
     * Shows the guest home screen.
     */
    public void onGuestModeButtonClicked() {
        ScreenManager.getInstance().switchToScreen("/fxml/GuestHomeScreen.fxml");
    }
}
