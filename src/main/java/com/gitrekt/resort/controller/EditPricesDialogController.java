package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.services.RoomService;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditPricesDialogController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private Button editButton;

    @FXML
    private TextField editPriceField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onCancelButtonClicked() {
        Stage dialogStage = (Stage) cancelButton.getScene().getWindow();
        dialogStage.close();
        ScreenManager.getInstance().switchToScreen("/fxml/EditPricesScreen.fxml");

    }

    public void onEditButtonClicked() {
        boolean isGoodInput = true;
        double d  = 0;
        try {
           d = Double.valueOf(editPriceField.getText());
        } catch (NumberFormatException e) {
            isGoodInput = false;
        }
        if (isGoodInput == true) {
            RoomService roomservice = new RoomService();

            Alert a = new Alert(AlertType.CONFIRMATION);
            a.setTitle("Price Change Confirmation");
            a.setHeaderText("Confirm the Price Change?");
            Optional<ButtonType> result = a.showAndWait();
            if (result.get() == ButtonType.OK) {
                roomservice.editRoomPrice(EditPricesScreenController.service, d);
                Stage dialogStage = (Stage) cancelButton.getScene().getWindow();
                dialogStage.close();
                ScreenManager.getInstance().switchToScreen("/fxml/EditPricesScreen.fxml");
            }
        }
    }

}
