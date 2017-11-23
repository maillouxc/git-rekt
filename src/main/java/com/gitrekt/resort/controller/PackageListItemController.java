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

    public PackageListItemController() {
        // Intentionally blank
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        qtySpinner.setValueFactory(new IntegerSpinnerValueFactory(0, 100, 0));
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

    public void setListController(PackageListController listController) {
        this.listController = listController;
    }

    public void setData(Package p) {
        this.packageData = p;

        packageNameLabel.setText(p.getName());
        String packagePrice = String.format("$%.2f", p.getPricePerPerson());
        packagePriceLabel.setText(packagePrice + " per person");
        packageDescription.setText(p.getDescription());
        packageImage.setImage(new Image(p.getImagePath()));
    }

}
