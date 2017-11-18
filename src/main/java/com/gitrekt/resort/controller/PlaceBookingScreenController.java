package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.UsState;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    private Label statePickerLabel;

    private ObservableList<String> countries ;

    private ObservableList<String> states;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeStatePicker();
        initializeCountryPicker();
    }

    @FXML
    private void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen("/fxml/BrowseRoomsScreen.fxml");
    }

    @FXML
    private void onFinishButtonClicked() {
        // TODO
    }

    public void initializeCountryPicker() {
        countries = FXCollections.observableArrayList();

        Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
            String name = locale.getDisplayCountry();
            if (!countries.contains(name)) {
                countries.add(name);
            }
        }
        FXCollections.sort(countries);
        countryPicker.setItems(countries);
    }

    public void onCountryPicked() {
        if(countryPicker.getValue().equals("United States")) {
            statePicker.setVisible(true);
            statePickerLabel.setVisible(true);
        } else {
            statePicker.setVisible(false);
            statePickerLabel.setVisible(false);
        }
    }

    public void initializeStatePicker() {
        states = FXCollections.observableArrayList();
        for(UsState state : UsState.values()) {
            states.add(state.getUnabbreviated());
        }
        statePicker.setItems(states);
        statePicker.setVisible(false);
        statePickerLabel.setVisible(false);
    }
}
