import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        BankAccount account1 = new BankAccount(1, new BigDecimal(1000));
        BankAccount account2 = new BankAccount(2, new BigDecimal(1000));
        BankAccount account3 = new BankAccount(3, new BigDecimal(1000));

        Thread transaction1 = new Thread(new Transaction(account1, account2, new BigDecimal(100)),"Transaction1");
        Thread transaction2 = new Thread(new Transaction(account2, account3, new BigDecimal(200)),"Transaction2");
        Thread transaction3 = new Thread(new Transaction(account3, account1, new BigDecimal(250)),"Transaction3");

        Thread balanceReader = new Thread(() -> {
            System.out.println("Balance of Account 1: " + account1.getBalance());
            System.out.println("Balance of Account 2: " + account2.getBalance());
            System.out.println("Balance of Account 3: " + account3.getBalance());
        }, "BalanceReader 1");

        transaction1.start();
        transaction2.start();
        transaction3.start();

        balanceReader.start();

        try {
            transaction1.join();
            transaction2.join();
            transaction3.join();
            balanceReader.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
