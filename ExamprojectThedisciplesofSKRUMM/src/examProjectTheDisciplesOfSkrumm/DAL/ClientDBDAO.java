/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.ClientDBDAOInterface;
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
 * @author Zanaxdk <https://github.com/zanaxdk>
 */
public class ClientDBDAO implements ClientDBDAOInterface
{
    private final DatabaseConnector dbCon;

    public ClientDBDAO() throws IOException
    {
        dbCon = new DatabaseConnector();
    }
    
    @Override
    public Client getClient(Client client) throws SQLException
    {
        if (!clientExist(client))
        {
            return null;
        }

        Client returnClient;
        try (Connection con = dbCon.getConnection())
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

    }

    @Override
    public List<Client> getAllClients() throws SQLServerException, SQLException
    {
        ArrayList<Client> clients = new ArrayList<>();

        try (Connection con = dbCon.getConnection())
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
    }

    @Override
    public boolean clientExist(Client client) throws SQLException
    {
        try (Connection con = dbCon.getConnection())
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
    }

    @Override
    public Client createClient(Client client) throws SQLException
    {
        

        try (Connection con = dbCon.getConnection())
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
                return null;
            }
            System.out.println(client.getClientName() + " created " + clientExist(client));
            JOptionPane.showMessageDialog(null, client.getClientName() + " has been created!");
            return client;
        }

    }
    
    public static void main(String[] args) throws IOException, SQLException
    {
        ClientDBDAO clientDb = new ClientDBDAO();
        ArrayList<Client> clients = new ArrayList<>();
        clients.addAll(clientDb.getAllClients());
        
//        for (Client client : clients)
//        {
//          System.out.println(client);
//        }
        
        Client steve = new Client(3, "Test", 0, 0);
        clientDb.createClient(steve);
//        System.out.println(clientDb.clientExist(steve));
//       System.out.println(clientDb.getClient(steve));
//        clientDb.createClient(steve);
//        
//        clients.addAll(clientDb.getAllClients());
//        
//        for (Client client : clients)
//        {
//          System.out.println(client);
//        }
    }

    @Override
    public boolean deleteClient(Client client) throws SQLException {
        try(Connection con = dbCon.getConnection())
        {
            if(clearClient(client))
            {

            PreparedStatement ps = con.prepareStatement("DELETE FROM [client] WHERE id = ?");
            ps.setInt(1, client.getId());
            
            int updatedRows = ps.executeUpdate();
            
            return updatedRows > 0;
            }
            
        }
        
        return false;
    }
    
        public boolean clearClient(Client client) throws SQLException
    {
        try(Connection con = dbCon.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("DELETE FROM [project] WHERE clientID = ?");
            ps.setInt(1, client.getId());
            ps.executeUpdate();
            
            PreparedStatement ps2 = con.prepareStatement("SELECT * FROM [project] WHERE clientID = ?");
            ps2.setInt(1, client.getId());
            ResultSet rs = ps2.executeQuery();
            
            while(rs.next())
            {
                return false;
            }
            
            return true;
        }
    }

    @Override
    public boolean updateClient(Client client) throws SQLServerException, SQLException {
        if (!clientExist(client))
        {
            return false;
        }

        System.err.println(client);

        try (Connection con = dbCon.getConnection())
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

            return updatedRows > 0;

        }
    }
    
}
