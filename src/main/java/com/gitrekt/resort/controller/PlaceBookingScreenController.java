package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.UsState;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

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

    public void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
                "/fxml/BrowseRoomsScreen.fxml"
        );
    }

    public void onFinishButtonClicked() {
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
        // TODO: If country is US, show state picker (and it's associated label)
    }

    public void initializeStatePicker() {
        states = FXCollections.observableArrayList();
        states.add(UsState.ALABAMA.getAbbreviation());
        states.add(UsState.ALASKA.getAbbreviation());
        states.add(UsState.ARIZONA.getAbbreviation());
        states.add(UsState.ARKANSAS.getAbbreviation());
        states.add(UsState.CALIFORNIA.getAbbreviation());
        states.add(UsState.COLORADO.getAbbreviation());
        states.add(UsState.CONNECTICUT.getAbbreviation());
        states.add(UsState.DELAWARE.getAbbreviation());
        states.add(UsState.DISTRICT_OF_COLUMBIA.getAbbreviation());
        states.add(UsState.FLORIDA.getAbbreviation());
        states.add(UsState.GEORGIA.getAbbreviation());
        states.add(UsState.HAWAII.getAbbreviation());
        states.add(UsState.IDAHO.getAbbreviation());
        states.add(UsState.ILLINOIS.getAbbreviation());
        states.add(UsState.INDIANA.getAbbreviation());
        states.add(UsState.IOWA.getAbbreviation());
        states.add(UsState.LOUISIANA.getAbbreviation());
        states.add(UsState.KENTUCKY.getAbbreviation());
        states.add(UsState.MAINE.getAbbreviation());
        states.add(UsState.MARYLAND.getAbbreviation());
        states.add(UsState.MASSACHUSETTS.getAbbreviation());
        states.add(UsState.MICHIGAN.getAbbreviation());
        states.add(UsState.MINNESOTA.getAbbreviation());
        states.add(UsState.MISSISSIPPI.getAbbreviation());
        states.add(UsState.MISSOURI.getAbbreviation());
        states.add(UsState.MONTANA.getAbbreviation());
        states.add(UsState.NEBRASKA.getAbbreviation());
        states.add(UsState.NEVADA.getAbbreviation());
        states.add(UsState.NEW_HAMPSHIRE.getAbbreviation());
        states.add(UsState.NEW_JERSEY.getAbbreviation());
        states.add(UsState.NEW_MEXICO.getAbbreviation());
        states.add(UsState.NEW_YORK.getAbbreviation());
        states.add(UsState.NORTH_CAROLINA.getAbbreviation());
        states.add(UsState.NORTH_DAKOTA.getAbbreviation());
        states.add(UsState.OHIO.getAbbreviation());
        states.add(UsState.OKLAHOMA.getAbbreviation());
        states.add(UsState.OREGON.getAbbreviation());
        states.add(UsState.PENNSYLVANIA.getAbbreviation());
        states.add(UsState.PUERTO_RICO.getAbbreviation());
        states.add(UsState.RHODE_ISLAND.getAbbreviation());
        states.add(UsState.SOUTH_CAROLINA.getAbbreviation());
        states.add(UsState.SOUTH_DAKOTA.getAbbreviation());
        states.add(UsState.TENNESSEE.getAbbreviation());
        states.add(UsState.TEXAS.getAbbreviation());
        states.add(UsState.UTAH.getAbbreviation());
        states.add(UsState.VERMONT.getAbbreviation());
        states.add(UsState.VIRGINIA.getAbbreviation());
        states.add(UsState.WASHINGTON.getAbbreviation());
        states.add(UsState.WEST_VIRGINIA.getAbbreviation());
        states.add(UsState.WISCONSIN.getAbbreviation());
        states.add(UsState.WYOMING.getAbbreviation());
        
        FXCollections.sort(states);
        statePicker.setItems(states);
    }
}
