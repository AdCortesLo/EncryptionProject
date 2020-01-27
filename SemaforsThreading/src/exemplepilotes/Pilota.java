/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplepilotes;

import java.awt.Point;
import java.util.Random;

/**
 *
 * @author Adrian cortes
 */
public class Pilota implements Runnable
{
    private Thread fil;
    private Point posicio;
    private boolean isX;

    public Pilota(Point p, boolean isX)
    {
        this.isX = isX;
        fil = new Thread(this);
        posicio = p;
        fil.start();
    }

    @Override
    public void run()
    {
        mou(10000);
    }

    public void mou(int temps)
    {
        final int INCREMENTO = 4;

        if (isX)
        {
            if (posicio.getX() + INCREMENTO > 700)
            {
                posicio.setLocation(0, posicio.getY());
            } else
            {
                posicio.setLocation(posicio.getX() + INCREMENTO, posicio.getY());
            }
        } else
        {
            if (posicio.getY() + INCREMENTO > 700)
            {
                posicio.setLocation(posicio.getX(), 0);
            } else
            {
                posicio.setLocation(posicio.getX(), posicio.getY() + INCREMENTO);
            }
        }
        try
        {
            Thread.sleep(10);
        } catch (InterruptedException ex)
        {
            System.out.println("Error en thread");
        }
        mou(10000);
    }

    public void tanca()
    {
        fil.interrupt();
    }

    public Point getPosicio()
    {
        return posicio;
    }

    public void atura() throws InterruptedException
    {
        fil.wait();
    }

    public void reanuda()
    {

    }
}
