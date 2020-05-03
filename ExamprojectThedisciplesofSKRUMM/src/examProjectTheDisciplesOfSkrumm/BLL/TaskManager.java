/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.TaskManagerInterface;
import examProjectTheDisciplesOfSkrumm.DAL.DALFacade;
import examProjectTheDisciplesOfSkrumm.DAL.TaskDBDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kacpe
 */
public class TaskManager implements TaskManagerInterface
{
    private final DALFacade dal;

    public TaskManager() throws IOException
    {
        dal = new DALFacade();
    }
    
    
    @Override
    public void newInterval(Interval interval) throws SQLServerException, SQLException
    {
        
        dal.newInterval(interval);
    }
}
