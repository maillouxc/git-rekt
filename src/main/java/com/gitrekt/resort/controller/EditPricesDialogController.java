package com.gitrekt.resort.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    }


    public void onEditButtonClicked() {

    }



}
