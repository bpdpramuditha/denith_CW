public class Customer implements Runnable{

    private final CoffeeShop coffeeShop;

    public Customer(CoffeeShop coffeeShop) {
        this.coffeeShop = coffeeShop;
    }

    @Override
    public void run() {
        try {
            coffeeShop.placeOrder("Coffee");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
