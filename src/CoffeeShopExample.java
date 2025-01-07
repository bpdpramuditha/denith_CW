public class CoffeeShopExample {
    public static void main(String[] args) {
        CoffeeShop shop = new CoffeeShop(4);

        new Thread(new Barista(shop), "Barista " + 1).start();
        new Thread(new Barista(shop), "Barista " + 2).start();
        new Thread(new Barista(shop), "Barista " + 3).start();
        new Thread(new Barista(shop), "Barista " + 4).start();

        new Thread(new Customer(shop), "Customer " + 1).start();
        new Thread(new Customer(shop), "Customer " + 2).start();
        new Thread(new Customer(shop), "Customer " + 3).start();
        new Thread(new Customer(shop), "Customer " + 4).start();
        new Thread(new Customer(shop), "Customer " + 5).start();
        new Thread(new Customer(shop), "Customer " + 6).start();
        new Thread(new Customer(shop), "Customer " + 7).start();
        new Thread(new Customer(shop), "Customer " + 8).start();
        new Thread(new Customer(shop), "Customer " + 9).start();
        new Thread(new Customer(shop), "Customer " + 10).start();
    }
}