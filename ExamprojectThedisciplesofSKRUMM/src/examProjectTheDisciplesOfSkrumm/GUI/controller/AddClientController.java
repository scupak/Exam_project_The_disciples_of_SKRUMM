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
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Zanaxdk <https://github.com/zanaxdk>
 */
public class AddClientController implements Initializable
{

    private ModelFacadeInterface modelfacade;
    
    @FXML
    private JFXButton AddClientOkBtn;
    @FXML
    private JFXButton AddClientCancelBtn;
    @FXML
    private JFXTextField ClientNameTextField;
    
    
    @FXML
    private JFXTextField ClientRateTextField;
    @FXML
    private ImageView IspaidImageView;
    
    private int isPaidNum;
    
   
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
       
        try
        {
            modelfacade = ModelFacade.getInstance();
            isPaidNum = 1;
        }
        catch (Exception ex)
        {
            Logger.getLogger(AddClientController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couldn't get the instance of modelfacade" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void HandleAddClientOkBtn(ActionEvent event)
    {
        if(!ClientNameTextField.getText().isEmpty() && !ClientRateTextField.getText().isEmpty() && isPaidNum == 1)
        {
            try
            {
                int id = 1;
                String ClientName = ClientNameTextField.getText();
                int ClientRate = Integer.parseInt(ClientRateTextField.getText());
                 int isPaid = isPaidNum;
        
                Client client = new Client(id, ClientName, ClientRate, isPaid);
                modelfacade.createClient(client);
                
               
                Stage addClientView = (Stage) ((Node) event.getSource()).getScene().getWindow();
                addClientView.close();
            }
            catch (NumberFormatException ex)
            {
                Logger.getLogger(AddClientController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "You wrote a letter in the rate textfield it needs to be a number" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        else if(!ClientNameTextField.getText().isEmpty() && isPaidNum == 0)
        {
            try
            {
                int id = 1;
                String ClientName = ClientNameTextField.getText();
                int ClientRate = 0;
                int isPaid = isPaidNum;
        
                Client client = new Client(id, ClientName, ClientRate, isPaid);
                modelfacade.createClient(client);
               
                Stage addClientView = (Stage) ((Node) event.getSource()).getScene().getWindow();
                addClientView.close();
            }
            catch (NumberFormatException ex)
            {
                Logger.getLogger(AddClientController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "You wrote a letter in the rate textfield it needs to be a number" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Missing input, you need to write something","ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    @FXML
    private void HandleAddClientCancelBtn(ActionEvent event)
    {
        Stage addClientView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addClientView.close();
    }
    
   

    @FXML
    private void handleIsPaid(MouseEvent event) 
    {
        Image Paid = new Image("/examProjectTheDisciplesOfSkrumm/GUI/Icons/Paid.png");
        Image NotPaid = new Image("/examProjectTheDisciplesOfSkrumm/GUI/Icons/NotPaid.png");
        
        if(isPaidNum == 0)
        {
            ClientRateTextField.setDisable(false);
            isPaidNum = 1;
            IspaidImageView.setImage(Paid);
            
        }
        else if (isPaidNum == 1)
        {
            ClientRateTextField.setDisable(true);
            isPaidNum = 0;
            IspaidImageView.setImage(NotPaid);
        }
    }
}
