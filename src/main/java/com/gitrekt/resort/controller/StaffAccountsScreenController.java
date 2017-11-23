package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.entities.Employee;
import com.gitrekt.resort.model.services.EmployeeService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class for the "Manage Staff Accounts" screen.
 */
public class StaffAccountsScreenController implements Initializable {

    @FXML
    private Button resetEmployeePasswordButton;

    @FXML
    private Button addNewEmployeeButton;

    @FXML
    private TableView<Employee> staffAccountsTableView;

    @FXML
    private TableColumn<Employee,String> employeeNameColumn;

    @FXML
    private TableColumn<Employee,Boolean> isManagerColumn;

    @FXML
    private TableColumn<Employee,String> employeeIdColumn;

    private ObservableList<Employee> staffAccountsList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        staffAccountsList = FXCollections.observableArrayList();
        staffAccountsTableView.setItems(staffAccountsList);

        employeeIdColumn.setCellValueFactory((param) -> {
            return new SimpleStringProperty(
                String.valueOf(param.getValue().getId())
            );
        });

        employeeNameColumn.setCellValueFactory((param) -> {
            return new SimpleStringProperty(
                param.getValue().getLastName() + ", " + param.getValue().getFirstName()
            );
        });

        isManagerColumn.setCellValueFactory((param) -> {
            return new SimpleBooleanProperty(param.getValue().isManager());
        });

        // Display the boolean column using checkboxes instead of strings
        isManagerColumn.setCellFactory(
            (param) -> {
                return new CheckBoxTableCell<>();
            }
        );

        staffAccountsTableView.setPlaceholder(
            new Label("We fired everyone")
        );

        fetchTableData();
    }

    /**
     * Returns to the previous screen.
     */
    @FXML
    private void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen("/fxml/StaffHomeScreen.fxml");
    }

    /**
     * Displays the dialog to delete the currently selected employee account.
     */
    @FXML
    private void onRemoveEmployeeButtonClicked() {
        Employee selectedEmployee = getSelectedEmployee();
        EmployeeService employeeService = new EmployeeService();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Remove Employee Confirmation");
        alert.setHeaderText("Warning");
        alert.setContentText("Do you wish to remove selected employee?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            employeeService.deleteEmployee(selectedEmployee);

            // TODO REMOVE TEST CODE
            List<Employee> employees = employeeService.getAllEmployees();
            for(Employee employ : employees) {
                System.out.println("After delete we found: " + employ.getId());
            }

            staffAccountsList.remove(selectedEmployee);
        }
    }

    /**
     * Displays the reset password dialog for the currently selected employee.
     *
     * @throws IOException
     */
    @FXML
    private void onResetEmployeePasswordButtonClicked() throws IOException {
        Stage dialogStage = new Stage();
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/fxml/ResetEmployeePasswordDialog.fxml")
        );
        Parent dialogRoot = loader.load();
        Scene resetPasswordDialog = new Scene(dialogRoot);
        dialogStage.getIcons().add(new Image("images/Logo.png"));
        dialogStage.setScene(resetPasswordDialog);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(resetEmployeePasswordButton.getScene().getWindow());
        dialogStage.setResizable(false);
        dialogStage.setTitle("Confirm");
        dialogStage.centerOnScreen();

        ResetEmployeePasswordDialogController c;
        c = (ResetEmployeePasswordDialogController) loader.getController();
        long employeeId = getSelectedEmployee().getId();
        c.setEmployeeId(employeeId);

        dialogStage.show();
    }

    /**
     * Displays the dialog to create a new employee account.
     *
     * @throws IOException
     */
    @FXML
    private void onAddNewEmployeeButtonClicked() throws IOException {
        Stage dialogStage = new Stage();
        Parent dialogRoot = FXMLLoader.load(
            getClass().getResource("/fxml/CreateStaffAccountDialog.fxml")
        );
        Scene createStaffAccountDialog = new Scene(dialogRoot);
        dialogStage.getIcons().add(new Image("images/Logo.png"));
        dialogStage.setScene(createStaffAccountDialog);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(addNewEmployeeButton.getScene().getWindow());
        dialogStage.setResizable(false);
        dialogStage.setTitle("Create employee");
        dialogStage.centerOnScreen();
        dialogStage.show();
    }

    /**
     * @return The currently selected employee in the employee table view.
     */
    private Employee getSelectedEmployee() {
        Employee selectedEmployee = staffAccountsTableView.getSelectionModel().getSelectedItem();
        return selectedEmployee;
    }

    /**
     * Retrieves the employee data from the database and populates the tableview
     * with it.
     */
    private void fetchTableData() {
        EmployeeService employeeService = new EmployeeService();
        staffAccountsList.addAll(employeeService.getAllEmployees());
    }

}
