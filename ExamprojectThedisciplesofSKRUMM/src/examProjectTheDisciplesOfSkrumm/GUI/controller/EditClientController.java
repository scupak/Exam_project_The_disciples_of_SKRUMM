/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 * FXML Controller class
 *
 * @author Zanaxdk <https://github.com/zanaxdk>
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
    private AdminClientsAndProjectsController adminClientsAndProjectsController;
     

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
       try
        {
            modelfacade = ModelFacade.getInstance();
        } catch (Exception ex)
        {
            Logger.getLogger(CreateTaskController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void HandleEditOkButton(ActionEvent event) throws SQLException
    {
        String clientName = EditClientNameTextField.getText();
        String clientRate = ClientEditRateTextField.getText();
        int intClientRate;
        
        try{
            intClientRate = Integer.parseInt(clientRate);
            }
            catch(NumberFormatException e) {
            intClientRate = 0;
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("error");
                alert.setHeaderText("error");
                alert.setContentText(e.toString() + "rate has to be a number");

                alert.showAndWait();
            }
        
        Client client = new Client(this.client.getId(), clientName, intClientRate, this.client.getIsPaid());
        
        try{
        if(modelfacade.updateClient(client) == false)
        {
            throw new NullPointerException();
        }
         if(adminClientsAndProjectsController != null)
                {
                adminClientsAndProjectsController.RefreshTableView();
                }
         Stage editClientView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        editClientView.close();
        }catch(NullPointerException e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("error");
                alert.setHeaderText("error");
                alert.setContentText(e.toString());

                alert.showAndWait();
        }
      
        
        
        
       
    }

    @FXML
    private void HandleEditCancelButton(ActionEvent event)
    {
        Stage editClientView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        editClientView.close();
    }

    
    public void setClient(Client client)
    {
     this.client = client;
     
     EditClientNameTextField.setText(client.getClientName());
     ClientEditRateTextField.setText(client.getClientRate() + "");
    }

     public AdminClientsAndProjectsController getAdminClientsAndProjectsController()
    {
        return adminClientsAndProjectsController;
    }

    public void setAdminClientsAndProjectsController(AdminClientsAndProjectsController adminClientsAndProjectsController)
    {
        this.adminClientsAndProjectsController = adminClientsAndProjectsController;
    }
}
