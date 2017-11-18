package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.RoomSearchResult;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class PackageScreenController implements Initializable {

    @FXML
    private ListView<RoomSearchResult> listOfPackages;

    public PackageScreenController() {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
