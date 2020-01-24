/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafics;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
/**
 *
 * @author Jordi
 */
public class Grafics {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Marc marc1 = new Marc();
        marc1.setVisible(true);
        marc1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}

class Marc extends JFrame{
    private JButton bot1;
    public Marc(){
        setTitle("Prova de gràfics");
        setSize(600,500);
        ElPanel panel1 = new ElPanel();
        add(panel1);
        bot1 = new JButton("Saluda");
        bot1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Hola mon!");
            }

        });
        panel1.add(bot1);
        
    }
}

class ElPanel extends JPanel {
    private int posX =5;
    public ElPanel(){
        this.setLayout(new FlowLayout());
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);  // Crido al mètode de la clase pare.
        
        // Canvio el color
        g.setColor(Color.red);
        
        // Escriu línia. Comença en 50, 50 i acaba en 300,50
        g.drawLine(50, 50, 300, 50);
        
        // Escriu un rectangle. Comença en 60,60. Ample de 120 i alçada de 80
        g.drawRect(60, 60, 120, 80);
        
        // Dibuixa un rectangle omplert. color en RGB
        Color color1 = new Color(100,240,240);
        g.setColor(color1);
        g.fillRect(250, 280, 200, 100);
        
        // Dibuixa un triangle. Passem les coordinades x, y i 3 costats.
        int xPoints[] = {310,310,550};
        int yPoints[] = {130,240,240};
        g.drawPolygon(xPoints, yPoints, 3);
        
        g.fillArc(100, 300, 100, 100, 0, 90);
        g.setColor(Color.BLUE);
        g.fillArc(100, 300, 100, 100, 90, 230);
        g.setColor(Color.GREEN);
        
        
        //Escriu un Oval. La posició X és variable, Y=250. Ample i alçada de 10
        g.fillOval(posX, 250, 10, 10);
        posX=posX+2;
        if (posX>580){   //Control per que no surti de la pantalla
            posX=5;
        }
        
        try {
            Thread.sleep(100);  // Espera 100 mil·lisegons 
        } catch (InterruptedException ex) {
            Logger.getLogger(ElPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Crida recursiva.
        repaint();
    }
}

