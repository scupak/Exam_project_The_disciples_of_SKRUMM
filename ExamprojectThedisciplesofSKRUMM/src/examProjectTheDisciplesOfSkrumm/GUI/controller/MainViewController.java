/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Christina
 */
public class MainViewController implements Initializable
{

    @FXML
    private JFXButton AdminBtn;
    private GridPane taskGrid;

   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        /*
        ColumnConstraints halfConstraint = new ColumnConstraints(50);
        taskGrid.getColumnConstraints().addAll(halfConstraint,halfConstraint); 
        */

    }
       
    @FXML
    private void handlecChartView(ActionEvent event) throws IOException
    {
        Stage mainView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/ChartView.fxml"));
        Parent root = loader.load();
        ChartViewController Controller = loader.getController();
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setMinHeight(423);
        stage.setMinWidth(721);
        stage.setTitle("TimeTracker");
        stage.show();
        mainView.close();
        
    }

    @FXML
    private void handlecAdminView(ActionEvent event) {
    }

}
