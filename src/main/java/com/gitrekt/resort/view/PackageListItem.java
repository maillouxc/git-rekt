package com.gitrekt.resort.view;

import com.gitrekt.resort.controller.PackageListController;
import com.gitrekt.resort.controller.PackageListItemController;
import com.gitrekt.resort.model.entities.Package;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

public class PackageListItem extends ListCell<Package> {

    private final PackageListItemController controller;

    private final FXMLLoader fxmlLoader;

    private final Node view;

    private final PackageListController listController;

    public PackageListItem(PackageListController listController) {
        super();

        // Initialize a reference to the parent controller to allow callbacks
        this.listController = listController;

        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PackageListItem.fxml"));
            view = fxmlLoader.load();
            controller = fxmlLoader.getController();
            controller.setListController(listController);
        } catch (IOException e) {
            throw new IllegalStateException(e + "FXML file loading failed.");
        }
    }

    @Override
    protected void updateItem(Package packageData, boolean empty) {
        super.updateItem(packageData, empty);

        if(empty) {
            setGraphic(null);
        } else {
            controller.setData(packageData);
            setGraphic(view);
        }
    }

}
