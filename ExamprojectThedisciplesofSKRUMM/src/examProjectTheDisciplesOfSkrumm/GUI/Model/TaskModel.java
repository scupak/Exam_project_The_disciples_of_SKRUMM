/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model;

import examProjectTheDisciplesOfSkrumm.BLL.BLLFacade;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.BLLFacadeInterface;

/**
 *
 * @author kacpe
 */
public class TaskModel 
{
    private BLLFacadeInterface bllfacade;

    private TaskModel()
    {
        bllfacade = new BLLFacade();
    }
    
    
}


