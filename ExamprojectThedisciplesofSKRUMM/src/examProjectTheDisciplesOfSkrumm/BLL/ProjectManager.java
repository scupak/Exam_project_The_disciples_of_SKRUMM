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
import examProjectTheDisciplesOfSkrumm.DAL.Interface.DALFacadeInterface;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SKRUMM
 */
public class ProjectManager implements ProjectManagerInterface
{
    DALFacadeInterface dalfacade;

    public ProjectManager(DALFacadeInterface dalfacade) throws IOException, Exception 
    {
        this.dalfacade = dalfacade;
    }
    

    /**
     * Gets a list of all projects
     * @return ArrayList list
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public List<Project> getAllProjects() throws SQLServerException, SQLException {
        ArrayList<Project> list = new ArrayList();
        list.addAll(dalfacade.getAllProjects());
        for (Project project : list) {
            project.setTimeSec(dalfacade.getDurationFromTasks(project));
            System.out.println("Added the time " + project.getFormatedTime() + " to the project at " + list.indexOf(project));
        }
        return list;
    }

    /**
     * Checks if a project exists
     * @param project
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean projectExist(Project project) throws SQLException {
        return dalfacade.projectExist(project);
    }

    /**
     * creates a new project
     * @param project
     * @return project
     * @throws SQLException 
     */
    @Override
    public Project createProject(Project project) throws SQLException {
        return dalfacade.createProject(project);
    }

    /**
     * gets a specific project
     * @param project
     * @return retrunProject
     * @throws SQLException 
     */
    @Override
    public Project getProject(Project project) throws SQLException {
        Project returnProject = dalfacade.getProject(project);
        returnProject.setTimeSec(dalfacade.getDurationFromTasks(project));
        System.out.println("Added time adds together to " + returnProject.getFormatedTime());
        return returnProject;
    }

    /**
     * Deletes a project
     * @param project
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean deleteProject(Project project) throws SQLException {
         return dalfacade.deleteProject(project);
    }

    /**
     * updates an existing project
     * @param project
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean updateProject(Project project) throws SQLException {
        return dalfacade.updateProject(project);
    }
    
    /**
     * Gets a list of projects for a client
     * @param client
     * @return ArrayList list
     * @throws SQLException 
     */
    @Override
    public List<Project> getProjectsForClient(Client client) throws SQLException
    {

        ArrayList<Project> list = new ArrayList();
        list.addAll(dalfacade.getProjectsForClient(client));
        for (Project project : list) {
            project.setTimeSec(dalfacade.getDurationFromTasks(project));
            System.out.println("Added the time " + project.getFormatedTime() + " to the project at " + list.indexOf(project));
        }
        return list;
    }
    
    
}
