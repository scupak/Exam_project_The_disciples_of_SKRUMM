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
import examProjectTheDisciplesOfSkrumm.BLL.Interface.TreeTableUtilInterface;
import examProjectTheDisciplesOfSkrumm.BLL.Util.TreeTableUtil;
import javafx.scene.control.TreeItem;

/**
 *
 * @author kacpe
 */
public class BLLFacade implements BLLFacadeInterface
{
    private TreeTableUtilInterface treeTableUtil;

    public BLLFacade() 
    {
        treeTableUtil = new TreeTableUtil();
    }
    

    @Override
    public TreeItem<Task> getMockModel() {
        return treeTableUtil.getMockModel();
    }

    @Override
    public TreeItem<Task> getModel(String title, Project project, Client client, int time) {
        return treeTableUtil.getModel(title, project, client, time);
    }
    
}
