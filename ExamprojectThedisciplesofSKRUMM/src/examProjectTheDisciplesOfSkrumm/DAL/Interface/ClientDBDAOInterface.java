/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL.Interface;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Zanaxdk <https://github.com/zanaxdk>
 */
public interface ClientDBDAOInterface
{
    public Client getClient(Client client) throws SQLException;
    
    public List<Client> getAllClients() throws SQLServerException, SQLException;
    
    public boolean clientExist(Client client) throws SQLException;
    
    public Client createClient(Client client) throws SQLException;
     
    public boolean deleteClient(Client client) throws SQLException;
}
