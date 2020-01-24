/*
    Genera un fil que accedeix a un String static definit a la class princial
    i a un objecte passat per paràmetres.
 */
package comunicacio;

/**
 *
 * @author Jordi
 */
public class Subproces implements Runnable{
    Thread fil;
    ClassExemple ce1;
    public Subproces(String nom, ClassExemple ce){
        ce1 = ce;
        fil = new Thread(this, nom);
        fil.start();
    }
    public void run(){
        ExempleComunicacio.textComu += (". Accedit des de el fil "+fil.getName());
        System.out.println("El nou text comú és "+ExempleComunicacio.textComu);
        
        System.out.println("El text de la class comuna és "+ce1.getText());
        ce1.setText(ce1.getText()+". Accedit des de "+fil.getName());
    } 
}
