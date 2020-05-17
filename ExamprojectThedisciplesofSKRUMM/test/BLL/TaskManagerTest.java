/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;


import examProjectTheDisciplesOfSkrumm.BLL.Interface.TaskManagerInterface;
import examProjectTheDisciplesOfSkrumm.BLL.TaskManager;
import examProjectTheDisciplesOfSkrumm.DAL.DALFacadeFactory;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author anton
 */
public class TaskManagerTest {
    
    public TaskManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
//    /**
//     * Test of convertSecToTimeString method, of class TaskManager.
//     */
    @Test
    public void convertSecToTimeStringTest() throws Exception {
        //Arrange
        System.out.println("convertSecToTimeString");
        int totalSec = 3600;
        TaskManagerInterface instance = new TaskManager(DALFacadeFactory.CreateDALFacade(DALFacadeFactory.DALFacadeTypes.MOCK));
        String expResult = "01:00:00";
        //Act
        String result = instance.convertSecToTimeString(totalSec);
       
        //Assert
        assertEquals(expResult, result);        
    }
    
//    /**
//     * Test of handleProjectBarChartDataTest method, of class TaskManager.
//     */
    @Test
    public void handleProjectBarChartDataTest() throws Exception {
        //Arrange
        System.out.println("handleProjectBarChartData");
        String userID = "1";
        LocalDate fromdate = LocalDate.now();
        LocalDate todate = LocalDate.now();
        
        TaskManagerInterface instance = new TaskManager(DALFacadeFactory.CreateDALFacade(DALFacadeFactory.DALFacadeTypes.MOCK));
        XYChart.Series dataIN = new XYChart.Series();
        dataIN.setName("Hours spent on projects");
        dataIN.getData().add(new XYChart.Data("TestProject", (3600/ 3600.0)));
        String expResult = dataIN.getData().get(0).toString();
        //Act
        XYChart.Series dataOUT = instance.handleProjectBarChartData(userID, fromdate, todate);
        String result = dataOUT.getData().get(0).toString();
        //Assert
        assertEquals(expResult, result);        
    }
    
    //    /**
//     * Test of handleProjectBarChartDataForAdmin method, of class TaskManager.
//     */
    @Test
    public void handleProjectBarChartDataForAdminTest() throws Exception {
        //Arrange
        System.out.println("handleProjectBarChartDataForAdmin");
        String userID = "1";
        int projectID = 1;
        LocalDate fromdate = LocalDate.now();
        LocalDate todate = LocalDate.now();
        
        TaskManagerInterface instance = new TaskManager(DALFacadeFactory.CreateDALFacade(DALFacadeFactory.DALFacadeTypes.MOCK));
        XYChart.Series dataIN = new XYChart.Series();
        dataIN.setName("Hours this user spent on the project");
        dataIN.getData().add(new XYChart.Data("TestEmail1", (3600/ 3600.0)));
        String expResult = dataIN.getData().get(0).toString();
        //Act
        XYChart.Series dataOUT = instance.handleProjectBarChartDataForAdmin(projectID, fromdate, todate);
        String result = dataOUT.getData().get(0).toString();
        //Assert
        assertEquals(expResult, result);        
    }
}
