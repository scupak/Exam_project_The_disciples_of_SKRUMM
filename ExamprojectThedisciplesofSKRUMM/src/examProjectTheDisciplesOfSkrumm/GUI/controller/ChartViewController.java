/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author lumby
 */
public class ChartViewController implements Initializable
{

    @FXML
    private JFXDatePicker endDate;
    @FXML
    private BarChart<?, ?> hoursChart;
    @FXML
    private Label nameLabel;
  
    @FXML
    private JFXButton backBtn;
    @FXML
    private JFXDatePicker startDate;
    @FXML
    private AnchorPane anchorPane;
    
    private ModelFacadeInterface modelfacade;
    
    private Project currentProject;
    @FXML
    private CategoryAxis xAxisInBarChart;
    
  
   @Override 
    public void initialize(URL url, ResourceBundle rb) {
        try {
            modelfacade = ModelFacade.getInstance();
            
        } catch (Exception ex) {
            Logger.getLogger(ChartViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        startDate.setValue(LocalDate.now());
        endDate.setValue(LocalDate.now());
        handleBarChart();
    }   
    
    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        Stage chartView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/MainView.fxml"));
        Parent root = loader.load();
        MainViewController Controller = loader.getController();
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("TimeTracker");
        stage.show();
        chartView.close();
    }
    
    public void handleBarChart()
    {
        if(currentProject != null){
            
            try {
                hoursChart.getData().clear();
                hoursChart.getData().add(modelfacade.handleProjectBarChartDataForAdmin(currentProject.getId(),startDate.getValue(),endDate.getValue()));
            } catch (SQLException ex) {
                Logger.getLogger(ChartViewController.class.getName()).log(Level.SEVERE, null, ex);
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("SQLException");
                alert.setHeaderText("SQLException");
                alert.setContentText(ex.toString());
                alert.showAndWait();
             }
            
        
        
        
        
        
        }
        else{
        
        try {
            /*
            XYChart.Series data = new XYChart.Series();
            data.setName("Hours Spend");
            
            //data
            data.getData().add(new XYChart.Data(DayOfWeek.MONDAY.toString(), 5));
            data.getData().add(new XYChart.Data(DayOfWeek.TUESDAY.toString(), 10));
            data.getData().add(new XYChart.Data(DayOfWeek.WEDNESDAY.toString(), 12));
            data.getData().add(new XYChart.Data(DayOfWeek.THURSDAY.toString(), 7));
            data.getData().add(new XYChart.Data(DayOfWeek.FRIDAY.toString(), 6));
            */
            hoursChart.getData().clear();
            hoursChart.getData().add(modelfacade.handleProjectBarChartData(modelfacade.getCurrentuser().getEmail(),startDate.getValue(),endDate.getValue()));
        } catch (SQLException ex) {
            Logger.getLogger(ChartViewController.class.getName()).log(Level.SEVERE, null, ex);
            
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("SQLException");
                alert.setHeaderText("SQLException");
                alert.setContentText(ex.toString());
                alert.showAndWait();
        }
        }
        
    }

    @FXML
    private void handleDatepickerAction(ActionEvent event) 
    {
        handleBarChart();
    }

    public Project getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(Project currentProject) {
        this.currentProject = currentProject;
        handleBarChart();
        
    }

    public JFXButton getBackBtn() {
        return backBtn;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public CategoryAxis getxAxisInBarChart() {
        return xAxisInBarChart;
    }
    
    
            
    
}
