package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import examProjectTheDisciplesOfSkrumm.enums.ViewTypes;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML controller class
 *
 * @author SKRUMM
 */
public class ChartViewController implements Initializable
{

    @FXML
    private JFXDatePicker endDate;
    @FXML
    private BarChart<String, Number> hoursChart;
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

    /**
     * Initialises the modelfacade and sets the default stardate and enddate.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            modelfacade = ModelFacade.getInstance();
            
        } catch (Exception ex) 
        {
            Logger.getLogger(ChartViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed to get the intance of modelfacade" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        
        
        xAxisInBarChart.setAnimated(false);
        startDate.setValue(LocalDate.now());
        endDate.setValue(LocalDate.now());
        handleBarChart();
    }

    /**
     * Sends the user back to the mainview.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void handleBack(ActionEvent event)
    {
        try
        {
            Stage chartView = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            FXMLLoader loader = modelfacade.getLoader(ViewTypes.MAIN);
            Parent root = loader.load();
            MainViewController Controller = loader.getController();
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("TimeTracker");
            stage.show();
            chartView.close();
        } 
        catch (IOException ex)
        {
            Logger.getLogger(ChartViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed to load in the main view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        } 
        catch (Exception ex)
        {
            Logger.getLogger(ChartViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Given wrong view type" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Adds the data to the barchart.
     */
    public void handleBarChart()
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> 
        {
            if(currentProject != null)
            {
                try 
                {
                    XYChart.Series stuff = modelfacade.handleProjectBarChartDataForAdmin(currentProject.getId(),startDate.getValue(),endDate.getValue());
                    
                    Platform.runLater( () ->
                    {
                       hoursChart.getData().clear();
                       hoursChart.getData().add(stuff);

                       for (final Series<String, Number> series : hoursChart.getData())
                        {
                            for (final XYChart.Data<String, Number> data : series.getData())
                            {
                                Tooltip tooltip = new Tooltip();
                                tooltip.setText(modelfacade.convertSecToTimeString(((int) Math.round(data.getYValue().doubleValue() * 3600.0))));
                                Tooltip.install(data.getNode(), tooltip);
                                
                                
                                
                                
                            }
                        }
                    });
                }
                catch (SQLException ex)
                {
                    Platform.runLater( () ->
                    {
                       Logger.getLogger(ChartViewController.class.getName()).log(Level.SEVERE, null, ex);
                       JOptionPane.showMessageDialog(null, "Failed to get data from database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
                    });
                }
            }
            else
            {
                try 
                {
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
                    
                    XYChart.Series stuff = modelfacade.handleProjectBarChartData(modelfacade.getCurrentuser().getEmail(),startDate.getValue(),endDate.getValue());
                    
                    Platform.runLater( () ->
                    {
                       hoursChart.getData().clear();
                       hoursChart.getData().add(stuff);
                       for (final Series<String, Number> series : hoursChart.getData()) {
                        for (final XYChart.Data<String, Number> data : series.getData()) {
                            Tooltip tooltip = new Tooltip();
                            
                            DecimalFormat f = new DecimalFormat("##.00");
                            tooltip.setText(modelfacade.convertSecToTimeString(((int) Math.round(data.getYValue().doubleValue() * 3600.0))));
                            Tooltip.install(data.getNode(), tooltip);
                        }
                            }

                       
                    });
                }
                catch (SQLException ex) 
                {
                    Platform.runLater( () ->
                    {
                       Logger.getLogger(ChartViewController.class.getName()).log(Level.SEVERE, null, ex);
                       JOptionPane.showMessageDialog(null, "Failed to get data from database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
                    });
                }
            }       
        });
        executor.shutdown();
    }

    /**
     * Tells the program to refresh the barchart whenever the datepickers are
     * used.
     *
     * @param event
     */
    @FXML
    private void handleDatepickerAction(ActionEvent event)
    {
        handleBarChart();
    }

    /**
     * Getter for the current project variable
     *
     * @return
     */
    public Project getCurrentProject()
    {
        return currentProject;
    }

    /**
     * Setter for the current project variable
     *
     * @return
     */
    public void setCurrentProject(Project currentProject)
    {
        this.currentProject = currentProject;
        handleBarChart();

    }

    /**
     * Getter for the back button
     *
     * @return
     */
    public JFXButton getBackBtn()
    {
        return backBtn;
    }

    /**
     * Getter for the namelabel
     *
     * @return
     */
    public Label getNameLabel()
    {
        return nameLabel;
    }

    /**
     * Getter for the barchart
     *
     * @return
     */
    public CategoryAxis getxAxisInBarChart()
    {
        return xAxisInBarChart;
    }

    @FXML
    private void handleLastMonth(ActionEvent event)
    {
        startDate.setValue(LocalDate.now().minusMonths(1).withDayOfMonth(1));
        endDate.setValue(LocalDate.now().minusMonths(1).
                withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth()));
      
    }

    @FXML
    private void handleLastWeek(ActionEvent event)
    {
        startDate.setValue(LocalDate.now().minusWeeks(1).with(DayOfWeek.MONDAY));
        endDate.setValue(LocalDate.now().minusWeeks(1).with(DayOfWeek.SUNDAY));
    }

    @FXML
    private void handleCurrentWeek(ActionEvent event)
    {
        startDate.setValue(LocalDate.now().with(DayOfWeek.MONDAY));
        endDate.setValue(LocalDate.now().with(DayOfWeek.SUNDAY));
    }
    

}
