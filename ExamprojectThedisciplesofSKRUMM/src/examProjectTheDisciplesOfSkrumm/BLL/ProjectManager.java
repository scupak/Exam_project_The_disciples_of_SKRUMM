/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.ProjectManagerInterface;
import examProjectTheDisciplesOfSkrumm.DAL.DALFacade;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.DALFacadeInterface;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author kacpe
 */
public class ProjectManager implements ProjectManagerInterface
{
    DALFacadeInterface dalfacade;

    public ProjectManager() throws IOException 
    {
        dalfacade = new DALFacade();
    }
    

    @Override
    public List<Project> getAllProjects() throws SQLServerException, SQLException {
        return dalfacade.getAllProjects();
    }

    @Override
    public boolean projectExist(Project project) throws SQLException {
        return dalfacade.projectExist(project);
    }

    @Override
    public Project createProject(Project project) throws SQLException {
        return dalfacade.createProject(project);
    }

    @Override
    public Project getProject(Project project) throws SQLException {
        return dalfacade.getProject(project);
    }

    @Override
    public boolean deleteProject(Project project) throws SQLException {
         return dalfacade.deleteProject(project);
    }

    @Override
    public boolean updateProject(Project project) throws SQLException {
        return dalfacade.updateProject(project);
    }

    
    
}
