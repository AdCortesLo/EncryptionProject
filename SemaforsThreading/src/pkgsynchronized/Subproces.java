package pkgsynchronized;

/**
 *
 * @author Jordi
 */
public class Subproces implements Runnable{
    Thread fil;
    static SumArray sa = new SumArray();
    int enters[];
    int resposta;

    public Subproces(String nom, int[] enters) {
        fil = new Thread(this, nom);
        this.enters = enters;
        fil.start();
    }
    public void run(){
        System.out.println("Iniciant "+fil.getName());
        /* 
            Crida a mètode synchronized
            El fil es col·loca en espera si un altre fil l'està utilitzant.
        */
        resposta = sa.sumaArray(enters);   
        System.out.println("Terminant "+fil.getName());
    }
    
    
}
