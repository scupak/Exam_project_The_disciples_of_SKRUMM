
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SKRUMM
 */
public class EditUserController implements Initializable
{
    private ModelFacadeInterface modelfacade;
    
    private User oldUser;
    
    private User newUser;

    @FXML
    private JFXButton createBtn;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private RadioButton adminRadioButton;
    @FXML
    private JFXTextField emailTextField;
    @FXML
    private JFXPasswordField passwordTextField;
    @FXML
    private JFXTextField firstNameTextField;
    @FXML
    private JFXTextField lastNameTextField;
    
    
   


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
         try {
            modelfacade = ModelFacade.getInstance();
        } catch (Exception ex) {
            Logger.getLogger(CreateTaskController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    /**
     * Creates a new instance of user and then gives it to the update user method
     * @param event
     * @throws SQLException 
     */
    @FXML
    private void handleCreateUser(ActionEvent event) throws SQLException
    {
        if(!emailTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty() && !firstNameTextField.getText().isEmpty() && !lastNameTextField.getText().isEmpty())
        {
            if(validateInput(emailTextField.getText()) == true)
            {
                String email = emailTextField.getText();
                String firstName = firstNameTextField.getText();
                String lastName = lastNameTextField.getText();
                String password =  modelfacade.hashPassword(passwordTextField.getText());
                boolean admin = adminRadioButton.isSelected();
                User newUser = new User(email, firstName, lastName,password, admin);
                modelfacade.updateUser(oldUser, newUser);
                Stage editUserView = (Stage) ((Node) event.getSource()).getScene().getWindow();
                editUserView.close();
            }
               
        }
        else if(!emailTextField.getText().isEmpty() && passwordTextField.getText().isEmpty() && !firstNameTextField.getText().isEmpty() && !lastNameTextField.getText().isEmpty())
        {
            if(validateInput(emailTextField.getText()) == true)
            {
                String email = emailTextField.getText();
                String firstName = firstNameTextField.getText();
                String lastName = lastNameTextField.getText();
                String password =  oldUser.getPassword();
                boolean admin = adminRadioButton.isSelected();
                User newUser = new User(email, firstName, lastName,password, admin);
                modelfacade.updateUser(oldUser, newUser);
                Stage editUserView = (Stage) ((Node) event.getSource()).getScene().getWindow();
                editUserView.close();
            }
            else 
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Oops");
                alert.setHeaderText("Incorrect input");
                alert.setContentText("You didnt write a correct email");
                alert.showAndWait();
            }    
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("Incorrect input");
            alert.setContentText("You didnt write an email, first name or last name");
            alert.showAndWait();
        }
        
    }

    /**
     * Closes the view
     * @param event 
     */
    @FXML
    private void handleCancel(ActionEvent event)
    {
        Stage createUserView = (Stage) ((Node) event.getSource()).getScene().getWindow();
         createUserView.close();
    }
    
    
     /**
      * Checks if the input is a valid email
      * @param input
      * @return 
      */
     public boolean validateInput(String input) {
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) 
        {
            return true;
        } else
        {
            return false;
        }
    }
     
     /**
      * Sets the field to the information of the user that is being edited
      * @param user 
      */
     public void setEditUser(User user)
     {  
        oldUser = user;
        emailTextField.setText(oldUser.getEmail());
        firstNameTextField.setText(oldUser.getFirstName());
        lastNameTextField.setText(oldUser.getLastName());
        if(oldUser.getIsAdmin())
        {
            adminRadioButton.setSelected(true);
        }
         
     }
    
    
}

