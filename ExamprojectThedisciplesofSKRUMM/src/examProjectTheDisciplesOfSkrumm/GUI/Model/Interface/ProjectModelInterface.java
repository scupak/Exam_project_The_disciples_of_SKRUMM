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
import javafx.collections.ObservableList;

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
    
    public boolean updateProject(Project project) throws SQLServerException, SQLException;
    
}
