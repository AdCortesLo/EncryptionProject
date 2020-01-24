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

/**
 *
 * @author jordi
 */
class Marc extends JFrame {

    public Marc() {
        setTitle("Prova de gràfics que agafa dades d'un Thread");
        setSize(600, 500);
        ExemplePilotes panel1 = new ExemplePilotes();
        add(panel1);
    }
}

class ExemplePilotes extends JPanel {

    public static Graphics2D gr;
    public static Pilota pilotes[] = new Pilota[200];
    public static int nPilotes = 0;
    public static int contador = 0;
    public static int posicio = 0;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        gr = (Graphics2D) g;
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        gr.setColor(new Color(255, 0, 0));
        Point posi;

        for (int i = 0; i < nPilotes; i++) {
            posi = pilotes[i].getPosicio();
            gr.fillOval((int) posi.getX(), (int) posi.getY(), 8, 8);
        }

        gr.setColor(new Color(0, 80, 0));
        gr.drawString("Portes " + nPilotes + " Clicks", 20, 450);
        gr.drawString("Fes click per crear una boleta", 400, 450);
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            Logger.getLogger(ExemplePilotes.class.getName()).log(Level.SEVERE, null, ex);
        }
        repaint();
    }

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Marc marc1 = new Marc();
                    marc1.setVisible(true);
                    marc1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    marc1.setVisible(true);

                    Timer timer = new Timer(1000, new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                            Random r = new Random();

                            if (r.nextInt() > 0.7) {
                                Point p = new Point();
                                p.y = posicio + 90;
                                posicio += 90;
                                p.x = 0;
                                pilotes[nPilotes] = new Pilota(p);
                                nPilotes++;
                                if (posicio + 90 > 500) {
                                    posicio = 0;
                                }
                            }
                        }
                    });
                    timer.start();

                    marc1.addMouseListener(new MouseAdapter() {
                        public void mousePressed(MouseEvent me) {
                            if (nPilotes < 200) {
                                Point p = me.getPoint();
                                pilotes[nPilotes] = new Pilota(p);
                                nPilotes++;
                            } else {
                                JOptionPane.showMessageDialog(marc1, "Màxim 20!");
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
