/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL.Util;

import com.sun.javafx.tools.packager.Main;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 *
 * @author Zanaxdk <https://github.com/zanaxdk>
 */
public class TimerUtil implements Runnable
{
    private int sec = 0;
    private int min = 0;
    private int hour = 0;
    private String time;
    private boolean isRunning = true;
    private Label timeLabel;
    private int totalSec = 0;

    public TimerUtil(Label timeLabel, int totalSec) {
        this.totalSec = totalSec;
        while(totalSec >= 3600){
        totalSec  = totalSec - 3600;
        hour++;
        System.out.println("added one to hours...");
        }
        
        while(totalSec >= 60){
        totalSec = totalSec - 60;
        min++;
        System.out.println("added one to min...");
        }
        
        sec = totalSec;
        System.out.println("added rest of seconds to sec...");
        
        time = String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec);
        System.out.println(time);
        
        //while totalSec is over 3600, totalSec minus 3600, and add 1 to hour until it isnt anymore
        //when done, while totalSec is over 60, totalSec minus 60 and add 1 to min until it isnt anymore
        //add rest of seconds to sec
        this.timeLabel = timeLabel;
    }
    
    
    public String getTime(){
    return time;
    }
            
    public int getTotalsec(){
    return totalSec;
    }

    @Override
    public void run()
    {
        while (true) {
                if (isRunning) {

                    try {
                        Thread.sleep(1000);

                        sec++;
                        totalSec++;

                        if (sec >= 60) {
                            min++;
                            sec = 0;
                        }

                        if (min >= 60) {
                            hour++;
                            min = 0;
                        }

                        time = String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec);
                        System.out.println(time);
                        System.out.println(totalSec);
                        
                            if(timeLabel != null){
                            Platform.runLater (() -> 
                            {   
                             timeLabel.setText(String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec));
                            });
                        }
                            if(timeLabel == null){
                            System.out.println("Nonexistant label is nonexistant, putting relevant data into time String. This is a Null Pointer");
                            }  
                    }
                    catch (Exception e){
                    }

                }
                else{
                    break;
                }

            }
    }
    
    public static void main(String[] args){
        int totSec = 123456;
        
        //giving label null, as it works with null
        TimerUtil tu = new TimerUtil(null, totSec);
        
        ExecutorService executorService = 
            Executors.newFixedThreadPool(1);
        
        executorService.submit(tu);
    }
    
}
