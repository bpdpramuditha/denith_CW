import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class CoffeeShop {

    /**Use thread safe BlockingQueue to handle customer orders and barista preparations **/
    private final BlockingQueue<String> orderQueue; //Queue to hold customer orders

    /** Parameter capacity maximum number of orders the queue can hold **/
    public CoffeeShop(int capacity) {
        this.orderQueue = new ArrayBlockingQueue<>(capacity);
    }

    /** Places an order in the queue. If the queue is full, the method will block until space becomes available.
    Parameter order the order to be added to the queue. **/
    public synchronized void placeOrder(String order) throws InterruptedException {
        orderQueue.put(order); //Add orders to the queue, waiting if necessary for space
        System.out.println(Thread.currentThread().getName() + " placed order: " + order);
    }

    /**Prepares an order from the queue. If the queue is empty, the method will block until an order becomes available. **/
    public synchronized void prepareOrder() throws InterruptedException {
        String order = orderQueue.take(); //Retrieves and removes the order from the queue, waiting if necessary.
        System.out.println(Thread.currentThread().getName() + " prepared order: " + order);
    }

}
