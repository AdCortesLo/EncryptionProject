/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemple.priority;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jordi
 */
public class ExemplePriority {

    public static void main(String[] args) {
        
            // Crea els dos fils
            Priority fil1 = new Priority("Alta Prioritat");
            Priority fil2 = new Priority("Baixa Prioritat");
            
            //Assigna les prioritats als dos fils
            fil1.fil.setPriority(Thread.MAX_PRIORITY);
            fil2.fil.setPriority(Thread.MIN_PRIORITY);
            
            // Inicia els subprocessos
            fil1.fil.start();
            fil2.fil.start();
            
            
        try {
            // Espera que acabin els dos subprocessos.
            fil1.fil.join();
            fil2.fil.join();
        } catch (InterruptedException ex) {
            System.out.println("Fil principal interromput");
        }
        
        // Mostra el contador dels dos subfils
        System.out.println("\nContador de fil d'alta prioritat: "+fil1.contador);
        System.out.println("Contador de fil de baixa prioritat: "+fil2.contador);
    }
    
}
