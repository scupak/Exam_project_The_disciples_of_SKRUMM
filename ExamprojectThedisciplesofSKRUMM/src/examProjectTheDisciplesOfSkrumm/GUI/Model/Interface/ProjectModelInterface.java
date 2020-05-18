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
 * @author SKRUMM
 */
public interface ProjectModelInterface 
{
    /**
     * gets a list of all projects
     * @return a list of all projects
     */
    public ObservableList<Project> getProjects();
    
    /**
     * sets projects to the given list
     * @param projects 
     */
    public void setProjects(ObservableList<Project> projects); 
    
    /**
     * creates a project
     * @param project 
     */
    public void CreateProject(Project project);
    
    /**
     * deletes a project
     * @param project
     * @return true if successful, false otherwise
     * @throws SQLException 
     */
    public boolean deleteProject(Project project) throws SQLException;
    
    /**
     * gets a list of all projects
     * @return a list of all projects
     * @throws SQLServerException
     * @throws SQLException 
     */
    public List<Project> getAllProjects() throws SQLServerException, SQLException;
    
    /**
     * updates a project
     * @param project
     * @return true if successful, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    public boolean updateProject(Project project) throws SQLServerException, SQLException;
    
    /**
     * gets all projects for the given client
     * @param client
     * @return a list of projects for the given client
     * @throws SQLException 
     */
    public ObservableList<Project> getProjectsForClient(Client client) throws SQLException;
    
    /**
     * handles bar chart data
     * @param userID
     * @param fromdate
     * @param todate
     * @return bar chart data
     * @throws SQLException 
     */
    public XYChart.Series handleProjectBarChartData(String userID,LocalDate fromdate, LocalDate todate) throws SQLException;
     
    /**
     * handles bar chart data for the admin
     * @param projectID
     * @param fromdate
     * @param todate
     * @return bar chart data for the admin
     * @throws SQLException 
     */
    public XYChart.Series handleProjectBarChartDataForAdmin(int projectID,LocalDate fromdate, LocalDate todate) throws SQLException;
    
}
