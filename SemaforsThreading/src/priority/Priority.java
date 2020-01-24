package exemple.priority;

/**
 *
 * @author Jordi
 */
public class Priority implements Runnable{
    int contador;   // Contarà el nombre d'interaccions del bucle
    Thread fil;     // Fil intern
    static boolean stop = false;

    public Priority(String nom) {
        contador = 0;
        fil = new Thread(this, nom);
    }

    @Override
    public void run() {
        System.out.println("Iniciant "+fil.getName());
        do {
            contador++;
        }while (stop == false && contador<10000);
        stop = true;
        System.out.println("\n"+fil.getName()+" finalitzat");
    }
    
    
}
