/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL.Interface;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import java.sql.SQLException;

/**
 *
 * @author lumby
 */
public interface DALFacadeInterface extends UserDBDAOInterface, ClientDBDAOInterface, ProjectDBDAOInterface, TaskDBDAOInterface 
{
    
}
