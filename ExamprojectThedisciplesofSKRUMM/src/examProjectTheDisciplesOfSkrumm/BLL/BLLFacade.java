/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL;


import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.BLLFacadeInterface;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.SecurityManagerInterface;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.TaskManagerInterface;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.TreeTableUtilInterface;
import examProjectTheDisciplesOfSkrumm.BLL.Util.TreeTableUtil;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 *
 * @author kacpe
 */
public class BLLFacade implements BLLFacadeInterface
{
    private TreeTableUtilInterface treeTableUtil;
    private TaskManagerInterface taskmanager;
    private SecurityManagerInterface securityManager;

    public BLLFacade() 
    {
        treeTableUtil = new TreeTableUtil();
        taskmanager = new TaskManager();
        securityManager = new examProjectTheDisciplesOfSkrumm.BLL.Security.SecurityManager();
    }
    

    @Override
    public TreeItem<Task> getMockModel() {
        return treeTableUtil.getMockModel();
    }

    @Override
    public TreeItem<Task> getModel(ObservableList<Task> tasks) {
        return treeTableUtil.getModel(tasks);
    }

   

    @Override
    public String hashPassword(String password) throws SecurityException 
    {
        return securityManager.hashPassword(password);
    }
    
}
