import java.util.concurrent.Semaphore;

public class FloorBathroom {
    private static final int BATHROOM_STALLS = 6;
    private static final int NUM_EMPLOYEES = 10;

    //Semaphore to control access to the stall
    private static final Semaphore stalls = new Semaphore(BATHROOM_STALLS, true);

    //Array to track of status each stall
    private static final boolean[] stallStatus = new boolean[BATHROOM_STALLS];

    public static void main (String [] args){
        for(int i = 1; i <= NUM_EMPLOYEES; i++){
            new Thread(new User(i)).start();
        }
    }

    static class User implements Runnable {
         private final int id;

         public User(int id){
             this.id = id;
        }

        @Override
        public void run(){
             int stallNumber = -1;
             try {
                 System.out.println("User " + id + " is waiting to use a stall");
                 //Acquire a stall
                 stalls.acquire();

                 synchronized (stallStatus) {
                     // Find an available stall and mark it as occupied
                     for (int i = 0; i < BATHROOM_STALLS; i++) {
                         if (!stallStatus[i]) {
                             stallStatus[i] = true;
                             stallNumber = i + 1;
                             break;
                         }
                     }
                 }

                 System.out.println("User " + id + " is using a stall " + stallNumber + ".");

                 Thread.sleep((long)(Math.random() * 3000));

                 //release the stall
                 synchronized (stallStatus){
                     stallStatus[stallNumber - 1] = false;
                 }

                 System.out.println("User " + id + " is leaving the stall " + stallNumber + ".");
                 stalls.release();

             }catch (InterruptedException e){
                 e.printStackTrace();
             } catch (IllegalStateException e) {
                 System.err.println(e.getMessage());
                 stalls.release(); //semaphore is released if exception occurs
             }
        }
    }
}

