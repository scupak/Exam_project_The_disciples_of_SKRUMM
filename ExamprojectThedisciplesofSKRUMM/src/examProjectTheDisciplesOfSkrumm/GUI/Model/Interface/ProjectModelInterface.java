/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model.Interface;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

/**
 *
 * @author kacpe
 */
public interface ProjectModelInterface 
{
    public ObservableList<Project> getProjects();
    
    public void setProjects(ObservableList<Project> projects); 
    
    public void CreateProject(Project project);
    
    public boolean deleteProject(Project project) throws SQLException;
    
    public List<Project> getAllProjects() throws SQLServerException, SQLException;
    
    public boolean updateProject(Project project) throws SQLServerException, SQLException;
    
    public ObservableList<Project> getProjectsForClient(Client client) throws SQLException;
    
    public XYChart.Series handleProjectBarChartData(String userID,LocalDate fromdate, LocalDate todate) throws SQLException;
     
    public XYChart.Series handleProjectBarChartDataForAdmin(int projectID,LocalDate fromdate, LocalDate todate) throws SQLException;
    
}
