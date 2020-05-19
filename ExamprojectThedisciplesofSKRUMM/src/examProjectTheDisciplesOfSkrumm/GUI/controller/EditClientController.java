
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author SKRUMM
 */
public class EditClientController implements Initializable
{

    @FXML
    private JFXButton EditOkButton;
    @FXML
    private JFXButton EditCancelButton;
    @FXML
    private JFXTextField EditClientNameTextField;
    @FXML
    private JFXTextField ClientEditRateTextField;
    
    private Client client;
    private ModelFacadeInterface modelfacade;
     

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
       try
        {
            modelfacade = ModelFacade.getInstance();
        } 
        catch (Exception ex)
        {
            Logger.getLogger(CreateTaskController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed to get an intance of modelfacade" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Creates a new client instance and the sends it to the update client method
     * @param event
     * @throws SQLException 
     */
    @FXML
    private void HandleEditOkButton(ActionEvent event)
    {
        String clientName = EditClientNameTextField.getText();
        String clientRate = ClientEditRateTextField.getText();
        int intClientRate;
        
        try
        {
            intClientRate = Integer.parseInt(clientRate);
        }
        catch(NumberFormatException ex)
        {
            intClientRate = 0;
            Logger.getLogger(CreateTaskController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Rate has to be a number" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        
        Client client = new Client(this.client.getId(), clientName, intClientRate, this.client.getIsPaid());
        
        try
        {
            if(modelfacade.updateClient(client) == false)
            {
                throw new NullPointerException();
            }
            
            Stage editClientView = (Stage) ((Node) event.getSource()).getScene().getWindow();
            editClientView.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EditClientController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed to contact the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        catch(NullPointerException ex)
        {
            Logger.getLogger(CreateTaskController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "The client you were editing does not exist anymore" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Closes the window
     * @param event 
     */
    @FXML
    private void HandleEditCancelButton(ActionEvent event)
    {
        Stage editClientView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        editClientView.close();
    }

    
    /**
     * Sets the textfield to the information of the client currently being edited
     * @param client 
     */
    public void setClient(Client client)
    {
     this.client = client;
     
     EditClientNameTextField.setText(client.getClientName());
     ClientEditRateTextField.setText(client.getClientRate() + "");
    }
}
