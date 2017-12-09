package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.UsState;
import com.gitrekt.resort.model.entities.Booking;
import com.gitrekt.resort.model.entities.Guest;
import com.gitrekt.resort.model.entities.MailingAddress;
import com.gitrekt.resort.model.entities.Package;
import com.gitrekt.resort.model.entities.Room;
import com.gitrekt.resort.model.entities.RoomCategory;
import com.gitrekt.resort.model.services.BookingService;
import com.gitrekt.resort.model.services.GuestService;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.persistence.NoResultException;

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

    private ObservableList<String> countries;

    private ObservableList<String> states;

    private Map<RoomCategory, Integer> roomsInfo;

    private Map<Package, Integer> packagesInfo;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    /**
     * Called by JavaFX.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeStatePicker();
        initializeCountryPicker();
    }

    /**
     * Initializes the information needed to place the booking.
     *
     * @param roomsInfo The map of the list of room categories and the quantity of each category
     * to book.
     *
     * @param packagesInfo The info on the selected packages for the booking.
     */
    public void initializeData(Map<RoomCategory, Integer> roomsInfo,
            Map<Package, Integer> packagesInfo, LocalDate checkInDate, LocalDate checkOutDate) {

        this.roomsInfo = roomsInfo;
        this.packagesInfo = packagesInfo;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
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
            // Gather the form data
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            String addressLine1 = addressLine1Field.getText().trim();
            String addressLine2 = addressLine2Field.getText().trim();
            String city = cityField.getText().trim();
            String country = countryPicker.getValue().trim();
            String postalCode = postalCodeField.getText().trim();
            String specialInstructions = specialInstructionsBox.getText().trim();

            // Mailing address shouold not include a state if the guest doesn't live in the US
            MailingAddress mailingAddress;
            if(country.equals("United States")) {
                UsState state = UsState.parse(statePicker.getValue());
                mailingAddress = new MailingAddress(
                    addressLine1, addressLine2, city, postalCode, state, country
                );
            } else {
                mailingAddress = new MailingAddress(
                    addressLine1, addressLine2, city, postalCode, null, country
                );
            }

            // Gather the needed data to create the booking
            BookingService bookingService = new BookingService();
            Date checkin = Date.from(
                checkInDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
            );
            Date checkout = Date.from(
                checkOutDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
            );
            List<Package> packagesToBook = getListOfPackagesToBook();
            List<Room> roomsToBook;
            try {
                roomsToBook = getListOfRoomsToBook(checkin, checkout);
            } catch (IllegalStateException e) {
                showNoMoreRoomsInCategoryErrorDialog();
                return;
            }
            Guest guest = findOrCreateGuest(firstName, lastName, email, mailingAddress);

            Booking booking = new Booking(
                guest, checkin, checkout, specialInstructions, packagesToBook, roomsToBook
            );

            bookingService.createBooking(booking);
            displayBookingNumberAndReturnHome();
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
     * The state picker is set to be hidden at first;
     * The program should unhide the state picker when it is determined that the user is in the
     * United States.
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
        boolean isValid = true; // Form is innocent until proven guilty

        // Gather all trimmed input from all fields
        String email = emailField.getText().trim();
        String confirmedEmail = confirmEmailField.getText().trim();

        if(!confirmedEmail.equals(email) || email.isEmpty()) {
            isValid = false;
            showEmailAddressedDoNotMatchError();
        }
        if(!ensureRequiredFieldsNotEmpty()) {
            isValid = false;
        }

        return isValid;
    }

    private Guest findOrCreateGuest(String firstName, String lastName, String email,
            MailingAddress mailingAddress) {

        GuestService guestService = new GuestService();
        Guest guest;
        try {
            guest = guestService.getGuestByEmailAddress(email);
            // Update the guest's mailing address while we are here.
            guest.setMailingAddress(mailingAddress);
            guestService.updateGuest(guest);
        } catch (NoResultException e) {
            guest = new Guest(firstName, lastName, email, mailingAddress);
            guestService.createNewGuest(guest);
        }
        return guestService.getGuestByEmailAddress(email);
    }

    /**
     * Returns the actual list of rooms that are to be booked by the system.
     */
    private List<Room> getListOfRoomsToBook(Date checkin, Date checkout) {
        BookingService bookingService = new BookingService();
        List<Room> availableRooms = bookingService.getAvailableRoomsBetweenDates(checkin, checkout);
        List<Room> roomsToBook = new ArrayList<>();

        for(Map.Entry<RoomCategory, Integer> roomInfo : roomsInfo.entrySet()) {
            RoomCategory desiredCategory = roomInfo.getKey();
            int desiredQty = roomInfo.getValue();
            while(desiredQty > 0) {
                boolean available = false;
                for(Room r : availableRooms) {
                    if(r.getRoomCategory().equals(desiredCategory)) {
                        available = true;
                        roomsToBook.add(r);
                        availableRooms.remove(r);
                        desiredQty--;
                        break;
                    }
                }
                if(!available) {
                    throw new IllegalStateException(
                        "No more rooms available in desired category"
                    );
                }
            }
        }
        return roomsToBook;
    }

    private List<Package> getListOfPackagesToBook() {
        List<Package> packagesToBook = new ArrayList<>();
        // Because packages objects don't support a quantity booked, we need to do this.
        for(Map.Entry<Package, Integer> packageInfo : packagesInfo.entrySet()) {
            Package p = packageInfo.getKey();
            int qty = packageInfo.getValue();
            for(int i = 0; i < qty; i++) {
                packagesToBook.add(p);
            }
        }
        return packagesToBook;
    }

    private void showNoMoreRoomsInCategoryErrorDialog() {
        Alert errorDialog = new Alert(Alert.AlertType.ERROR);
            errorDialog.setTitle("Error");
            errorDialog.setHeaderText("Error Placing Booking");
            errorDialog.setContentText("We were unable to place your booking because there"
                    + " were not enough rooms of the type you requested available. "
                    + "This can happen if somebody else booked the rooms you were trying to"
                    + " before you completed your booking."
                    + "\n Please return to the previous screen and try again.");
            errorDialog.showAndWait();
    }

    private void displayBookingNumberAndReturnHome() {
        Alert bookingSuccessDialog = new Alert(AlertType.INFORMATION);
        bookingSuccessDialog.setHeaderText("Booking Complete");
        bookingSuccessDialog.setContentText(
            "Your booking has been successfully placed. Please check your email to receive your"
                + " booking number."
        );
        bookingSuccessDialog.showAndWait();

        // Once the user has closed the dialog...
        ScreenManager.getInstance().switchToScreen("/fxml/HomeScreen.fxml");
    }

    private boolean ensureRequiredFieldsNotEmpty() {
        // Gather trimmed form data from all required fields.
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String addressLine1 = addressLine1Field.getText().trim();
        String city = cityField.getText().trim();
        String country = countryPicker.getValue();
        String postalCode = postalCodeField.getText().trim();

        boolean requiredFieldsNotEmpty = true; // Innocent until proven guilty

        // Check if required fields are empty
        if(firstName.isEmpty()
                || lastName.isEmpty()
                || addressLine1.isEmpty()
                || city.isEmpty()
                || country.isEmpty()
                || postalCode.isEmpty()) {
            requiredFieldsNotEmpty = false;
        }

        if(requiredFieldsNotEmpty==false) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing Required Fields");
            alert.setContentText("Please ensure all fields are filled.");
            alert.showAndWait();
        }

        return requiredFieldsNotEmpty;
    }

    private void showEmailAddressesDoNotMatchError() {
        Alert error = new Alert(AlertType.ERROR);
        error.setTitle("Error");
        error.setContentText("Email addresses do not match.");
        error.showAndWait();
    }
}
