/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL;

import java.io.IOException;

/**
 *
 * @author lumby
 */
public class TaskDBDAO
{
    
    private final DatabaseConnector dbCon;
    
    public TaskDBDAO() throws IOException
    {
        dbCon = new DatabaseConnector();
    }
}
