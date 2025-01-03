public class CoffeeShopExample {
    public static void main(String[] args) {
        CoffeeShop shop = new CoffeeShop(1);
        for (int i = 0; i < 3; i++) {
            new Thread(new Customer(shop), "Customer " + i).start();
            new Thread(new Barista(shop), "Barista " + i).start();
        }
    }
}