package com.gitrekt.resort.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class PackageListItemController implements Initializable {

   @FXML
    private Node root;

    @FXML
    private Label packageTitle;

    @FXML
    private Label packageDescription;

    @FXML
    private Label totalPrice;

    @FXML
    private ImageView packageImage;

    @FXML
    private Label pricePerPerson;

    private PackageScreenController parentController;


    public PackageListItemController() {
        // Intentionally blank
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Intentionally blank
    }

    /**
     * @return The graphical representation of this list item.
     */
    public Node getView() {
        return root;
    }

    /**
     * Assigns a reference the the controller of the class containing the list that this item is
     * displayed in.
     *
     * This is needed to be able to handle the buttons in the list item easily.
     *
     * @param parentController
     */
    public void setParentController(PackageScreenController parentController) {
        this.parentController = parentController;
    }

}
