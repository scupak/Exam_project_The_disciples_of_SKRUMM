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
 * @author kacpe
 */
public interface ProjectDBDAOInterface 
{
    public List<Project> getAllProjects() throws SQLServerException, SQLException;
    
    public boolean projectExist(Project project) throws SQLException;
    
    public Project createProject(Project project) throws SQLException;
    
    public Project getProject(Project project) throws SQLException;
    
    public boolean deleteProject(Project project) throws SQLException;
    
    public boolean clearProject(Project project) throws SQLException;
    
    public List<Project> getProjectsForClient(Client client) throws SQLException;
        
    public boolean updateProject(Project project) throws SQLServerException, SQLException;
    
    public List<Project> getAllUserProjects() throws SQLServerException, SQLException;
    
}
