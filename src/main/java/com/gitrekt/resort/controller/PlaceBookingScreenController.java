package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.UsState;
import com.gitrekt.resort.model.entities.MailingAddress;
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
 * FXML Controller class for the screen where the user enters their name and other relevant info
 * for their booking, such as billing address.
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeStatePicker();
        initializeCountryPicker();
    }

    /**
     * Returns to the Browse Rooms screen.
     */
    @FXML
    private void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen("/fxml/BrowseRoomsScreen.fxml");
    }

    @FXML
    private void onFinishButtonClicked() {
        boolean validInput = validateInput();
        if(validInput) {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String addressLine1 = addressLine1Field.getText();
            String addressLine2 = addressLine2Field.getText();
            String city = cityField.getText();
            String country = countryPicker.getValue();
            String postalCode = postalCodeField.getText();
            String specialInstructions = specialInstructionsBox.getText();

            if(country.equals("United States")) {
                UsState state = UsState.valueOf(statePicker.getValue());
                MailingAddress mailingAddress = new MailingAddress(
                    addressLine1, addressLine2, postalCode, state, country
                );
            } else {
                MailingAddress mailingAddress = new MailingAddress(
                    addressLine1, addressLine2, postalCode, null, country
                );
            }

            // TODO: Determine if guest exists, if so, amend/use existing.
            // TODO: Create booking
            // TODO: Add packages.
        }
    }

    /**
     * Initializes the country picker with a list of all valid world countries, as obtained by the
     * Java locale class.
     */
    private void initializeCountryPicker() {
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

    /**
     * Called whenever a country is selected in the country picker.
     *
     * Checks if the selected country is the United States, and updates the visibility of the state
     * picker accordingly.
     */
    @FXML
    private void onCountryPicked() {
        if(countryPicker.getValue().equals("United States")) {
            statePicker.setVisible(true);
            statePickerLabel.setVisible(true);
        } else {
            statePicker.setVisible(false);
            statePickerLabel.setVisible(false);
        }
    }

    /**
     * Initializes the state picker with all US states plus Puerto Rico as choices.
     *
     * The state picker is set to be hidden initially. The program should unhide the state picker
     * when it is determined that the user is in the United States.
     */
    private void initializeStatePicker() {
        states = FXCollections.observableArrayList();
        for(UsState state : UsState.values()) {
            states.add(state.getUnabbreviated());
        }
        statePicker.setItems(states);
        statePicker.setVisible(false);
        statePickerLabel.setVisible(false);
    }

    private boolean validateInput() {
        boolean isValid = true;

        // Gather all input from all fields
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String confirmedEmail = confirmEmailField.getText();
        String addressLine1 = addressLine1Field.getText();
        String addressLine2 = addressLine2Field.getText();
        String city = cityField.getText();
        String country = countryPicker.getValue();
        String postalCode = postalCodeField.getText();
        String specialInstructions = specialInstructionsBox.getText();

        if(!confirmedEmail.equals(email)) {
            isValid = false;
        }


        return isValid;
        // TODO Finish validation
        // TODO UI cues
        // TODO trim input
    }
}
