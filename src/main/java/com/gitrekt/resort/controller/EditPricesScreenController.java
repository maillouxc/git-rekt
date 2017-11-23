package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.services.RoomService;
import com.gitrekt.resort.model.entities.Room;
import com.gitrekt.resort.model.entities.RoomCategory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
    private void oneditPriceClickedButton() {

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
