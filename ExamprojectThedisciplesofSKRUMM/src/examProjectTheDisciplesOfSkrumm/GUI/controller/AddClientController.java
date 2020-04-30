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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Zanaxdk <https://github.com/zanaxdk>
 */
public class AddClientController implements Initializable
{

    private ModelFacadeInterface modelfacade;
    
    private AddProjectViewController addprojectviewcontroller;
    
    @FXML
    private JFXButton AddClientOkBtn;
    @FXML
    private JFXButton AddClientCancelBtn;
    @FXML
    private JFXTextField ClientNameTextField;
    
    
    @FXML
    private JFXTextField ClientRateTextField;

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

    @FXML
    private void HandleAddClientOkBtn(ActionEvent event)
    {
        String ClientName = ClientNameTextField.getText();
        int ClientRate = Integer.parseInt(ClientRateTextField.getText());
        
        Client client = new Client(ClientName, ClientRate);
        modelfacade.createClient(client);
        addprojectviewcontroller.refreshClientComboBox();
        Stage addClientView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addClientView.close();
    }

    @FXML
    private void HandleAddClientCancelBtn(ActionEvent event)
    {
        Stage addClientView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addClientView.close();
    }
    
    void setAddProjectController( AddProjectViewController addprojectviewcontroller)
    {
        this.addprojectviewcontroller = addprojectviewcontroller;
    }

}
