package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.entities.Package;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML controller class for the list items displayed on the package booking screen.
 */
public class PackageListItemController implements Initializable {

    @FXML
    private Node root;

    @FXML
    private Label packageNameLabel;

    @FXML
    private Label packageDescription;

    @FXML
    private Label packagePriceLabel;

    @FXML
    private ImageView packageImage;

    @FXML
    private Spinner<Integer> qtySpinner;

    private PackageListController listController;

    private Package packageData;

    /**
     * Called by JavaFX.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int min = 0;
        int max = 100;
        int initialValue = 0;
        qtySpinner.setValueFactory(new IntegerSpinnerValueFactory(min, max, initialValue));
        qtySpinner.valueProperty().addListener(
            (obs, oldValue, newValue) -> {
                listController.updatePackageQty(this.packageData, newValue);
            }
        );
    }

    /**
     * @return The graphical representation of this list item.
     */
    public Node getView() {
        return root;
    }

    /**
     * This method must be called in order to initialize a reference to the screen controller
     * for the screen that contains these list items; that is, the book packages screen.
     *
     * This is a hacky way to do things, but this reference allows us to communicate between the
     * components without a nightmare of interfaces and callbacks.
     *
     * @param listController The controller for the class containing this list.
     */
    public void setListController(PackageListController listController) {
        this.listController = listController;
    }

    /**
     * Initializes the data item for this list item.
     *
     * @param p The package data to display in this list item.
     */
    public void setData(Package p) {
        this.packageData = p;

        packageNameLabel.setText(p.getName());
        String packagePrice = String.format("$%.2f", p.getPricePerPerson());
        packagePriceLabel.setText(packagePrice + " per person");
        packageDescription.setText(p.getDescription());
        packageImage.setImage(new Image(p.getImagePath()));
    }

}
