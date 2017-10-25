package com.gitrekt.resort.controller;

import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class for the screen where the user enters their name and 
 * other relevant info for their booking, such as billing address.
 */
public class PlaceBookingScreenController implements Initializable {

    @FXML
    private TextField firstNameField;
    
    @FXML
    private TextField lastNameField;
    
    @FXML
    private TextField emailField;
    
    @FXML
    private TextField confirmEmailField;
    
    @FXML
    private TextField addressLine1Field;
    
    @FXML
    private TextField addressLine2Field;
    
    @FXML
    private TextField cityField;
    
    @FXML
    private TextField postalCodeField;
    
    @FXML
    private ComboBox<String> countryPicker;
    
    @FXML
    private ComboBox<String> statePicker;
    
    @FXML
    private TextArea specialInstructionsBox;
    
    @FXML
    private Button finishButton;
    
    @FXML
    private Button backButton;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Hide the state picker until we know if the user is from the US
        statePicker.setVisible(false);
    }

    
    @FXML
    protected void onBackButtonClicked() {
        // TODO
    }
    
    @FXML
    protected void onFinishButtonClicked() {
        // TODO
    }
    
    @FXML
    protected void onCountryPicked() {
        // TODO: If country is US, show state picker.
        ObservableList<String> countries = FXCollections.observableArrayList();
        //ComboBox<String> countryPicker = new ComboBox<>(countries);
        Locale[] locales = Locale.getAvailableLocales();
        for(Locale locale : locales){
            String name = locale.getDisplayCountry();
            if(!countries.contains(name)){
                countries.add(name);
            }
        }
        countryPicker.setItems(countries);
    }
}
