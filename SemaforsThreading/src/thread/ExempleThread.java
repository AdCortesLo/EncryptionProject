/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemple.thread;

/**
 *
 * @author 34654
 */
public class ExempleThread {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Prova d'inicialització de la class Runnable");
        EstendreThread noufil = new EstendreThread("fil 1");
        
        for (int cont=0; cont<10; cont++){
            System.out.println("class principal "+cont);
            try {
                Thread.sleep(400); // Faig dormir el fil principal 4 decimes de segon
            }catch (InterruptedException exc) {
                System.out.println("Fil principal interrumput");
            }
        }
        System.out.println("Fil principal terminat");
    }
    
}
