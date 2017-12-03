package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.services.RoomService;
import com.gitrekt.resort.model.entities.Room;
import com.gitrekt.resort.model.entities.RoomCategory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditPricesScreenController implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private Button editPriceButton;
    @FXML
    private TableView<RoomCategory> roomTableView;
    @FXML
    private TableColumn<RoomCategory,String> roomNameColumn;
    @FXML
    private TableColumn<RoomCategory,String> roomDescriptionColumn;
    @FXML
    private TableColumn<RoomCategory,Double> roomPriceColumn;

    private ObservableList<RoomCategory> room;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        room = FXCollections.observableArrayList();
        roomTableView.setItems(room);

        roomNameColumn.setCellValueFactory((param) -> {
            return new SimpleStringProperty(
                    param.getValue().getName()
            );
        });

        roomDescriptionColumn.setCellValueFactory((param) -> {
            return new SimpleStringProperty(
                    param.getValue().getDescription()
            );
        });

        roomPriceColumn.setCellValueFactory((param) ->{
            return new SimpleDoubleProperty(
                    param.getValue().getBasePrice()
            ).asObject();
        } );

        loadData();

    }

    @FXML
    private void oneditPriceClickedButton() throws IOException {

        Stage editPriceDialogStage = new Stage();
        Parent editPriceDialogRoot = FXMLLoader.load(
                getClass().getResource("/fxml/EditPriceDialog.fxml")
        );
        Scene editPriceDialog = new Scene(editPriceDialogRoot);

        editPriceDialogStage.getIcons().add(new Image("images/Logo.png"));
        editPriceDialogStage.setScene(editPriceDialog);
        editPriceDialogStage.initModality(Modality.APPLICATION_MODAL);
        editPriceDialogStage.initOwner(editPriceButton.getScene().getWindow());
        editPriceDialogStage.setResizable(false);
        editPriceDialogStage.setTitle("Edit Price");
        editPriceDialogStage.centerOnScreen();
        editPriceDialogStage.show();

    }

    @FXML
    private void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen("/fxml/StaffHomeScreen.fxml");
    }

    private void loadData() {
        RoomService roomService = new RoomService();
        room.addAll(roomService.getAllRoomCategories());
    }





}

