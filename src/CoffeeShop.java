import java.util.LinkedList;
import java.util.Queue;
public class CoffeeShop {

    private final Queue<String> orderQueue; //Queue to hold customer orders
    private final int capacity;//maximum number of orders the queue can hold

    public CoffeeShop(int capacity) {
        this.orderQueue = new LinkedList<>();
        this.capacity = capacity;
    }

    //places an order in the queue. If the queue is full, the method will wait until space becomes available.
    public synchronized void placeOrder(String order) throws InterruptedException {
        while (orderQueue.size() == capacity){
            try{
                wait();//wait if the queue is full
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        Thread.sleep(500);
        orderQueue.add(order); //Add orders to the queue, waiting if necessary for space
        System.out.println(Thread.currentThread().getName() + " placed order: " + order);
        notifyAll();//notify waiting barista an order is available
    }

    //Prepares an order from the queue. If the queue is empty, the method will wait until an order becomes available.
    public synchronized void prepareOrder() throws InterruptedException {
        while (orderQueue.isEmpty()){
            try{
                wait();//wait if the queue is empty
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        Thread.sleep(1000);
        String order = orderQueue.poll(); //Retrieves and removes the order from the queue, waiting if necessary.
        System.out.println(Thread.currentThread().getName() + " prepared order: " + order);
        notifyAll(); //notify a waiting customer that space is available
    }
}
