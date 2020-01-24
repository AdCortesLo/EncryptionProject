/*
    En aquest exemple crearem una variable static i un objecte que
    passarem com a paràmetre als nous subprocessos.
 */
package comunicacio;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 34654
 */
public class ExempleComunicacio {

    // Text que utilitzaran els dos subprocessos
    public static String textComu = "Text comú";
    
    public static void main(String[] args) {
        ClassExemple ce;
        // Objecte que utilitzaran els dos subprocessos.
        ce = new ClassExemple("Text de prova"); 
        
        Subproces sp1 = new Subproces("Fil 1", ce);
        Subproces sp2 = new Subproces("Fil 2", ce);
        

        
        try {
            sp1.fil.join();
            sp2.fil.join();
        } catch (InterruptedException ex) {
            System.out.println("Fil principal interromput");
        }
        
        // Mostrem el contingut de l'string i de l'objecte.
        System.out.println("\nEl text comú final és "+textComu);
        System.out.println("El text de l'objecte final és "+ce.getText());
    }
    
}

