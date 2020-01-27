/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplepilotes;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

class Marc extends JFrame
{
    public Marc()
    {
        setTitle("Prova de gràfics que agafa dades d'un Thread");
        setSize(700, 700);
        ExemplePilotes panel1 = new ExemplePilotes();
        add(panel1);
    }
}

class ExemplePilotes extends JPanel
{
    public static Graphics2D gr;
    public static Pilota pilotes[] = new Pilota[1000];
    public static int nPilotes = 0;
    public static int contador = 0;
    public static int posicioY = 73;
    public static int posicioX = 73;
    final public static int INCREMENTO_CARRETERAS = 120;
    final public static int INICIO_X = 70;
    final public static int INICIO_Y = 70;

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        gr = (Graphics2D) g;
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        /* Pinta carreteras */
        gr.setColor(new Color(189,183,107));
        gr.setBackground(new Color(189,183,107));
        
        int x = INICIO_X;
        int y = INICIO_Y;
        for (int i = 0; i < 5; i++)
        {
            gr.fillRect(x, 0, 15, 700);
            x += INCREMENTO_CARRETERAS;
        }
        
        for (int i = 0; i < 5; i++)
        {
            gr.fillRect(0, y, 700, 15);
            y += INCREMENTO_CARRETERAS;
        }
        
        /* Pinta pilotes */
        gr.setColor(new Color(0, 0, 0));
        Point posi;

        for (int i = 0; i < nPilotes; i++)
        {
            posi = pilotes[i].getPosicio();
            gr.fillOval((int) posi.getX(), (int) posi.getY(), 8, 8);
        }
        
        /* Pinta String número pilotes */
        gr.drawString(nPilotes+"", 130, 130);
        try
        {
            Thread.sleep(10);
        } catch (InterruptedException ex)
        {
            Logger.getLogger(ExemplePilotes.class.getName()).log(Level.SEVERE, null, ex);
        }
        repaint();
    }

    public static void main(String args[])
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    Marc marc1 = new Marc();
                    marc1.setVisible(true);
                    marc1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    marc1.setVisible(true);

                    Random r = new Random();

                    Timer timer = new Timer(1000, new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            if (r.nextBoolean())
                            {
                                if (r.nextBoolean())
                                {
                                    generarHorizontal();
                                } else
                                {
                                    generarVertical();
                                }
                            }
                        }

                    });
                    timer.start();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void generarHorizontal()
    {
        Point p = new Point();
        p.y = posicioY;
        posicioY += INCREMENTO_CARRETERAS;
        p.x = 0;
        pilotes[nPilotes] = new Pilota(p, true);
        nPilotes++;
        if (posicioY + INCREMENTO_CARRETERAS > 700)
        {
            posicioY = 73;
        }
    }

    private static void generarVertical()
    {
        Point p = new Point();
        p.x = posicioX;
        posicioX += INCREMENTO_CARRETERAS;
        p.y = 0;
        pilotes[nPilotes] = new Pilota(p, false);
        nPilotes++;
        if (posicioX + INCREMENTO_CARRETERAS > 700)
        {
            posicioX = 73;
        }
    }
}
