package exemple.thread;

/**
 *
 * @author Jordi
 */
public class EstendreThread extends Thread{

    public EstendreThread(String name) {
        super(name);
        start();
    }
    
    public void run() {
        System.out.println("Iniciant "+getName());
        try {
            for (int cont=0; cont<10; cont++){
                Thread.sleep(500); // El fil s'atura mig segon
                System.out.println(getName()+": "+cont);
            }
        }catch(InterruptedException exc) {
            System.out.println(getName()+ " interrumput");
        }
        System.out.println(getName()+" terminat");
    }
    
}
