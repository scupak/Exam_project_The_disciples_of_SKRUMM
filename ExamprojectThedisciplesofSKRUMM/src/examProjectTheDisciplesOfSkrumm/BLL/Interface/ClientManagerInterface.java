/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL.Interface;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author lumby
 */
public interface ClientManagerInterface
{
     public Client getClient(Client client) throws SQLException;
    
     public List<Client> getAllClients() throws SQLServerException, SQLException;
    
     public boolean clientExist(Client client) throws SQLException;
    
     public Client createClient(Client client) throws SQLException;
     
     public boolean deleteClient(Client client) throws SQLException;
     
     public boolean updateClient(Client client) throws SQLException;
  
}
