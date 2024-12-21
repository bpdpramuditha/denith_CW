import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionSystem{
    private final Map<Integer, BankAccount> accounts = new HashMap<>();

    public TransactionSystem(List<BankAccount> accountList) {
        for (BankAccount account: accountList){
            accounts.put(account.getId(), account);
        }
    }
    public void transfer(int fromAccountId, int toAccountId, BigDecimal amount) {
        BankAccount fromAccount = accounts.get(fromAccountId);
        BankAccount toAccount = accounts.get(toAccountId);

        BankAccount firstLock = fromAccountId < toAccountId? fromAccount : toAccount;
        BankAccount secondLock = fromAccountId < toAccountId ? toAccount : fromAccount;

        firstLock.lock();
        try {
            secondLock.lock();
            try {
                // Perform transaction if sufficient balance in the account
                if (fromAccount.getBalance().compareTo(amount) >= 0){
                    fromAccount.withdraw(amount);
                    toAccount.deposit(amount);
                    System.out.println(Thread.currentThread().getName() + " transferred $" + amount +
                            " from Account " + fromAccount.getId() +
                            " to Account " + toAccount.getId());
                }else {
                    System.out.println(Thread.currentThread().getName() + ": Insufficient funds in Account " + fromAccount.getId());
                }
            } finally {
                secondLock.unlock();
            }
        } finally {
            firstLock.unlock();
        }
    }

    public void reverseTransaction(int fromAccountId, int toAccountId, BigDecimal amount){
        System.out.println("Reversing Transaction ....");
        transfer(toAccountId, fromAccountId, amount);
    }

    public void printAccountBalances() {
        for (BankAccount account : accounts.values()) {
            System.out.println("Account " + account.getId() + " Balance: $" + account.getBalance());
        }
    }
}
