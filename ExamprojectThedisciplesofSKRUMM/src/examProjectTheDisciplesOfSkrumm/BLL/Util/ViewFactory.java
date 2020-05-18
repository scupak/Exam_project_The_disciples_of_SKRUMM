/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL.Util;

import examProjectTheDisciplesOfSkrumm.BLL.Interface.ViewFactoryInterface;
import examProjectTheDisciplesOfSkrumm.enums.ViewTypes;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

/**
 * This class takes care of loading in all the views 
 * so that view creation can be handled in one place.
 * @author SKRUMM
 */
public class ViewFactory implements ViewFactoryInterface
{
    /**
     * This method returns a view based on a chosen viewtype enum. 
     * @param viewtype
     * @return FXMLLoader
     * @throws Exception 
     * @throws java.io.IOException 
     */
    @Override
    public FXMLLoader getLoader(ViewTypes viewtype) throws Exception, IOException
    {
        FXMLLoader loader = null;
        switch (viewtype)
        {
            case ADDCLIENT:
                loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AddClient.fxml"));
                return loader;
            
            case ADDPROJECT:
                loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AddProjectView.fxml"));
                return loader;
                
            case ADMINCLIENTSANDPROJECTS:
                loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AdminClientsAndProjects.fxml"));
                return loader;
                
            case ADMINMAIN:
                loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AdminMainView.fxml"));
                return loader;
                
            case ADMINTASKS:
                loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AdminTasksView.fxml"));
                return loader;
                
            case ADMINUSER:
                loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AdminUserView.fxml"));
                return loader;
                
            case CHART:
                loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/ChartView.fxml"));
                return loader;
                
            case CLIENTSANDPROJECTS:
                loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/ClientsAndProjects.fxml"));
                return loader;
                
            case CREATETASK:
                loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/CreateTaskView.fxml"));
                return loader;
               
            case CREATEUSER:
                loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/CreateUserView.fxml"));
                return loader;
                
            case EDITCLIENT:
                loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/EditClient.fxml"));
                return loader;    
               
            case EDITINTERVAL:
                loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/EditIntervalView.fxml"));
                return loader; 
            
            case EDITPROJECT:
                loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/EditProjectView.fxml"));
                return loader;
                
            case EDITTASK:
                loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/EditTaskView.fxml"));
                return loader;
                
            case EDITUSER:
                loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/EditUserView.fxml"));
                return loader;
             
            case MAIN:
                loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/MainView.fxml"));
                return loader;
                
            case LOGWINDOW:
                loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/LogWindow.fxml"));
                return loader;
                
            case LOGIN:
                loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/LoginView.fxml"));
                return loader;
                
            case TASK:
                loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/TaskView.fxml"));
                return loader;
                  
            default:
                throw new Exception("Unknown view type given to view factory");
        }
        
    }
    
}
