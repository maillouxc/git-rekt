package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.entities.Employee;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class.
 */
public class StaffAccountsScreenController implements Initializable {

    @FXML
    private Button backButton;
    
    @FXML
    private Button removeEmployeeButton;
    
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
    
    
    private ObservableList<Employee> staffAccountList;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        staffAccountList = FXCollections.observableArrayList();
        staffAccountsTableView.setItems(staffAccountList);
        
        employeeIdColumn.setCellValueFactory((param) -> {
            return new SimpleStringProperty(
                String.valueOf(param.getValue().getId())
            );
        });
        
        employeeNameColumn.setCellValueFactory((param) -> {
            return new SimpleStringProperty(
                param.getValue().getLastName() 
                    + ", " 
                    + param.getValue().getFirstName()
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
        
        // TODO: Remove test data
        
        Employee employee1 = new Employee(Long.valueOf(1),"1234", true, "Juan" , "Gomez");
        Employee employee2 = new Employee(Long.valueOf(2),"1234", true, "Juanito" , "Gomez");
        Employee employee3 = new Employee(Long.valueOf(3),"1234", false, "Juana" , "Gomez");
        Employee employee4 = new Employee(Long.valueOf(4),"1234", false, "Juanita" , "Gomez");
        Employee employee5 = new Employee(Long.valueOf(5),"1234", true, "Juanucho" , "Gomez");
        
        staffAccountList.add(employee1);
        staffAccountList.add(employee2);
        staffAccountList.add(employee3);
        staffAccountList.add(employee4);
        staffAccountList.add(employee5);
    }    
    
    public void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/StaffHomeScreen.fxml"
        );
    }
    
    /**
     * Displays the dialog to delete the currently selected employee account.
     */
    public void onRemoveEmployeeButtonClicked() {
        Employee selectedEmployee = getSelectedEmployee();
        // TODO: Display dialog to delete employee account.
    }
    
    /**
     * Displays the reset password dialog for the currently selected employee.
     * 
     * @throws IOException 
     */
    public void onResetEmployeePasswordButtonClicked() throws IOException {
        Stage dialogStage = new Stage();
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/fxml/ResetEmployeePasswordDialog.fxml")
        );
        Parent dialogRoot = loader.load();
        Scene resetPasswordDialog = new Scene(dialogRoot);
        
        dialogStage.setScene(resetPasswordDialog);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(
            resetEmployeePasswordButton.getScene().getWindow()
        );
        dialogStage.setResizable(false);
        dialogStage.setTitle("Confirm");
        dialogStage.centerOnScreen();
        
        ResetEmployeePasswordDialogController c;
        c = (ResetEmployeePasswordDialogController) loader.getController();
        long employeeId = getSelectedEmployee().getId();
        c.setEmployeeId(employeeId);
        
        dialogStage.show();
    }
    
    public void onAddNewEmployeeButtonClicked() throws IOException {
        Stage dialogStage = new Stage();
        Parent dialogRoot = FXMLLoader.load(
            getClass().getResource("/fxml/CreateStaffAccountDialog.fxml")
        );
        Scene createStaffAccountDialog = new Scene(dialogRoot);
        dialogStage.setScene(createStaffAccountDialog);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(
            addNewEmployeeButton.getScene().getWindow()
        );
        dialogStage.setResizable(false);
        dialogStage.setTitle("Create employee");
        dialogStage.centerOnScreen();
        
        dialogStage.show();
    }
    
    /**
     * @return The currently selected employee in the employee table view. 
     */
    private Employee getSelectedEmployee() {
        Employee selectedEmployee = 
                staffAccountsTableView.getSelectionModel().getSelectedItem();
        return selectedEmployee;
    }
    
}
