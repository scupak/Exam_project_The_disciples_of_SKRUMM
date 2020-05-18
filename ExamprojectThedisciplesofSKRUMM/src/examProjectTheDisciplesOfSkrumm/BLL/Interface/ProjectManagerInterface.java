/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL.Interface;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author SKRUMM
 */
public interface ProjectManagerInterface 
{
    /**
     * gets all projects
     * @return all projects
     * @throws SQLServerException
     * @throws SQLException 
     */
    public List<Project> getAllProjects() throws SQLServerException, SQLException;
    
    /**
     *  gets if a project exists
     * @param project
     * @return true or false if a project either does or does not exist
     * @throws SQLException 
     */
    public boolean projectExist(Project project) throws SQLException;
    
    /**
     * creates a new project
     * @param project
     * @return a newly created project
     * @throws SQLException 
     */
    public Project createProject(Project project) throws SQLException;
    
    /**
     * gets a singular project
     * @param project
     * @return a project
     * @throws SQLException 
     */
    public Project getProject(Project project) throws SQLException;
    
    /**
     * deletes a project
     * @param project
     * @return true or false wether or not if the project was or was not deleted
     * @throws SQLException 
     */
    public boolean deleteProject(Project project) throws SQLException;
    
    /**
     * updates a project with given information
     * @param project
     * @return a newly updated project
     * @throws SQLException 
     */
    public boolean updateProject(Project project) throws SQLException;
    
    /**
     * gets a list of all projects a client is assigned to
     * @param client
     * @return all the projects that a client is assigned to
     * @throws SQLException 
     */
    public List<Project> getProjectsForClient(Client client) throws SQLException;
   
}
