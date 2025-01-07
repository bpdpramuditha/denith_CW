public class Barista implements Runnable{

    private final CoffeeShop coffeeShop;
    public Barista(CoffeeShop coffeeShop) {
        this.coffeeShop = coffeeShop;
    }

    @Override
    public void run() {
        while (true) {
            try {
                coffeeShop.prepareOrder();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
