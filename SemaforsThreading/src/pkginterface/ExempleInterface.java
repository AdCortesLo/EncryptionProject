package pkginterface;

/**
 *
 * @author Jordi
 */
public class ExempleInterface {


    public static void main(String[] args) throws InterruptedException {
        System.out.println("Prova d'inicialització de la class Runnable");
        Interfacerunnable ir = new Interfacerunnable("fil 1");
        
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
