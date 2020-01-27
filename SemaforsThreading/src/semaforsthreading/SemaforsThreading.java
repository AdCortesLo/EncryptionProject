/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semaforsthreading;

import exemplepilotes.PilotaY;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import semaforsthreading.ExemplePilotes.Marc;

/**
 *
 * @author Adrian
 */
public class SemaforsThreading {

    public static Graphics2D gr;
    public static PilotaY pilotes[]=new PilotaY[200];
    public static int nPilotes=0;
    
    public static void main(String[] args) {
        // TODO code application logic here

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Marc marc1 = new Marc();
                    marc1.setVisible(true);
                    marc1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    marc1.setVisible(true);
                    marc1.addMouseListener(new MouseAdapter() {
                        public void mousePressed(MouseEvent me) {
                            Point p = me.getPoint();
                            pilotes[nPilotes] = new PilotaY(p);
                            nPilotes++;
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}

class ExemplePilotes extends JPanel {

    public static Graphics2D gr;
    public static PilotaY pilotes[] = new PilotaY[200];
    public static int nPilotes = 0;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        gr = (Graphics2D) g;
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        gr.setColor(new Color(255, 255, 0));
        Point posi;

        for (int i = 0; i < nPilotes; i++) {
            posi = pilotes[i].getPosicio();
            gr.fillOval((int) posi.getX(), (int) posi.getY(), 8, 8);
        }

        gr.setColor(new Color(0, 80, 0));
        gr.drawString("Portes " + nPilotes + " Clicks", 20, 450);
        gr.drawString("Fes click per crear una boleta", 400, 450);
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(ExemplePilotes.class.getName()).log(Level.SEVERE, null, ex);
        }
        repaint();
    }

    public static class Marc extends JFrame {

        public Marc() {
            setTitle("Prova de gràfics que agafa dades d'un Thread");
            setSize(600, 500);
            ExemplePilotes panel1 = new ExemplePilotes();
            add(panel1);
        }
    }
}
