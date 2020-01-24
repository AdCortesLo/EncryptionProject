/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkginterface;

/**
 *
 * @author Jordi
 */
public class Interfacerunnable implements Runnable {
    Thread fil; // Emmagatzemo una referència al subproces

    public Interfacerunnable(String nom) {
        fil = new Thread(this, nom); //Assigno un nom al subprocés
        fil.start();
    }
    
    @Override
    public void run() {
        System.out.println("Iniciant "+fil.getName());
        try {
            for (int cont=0; cont<10; cont++){
                Thread.sleep(500); // El fil s'atura mig segon
                System.out.println(fil.getName()+": "+cont);
            }
        }catch(InterruptedException exc) {
            System.out.println(fil.getName()+ " interrumput");
        }
        System.out.println(fil.getName()+" terminat");
    }
    
}
