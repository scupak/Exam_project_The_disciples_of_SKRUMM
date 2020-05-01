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
    @FXML
    private ImageView IspaidImageView;
    private int isPaidNum;

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
        isPaidNum = 1;
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
                addprojectviewcontroller.refreshClientComboBox();
                Stage addClientView = (Stage) ((Node) event.getSource()).getScene().getWindow();
                addClientView.close();
            }catch (NumberFormatException ex)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Oops");
                alert.setHeaderText("Incorrect input");
                alert.setContentText("You wrote a letter in client rate, it needs a number.");
                alert.showAndWait();
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
                addprojectviewcontroller.refreshClientComboBox();
                Stage addClientView = (Stage) ((Node) event.getSource()).getScene().getWindow();
                addClientView.close();
            }catch (NumberFormatException ex)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Oops");
                alert.setHeaderText("Incorrect input");
                alert.setContentText("You wrote a letter in client rate, it needs a number.");
                alert.showAndWait();
            }
            
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("Incorrect input");
            alert.setContentText("You didnt write a correct client name or rate");
            alert.showAndWait();
        }
        
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
