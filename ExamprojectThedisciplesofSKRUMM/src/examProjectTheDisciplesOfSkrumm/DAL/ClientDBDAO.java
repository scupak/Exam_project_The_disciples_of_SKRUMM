/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.ClientDBDAOInterface;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.LogDBDAOInterface;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author SKRUMM
 */
public class ClientDBDAO implements ClientDBDAOInterface
{
    private LogDBDAOInterface  logDBDAO;
    private final ConnectionPool conPool;

    public ClientDBDAO() throws IOException, Exception
    {
       this.conPool = ConnectionPool.getInstance();
       logDBDAO = new LogDBDAO();
    }
    /**
     * gets a client
     * @param client
     * @return a client
     * @throws SQLException 
     */
    @Override
    public Client getClient(Client client) throws SQLException
    {
        if (!clientExist(client))
        {
            return null;
        }

        Client returnClient;
        Connection con = conPool.checkOut();
        try
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM [client] WHERE id = ?");

            ps.setInt(1, client.getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String nane = rs.getString("name");
                int rate = rs.getInt("rate");
                int isPaid = rs.getInt("isPaid");
                returnClient = new Client(id, nane, rate, isPaid);
            } 
            else {
                return null;
            }
            return returnClient;
        }
        finally
        {
            conPool.checkIn(con);
        }

    }

    /**
     * gets all clients as a list
     * @return all clients as a list
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public List<Client> getAllClients() throws SQLServerException, SQLException
    {
        ArrayList<Client> clients = new ArrayList<>();

        Connection con = conPool.checkOut();
        try
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM [client]");
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                String nane = rs.getString("name");
                int rate = rs.getInt("rate");
                int isPaid = rs.getInt("isPaid");
                clients.add(new Client(id, nane, rate, isPaid));
            }
            return clients;
        }
        finally
        {
            conPool.checkIn(con);
        }
    }

    /**
     * checks if the given client exists
     * @param client
     * @return true if the client exists, false otherwise
     * @throws SQLException 
     */
    @Override
    public boolean clientExist(Client client) throws SQLException
    {
        Connection con = conPool.checkOut();
        try
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM [client] WHERE id = ?");
            ps.setInt(1, client.getId());

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                return true;
            }

            return false;
        }
        finally
        {
            conPool.checkIn(con);
        }
    }

    /**
     * creates a client
     * @param client
     * @return the new client
     * @throws SQLException 
     */
    @Override
    public Client createClient(Client client) throws SQLException
    {
        

        Connection con = conPool.checkOut();
        try
        {
            PreparedStatement ps = con.prepareStatement("INSERT INTO [client]"
                                                        + "(name, rate, isPaid) "
                                                        + "VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, client.getClientName());
            ps.setString(2, client.getClientRate() + "");
            ps.setString(3, client.getIsPaid() + "");
            
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            

            if (rs.next()) {
                client.setId((int) rs.getLong(1));
            } else {
                logDBDAO.createLog("Client Creation unsuccsessfully" + "-" + client.getClientName() + "-" +  "ERROR");
                return null;
            }
            System.out.println(client.getClientName() + " created " + clientExist(client));
            JOptionPane.showMessageDialog(null, client.getClientName() + " has been created!");
            logDBDAO.createLog("create Client successful" + "-" + client.getClientName() + "-" +  "SUCCESS");
            return client;
        }
        finally
        {
            conPool.checkIn(con);
        }

    }
    
    public static void main(String[] args) throws IOException, SQLException, Exception
    {
        ClientDBDAO clientDb = new ClientDBDAO();
        ArrayList<Client> clients = new ArrayList<>();
        clients.addAll(clientDb.getAllClients());
        
        for (Client client : clients)
        {
          System.out.println(client);
        }
    }

    /**
     * deletes a client
     * @param client
     * @return true if the client was deleted, false otherwise
     * @throws SQLException 
     */
    @Override
    public boolean deleteClient(Client client) throws SQLException {
        Connection con = conPool.checkOut();
        try
        {
            if(clearClient(client))
            {

            PreparedStatement ps = con.prepareStatement("DELETE FROM [client] WHERE id = ?");
            ps.setInt(1, client.getId());
            
            int updatedRows = ps.executeUpdate();
            
            if(updatedRows > 0){
               logDBDAO.createLog("Client Deletion successful" +"-"+ client.getClientName() + "-" +  "SUCCESS");
               return true;  
            }
            }
            
        }
        finally
        {
            conPool.checkIn(con);
        }
        logDBDAO.createLog("Client Deletion unsuccessful" +"-"+ client.getClientName() + "-" +  "ERROR");
        return false;
    }
    
    /**
     * clears a client
     * @param client
     * @return true if the client was cleared, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
        public boolean clearClient(Client client) throws SQLException
    {
        Connection con = conPool.checkOut();
        try
        {
            PreparedStatement ps = con.prepareStatement("DELETE FROM [project] WHERE clientID = ?");
            ps.setInt(1, client.getId());
            ps.executeUpdate();
            
            PreparedStatement ps2 = con.prepareStatement("SELECT * FROM [project] WHERE clientID = ?");
            ps2.setInt(1, client.getId());
            ResultSet rs = ps2.executeQuery();
            
            while(rs.next())
            {
                logDBDAO.createLog("Client Clearing unsuccessful" +"-"+ client.getClientName() + "-" +  "ERROR");
                return false;
            }
            logDBDAO.createLog("Client Cleared" +"-"+ client.getClientName() + "-" +  "SUCCESS");
            return true;
        }
        finally
        {
            conPool.checkIn(con);
        }
    }

    /**
     * updates a client
     * @param client
     * @return true if the client was updated, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean updateClient(Client client) throws SQLServerException, SQLException {
        if (!clientExist(client))
        {
            return false;
        }

        System.err.println(client);

        Connection con = conPool.checkOut();
        try
        {
            PreparedStatement ps = con.prepareStatement("UPDATE [client] "
                    + "SET name = ?,"
                    + " rate = ?, isPaid = ?"
                    + " WHERE id = ?");
            ps.setString(1, client.getClientName());
            ps.setInt(2, client.getClientRate());
            ps.setInt(3, client.getIsPaid());
            ps.setInt(4, client.getId());

            int updatedRows = ps.executeUpdate();
            
            if(updatedRows > 0){
                logDBDAO.createLog("Client Updated successfully" +"-"+ client.getClientName() + "-" +  "SUCCESS");
               return true; 
            }
            

        }
        finally
        {
            conPool.checkIn(con);
        }
        
        logDBDAO.createLog("Client Update unsuccessful" +"-"+ client.getClientName() + "-" +  "ERROR");
        return false;
    }
    
}
