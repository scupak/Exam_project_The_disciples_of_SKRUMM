/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL.Interface;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.SQLException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Project;

/**
 *
 * @author kacpe
 */
public interface TaskManagerInterface 
{
    public void newInterval(Interval interval) throws SQLServerException, SQLException;
    
}
