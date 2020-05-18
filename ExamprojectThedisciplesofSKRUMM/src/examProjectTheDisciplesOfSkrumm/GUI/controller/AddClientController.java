
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
 * This is the class that controls the addClient view. 
 * FXML Controller class
 *
 * @author SKRUMM
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
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
       
        try
        {
            //The modelfacade gets instantieted here.
            modelfacade = ModelFacade.getInstance();
            isPaidNum = 1;
        }
        catch (Exception ex) //This exception happens if for some reason the program is unable to get the instance of modelfacade, this is probably unnecessary
        {
            Logger.getLogger(AddClientController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couldn't get the instance of modelfacade" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * This method detects when the user has pressed the ok button and then atempts to make a new client. 
     * @param event 
     */
    @FXML
    private void HandleAddClientOkBtn(ActionEvent event)
    {
        if(!ClientNameTextField.getText().isEmpty() && !ClientRateTextField.getText().isEmpty() && isPaidNum == 1)
        {
            try
            {
                //The method gets all the input from the textfields and then makes a new instance of a client with this data.
                int id = 1;
                String ClientName = ClientNameTextField.getText();
                int ClientRate = Integer.parseInt(ClientRateTextField.getText());
                int isPaid = isPaidNum;
        
                Client client = new Client(id, ClientName, ClientRate, isPaid);
                //This new client is then sent down to the database
                modelfacade.createClient(client);
                
                //The window is closed
                Stage addClientView = (Stage) ((Node) event.getSource()).getScene().getWindow();
                addClientView.close();
            }
            catch (NumberFormatException ex) //If the user enters a letter in the rate textfield, then this error is triggered and handled here.
            {
                Logger.getLogger(AddClientController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "You wrote a letter in the rate textfield it needs to be a number" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        else if(!ClientNameTextField.getText().isEmpty() && isPaidNum == 0) //If the user wants to make a rateless client then the method needs to accept an empty rate textfield.
        {
            try
            {
                //Same procedure as before. 
                int id = 1;
                String ClientName = ClientNameTextField.getText();
                int ClientRate = 0;
                int isPaid = isPaidNum;
        
                Client client = new Client(id, ClientName, ClientRate, isPaid);
                modelfacade.createClient(client);
               
                Stage addClientView = (Stage) ((Node) event.getSource()).getScene().getWindow();
                addClientView.close();
            }
            catch (NumberFormatException ex)// check for wierdness again. 
            {
                Logger.getLogger(AddClientController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "You wrote a letter in the rate textfield it needs to be a number" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        else
        {
            //This shows up if the user forgot some needed input.
            JOptionPane.showMessageDialog(null, "Missing input, you need to write something","ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    /**
     * This method closes the view if the user regrets coming here. 
     * @param event 
     */
    @FXML
    private void HandleAddClientCancelBtn(ActionEvent event)
    {
        Stage addClientView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addClientView.close();
    }
    
   

    /**
     * This method disables and enables the rate textfield based on if the client is paid or not. 
     * @param event 
     */
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
