package pkgsynchronized;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Exemple d'us de la clausula synchronized en mètode sumaArray
 * 
 */
public class ExempleSynchronized {
    public static void main(String[] args) {
        
            int vector1[] = {1,2,3,4,5};
            int vector2[] = {6,7,8,9,10};
            
            Subproces sp1 = new Subproces("Fil 1", vector1);
            Subproces sp2 = new Subproces("Fil 2", vector2);
        try {    
            sp1.fil.join();
            sp2.fil.join();
        } catch (InterruptedException ex) {
            System.out.println("Fil principal interromput");
        }
    }
    
}
