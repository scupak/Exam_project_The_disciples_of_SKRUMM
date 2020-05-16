/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BLL.BLLFacade;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.BLLFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ProjectModelInterface;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

/**
 *
 * @author kacpe
 */
public class ProjectModel implements ProjectModelInterface
{
    ObservableList<Project> projects;
    private final BLLFacadeInterface bllfacade;

    public ProjectModel() throws IOException, Exception
    {
        bllfacade = new BLLFacade();
        this.projects = FXCollections.observableArrayList();
        
    }

    @Override
    public synchronized ObservableList<Project> getProjects() 
    {
        projects.clear();
        try {
            projects.addAll(bllfacade.getAllProjects());
        } catch (SQLException ex) {
            Logger.getLogger(ProjectModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return projects;
    }

    @Override
    public void setProjects(ObservableList<Project> projects) 
    {
        this.projects = projects;
    }

    @Override
    public void CreateProject(Project project) 
    {
        try {
            bllfacade.createProject(project);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectModel.class.getName()).log(Level.SEVERE, null, ex);
        }
       getProjects();
    }

    @Override
    public boolean deleteProject(Project project) throws SQLException {
       return bllfacade.deleteProject(project);
    }

    @Override
    public boolean updateProject(Project project) throws SQLServerException, SQLException {
        return bllfacade.updateProject(project);
    }
    
    public List<Project> getAllProjects() throws SQLServerException, SQLException
    {
        return bllfacade.getAllProjects();
    }
    
    public ObservableList<Project> getProjectsForClient(Client client) throws SQLException
    {
        ObservableList<Project> clientProjects =  FXCollections.observableArrayList();
        clientProjects.addAll(bllfacade.getProjectsForClient(client));
        return clientProjects;
    }

    @Override
    public XYChart.Series handleProjectBarChartData(String userID, LocalDate fromdate, LocalDate todate) throws SQLException {
      return  bllfacade.handleProjectBarChartData(userID, fromdate, todate);
    }

    @Override
    public XYChart.Series handleProjectBarChartDataForAdmin(int projectID, LocalDate fromdate, LocalDate todate) throws SQLException {
       return bllfacade.handleProjectBarChartDataForAdmin(projectID, fromdate, todate);
    }
    
    
    
}
