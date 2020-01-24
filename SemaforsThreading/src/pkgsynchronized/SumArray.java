package pkgsynchronized;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jordi
 */
public class SumArray {
    private int suma;
    
    /* 
        El mètode es bloqueja si l'ha cridat un subprocés.
        Si un segon subprocés intenta accedir al mètode es col·loca en
        espera fins que estigui disponible.
        suma el contingut de l'array passat per paràmetres.
    */
    synchronized int sumaArray(int nums[]){
        suma=0;
        for (int i=0; i<nums.length; i++){
            suma+=nums[i];
            System.out.println("Subtotal en "+Thread.currentThread().getName()+" es "+suma);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println("Fil interromput");
            }
        }
        return suma;
    }
}
