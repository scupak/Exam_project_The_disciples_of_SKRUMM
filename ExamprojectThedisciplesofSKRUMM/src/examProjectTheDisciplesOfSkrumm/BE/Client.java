/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BE;

import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author SKRUMM
 */
public class Client
{

    private SimpleIntegerProperty id;
    private SimpleStringProperty ClientName;
    private SimpleIntegerProperty ClientRate;
    private IntegerProperty isPaid;
    private ArrayList<Project> projects;
    private IntegerProperty numberOfProjects;

    public Client(int id, String ClientName, int ClientRate, int isPaid)
    {
        this.id = new SimpleIntegerProperty(id);
        this.ClientName = new SimpleStringProperty(ClientName);
        this.ClientRate = new SimpleIntegerProperty(ClientRate);
        this.isPaid = new SimpleIntegerProperty(isPaid);
        this.projects = new ArrayList<>();
        this.numberOfProjects = new SimpleIntegerProperty(projects.size());
    }

    /**
     * Gets the id of the client
     * @return int id
     */
    public int getId()
    {
        return id.get();
    }

    /**
     * Sets the id of the client
     * @param id 
     */
    public void setId(int id)
    {
        this.id.set(id);
    }

    /**
     * Gets the name of the client
     * @return String clientName
     */
    public String getClientName()
    {
        return ClientName.get();
    }

    /**
     * Sets the name of the client
     * @param ClientName 
     */
    public void setClientName(String ClientName)
    {
        this.ClientName.set(ClientName);
    }

    /**
     * gets the clients rate
     * @return int clientRate
     */
    public int getClientRate()
    {
        return ClientRate.get();
    }

    /**
     * Sets the clients rate
     * @param ClientRate 
     */
    public void setClientRate(int ClientRate)
    {
        this.ClientRate.set(ClientRate);
    }

    /**
     * Gets is paid for a client
     * @return int isPaid
     */
    public int getIsPaid()
    {
        return isPaid.get();
    }

    /**
     * Sets the is paid varible
     * @param isPaid 
     */
    public void setIsPaid(int isPaid)
    {
        this.isPaid.set(isPaid);
    }

    /**
     * Gets a list of projects for a client
     * @return ArrayList projects
     */
    public ArrayList<Project> getProjects()
    {

        return projects;
    }

    /**
     * Sets the projects ArrayList
     * @param projects 
     */
    public void setProjects(ArrayList<Project> projects)
    {
        this.projects = projects;
    }

    /**
     * Gets the number of projects for a client
     * @return int numberOfProjects
     */
    public int getnumberOfProjects()
    {
        numberOfProjects.set(projects.size());
        return numberOfProjects.get();
    }

    /**
     * Sets the number of projects for a client
     * @param numberOfProject 
     */
    public void setNumberOfProject(int numberOfProject)
    {
        this.numberOfProjects.set(numberOfProject);
    }

    /**
     * converts the info for the client to a string
     * @return The string, that has been made
     */
    @Override
    public String toString()
    {
        return "" + id.get() + "  " + "" + ClientName.get() + "   " + "" + ClientRate.get() + "   " + "" + isPaid.get();
    }

}
