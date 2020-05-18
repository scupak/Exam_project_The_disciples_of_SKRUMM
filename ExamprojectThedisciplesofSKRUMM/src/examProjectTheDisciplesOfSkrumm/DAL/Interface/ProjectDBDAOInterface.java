/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL.Interface;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author SKRUMM
 */
public interface ProjectDBDAOInterface 
{
    /**
     * Gets a list of all projects
     * @return a list of all projects
     * @throws SQLServerException
     * @throws SQLException 
     */
    public List<Project> getAllProjects() throws SQLServerException, SQLException;
    
    /**
     * checks if a project already exists
     * @param project
     * @return true if the project already exists, false otherwise
     * @throws SQLException 
     */
    public boolean projectExist(Project project) throws SQLException;
    
    /**
     * creates a new project
     * @param project
     * @return the new project that has been made
     * @throws SQLException 
     */
    public Project createProject(Project project) throws SQLException;
    
    /**
     * gets the given project
     * @param project
     * @return the given project as a project object
     * @throws SQLException 
     */
    public Project getProject(Project project) throws SQLException;
    
    /**
     * deletes the given project
     * @param project
     * @return true if the project was deleted, false otherwise
     * @throws SQLException 
     */
    public boolean deleteProject(Project project) throws SQLException;
    
    /**
     * clears the project
     * @param project
     * @return true if the project was cleared, false otherwise
     * @throws SQLException 
     */
    public boolean clearProject(Project project) throws SQLException;
    
    /**
     * gets a list of all the projects for the given client
     * @param client
     * @return a list of all the projects for the given client
     * @throws SQLException 
     */
    public List<Project> getProjectsForClient(Client client) throws SQLException;
        
    /**
     * updates the information on the given project with the given information
     * @param project
     * @return true if the project was updated, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    public boolean updateProject(Project project) throws SQLServerException, SQLException;

    
  
    
}
