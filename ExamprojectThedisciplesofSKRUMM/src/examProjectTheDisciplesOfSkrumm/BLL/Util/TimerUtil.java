/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL.Util;

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
    private boolean running = false;
    private Label timeLabe00;
    private int totalsec = 0;

        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("javafx.runtime.version"));
        
        System.out.println("start");

        if (running) {

            running = false;

        } else if (!running) {
            running = true;

        }

        new Thread(()
                -> {
            while (true) {

                if (running) {

                    try {
                        Thread.sleep(1000);

                        sec++;
                        totalsec++;

                        if (sec >= 60) {
                            min++;
                            sec = 0;
                        }

                        if (min >= 60) {
                            hour++;
                            min = 0;
                        }

                        String time = String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec);
                        System.out.println(time);
                        System.out.println(totalsec);
                        Platform.runLater(()
                                -> {
                            timeLabel.setText(String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec));
                        }
                        );

                    } catch (Exception e) {
                    }

                } else {

                    break;

                }

            }

        }).start();

    @Override
    public void run()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
