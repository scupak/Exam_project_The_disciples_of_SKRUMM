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
            PreparedStatement ps = con.prepareStatement("SELECT * FROM [client] WHERE name = ?");

            ps.setString(1, client.getClientName());
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
            PreparedStatement ps = con.prepareStatement("SELECT * FROM [client] WHERE name = ?");
            ps.setString(1, client.getClientName());

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
        if (clientExist(client))
        {
            return null;
        }

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

            return client;
        }
    }
    
}
