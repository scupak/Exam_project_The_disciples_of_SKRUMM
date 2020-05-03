/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL.Interface;

import examProjectTheDisciplesOfSkrumm.BE.Interval;
import java.time.LocalDateTime;

/**
 *
 * @author kacpe
 */

public interface BLLFacadeInterface extends TreeTableUtilInterface, TaskManagerInterface, SecurityManagerInterface, UserManagerInterface
{

    public void newInterval(Interval interval);
    
}
