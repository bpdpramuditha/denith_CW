import java.math.BigDecimal;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        BankAccount account1 = new BankAccount(1, new BigDecimal(1000));
        BankAccount account2 = new BankAccount(2, new BigDecimal(1000));
        BankAccount account3 = new BankAccount(3, new BigDecimal(1000));

        TransactionSystem transactionSystem = new TransactionSystem(
                Arrays.asList(account1, account2, account3)
        );

        Thread transaction1 = new Thread(() -> transactionSystem.transfer(1, 2, new BigDecimal(200)) ,"Transaction1");
        Thread transaction2 = new Thread(() -> transactionSystem.transfer(1, 2, new BigDecimal(900)) ,"Transaction2");
        Thread transaction3 = new Thread(() -> transactionSystem.transfer(2, 3, new BigDecimal(300)),"Transaction3");
        Thread transaction4 = new Thread(() -> transactionSystem.transfer(3, 1, new BigDecimal(100)),"Transaction4");
        Thread transaction5 = new Thread(() -> {System.out.println("Balance of Account 1: " + transactionSystem.getAccount(1).getBalance());
        System.out.println("Balance of Account 3: " + transactionSystem.getAccount(3).getBalance());
    }, "BalanceReader");


        transaction1.start();
        transaction2.start();
        transaction3.start();
        transaction4.start();
        transaction5.start();

        try {
            transaction1.join();
            transaction2.join();
            transaction3.join();
            transaction4.join();
            transaction5.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        transactionSystem.printAccountBalances();
    }
}
