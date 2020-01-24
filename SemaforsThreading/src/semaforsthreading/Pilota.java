/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semaforsthreading;

import exemplepilotes.*;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jordi
 */
public class Pilota implements Runnable{
    private Thread fil;
    private Point posicio;
    
    public Pilota(Point p){
        fil = new Thread(this);
        posicio = p;
        fil.start();
    }

    @Override
    public void run() {
        mou(1000);
    }

    public void mou(int temps){
        
        if (posicio.getX()+5 > 600) {
            posicio.setLocation(0, posicio.getY());
        }
        else {
            posicio.setLocation(posicio.getX()+5, posicio.getY());
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            System.out.println("Error en thread");
        }
        if (temps>0)
            mou(temps-1);
    }
    public void tanca(){
        fil.interrupt();
    }
    public Point getPosicio(){
        return posicio;
    }
    public void atura() throws InterruptedException{
        fil.wait();
    }
    public void reanuda(){
        
    }
    
}
