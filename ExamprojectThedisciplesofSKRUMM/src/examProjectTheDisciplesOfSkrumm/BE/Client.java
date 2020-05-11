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
 * @author kacpe
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
        this.isPaid =  new SimpleIntegerProperty(isPaid);
        this.projects = new ArrayList<>();
        this.numberOfProjects = new SimpleIntegerProperty(projects.size());        
    }
   

     public int getId()
    {
        return id.get();
    }

    public void setId(int id)
    {
        this.id.set(id);
    }
    
    public String getClientName() {
        return ClientName.get();
    }

    public void setClientName(String ClientName) {
        this.ClientName.set(ClientName);
    }

    public int getClientRate() {
        return ClientRate.get();
    }

    public void setClientRate(int ClientRate) {
        this.ClientRate.set(ClientRate);
    }
    
    public int getIsPaid() {
        return isPaid.get();
    }

    public void setIsPaid(int isPaid) {
        this.isPaid.set(isPaid);
    }

    public ArrayList<Project> getProjects()
    {
        
        return projects;
    }

    public void setProjects(ArrayList<Project> projects)
    {
        this.projects = projects;
    }

    public int getnumberOfProjects()
    {
        numberOfProjects.set(projects.size());
        return numberOfProjects.get();
    }

    public void setNumberOfProject(int numberOfProject)
    {
        this.numberOfProjects.set(numberOfProject);
    }
    
    

    @Override
    public String toString() {
        return  ""+ id.get() + "  " + "" + ClientName.get() + "   " + "" + ClientRate.get() + "   " + "" + isPaid.get() ;
    }
    
    
    
    
    
}
