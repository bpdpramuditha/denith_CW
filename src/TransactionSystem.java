import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//manage accounts and ensure deadlocks free, concurrent, and fair transaction
public class TransactionSystem{
    private final Map<Integer, BankAccount> accounts = new HashMap<>();
    public TransactionSystem(List<BankAccount> accountList) {
        for (BankAccount account: accountList){
            accounts.put(account.getId(), account);
        }
    }

    //Transfers money between accounts with safety, fairness, and deadlock prevention.
    public void transfer(int fromAccountId, int toAccountId, BigDecimal amount) {
        BankAccount fromAccount = accounts.get(fromAccountId);
        BankAccount toAccount = accounts.get(toAccountId);

        // Deadlock prevention, always lock accounts in order of IDs scenario where two threads performing transfers between the same accounts
        BankAccount firstAccountLock = fromAccountId < toAccountId? fromAccount : toAccount;
        BankAccount secondAccountLock = fromAccountId < toAccountId ? toAccount : fromAccount;

        firstAccountLock.lock();
        try {
            secondAccountLock.lock();
            try {
                // Perform transaction if sufficient balance in the account
                if (fromAccount.getBalance().compareTo(amount) >= 0) {
                    try {
                        fromAccount.withdraw(amount);
                        toAccount.deposit(amount);
                        System.out.println(Thread.currentThread().getName() + " transferred $" + amount +
                                " from Account " + fromAccount.getId() +
                                " to Account " + toAccount.getId());
                    }catch (InterruptedException e){
                        // Reverse the transaction if an error occurs
                        reverseTransaction(fromAccountId, toAccountId, amount);
                        System.out.println("Transaction failed: " + e.getMessage());
                    }
                }
                else{
                    System.out.println(Thread.currentThread().getName() + ": Insufficient funds in Account " + fromAccount.getId());
                }
            } finally {
                secondAccountLock.unlock();
            }
        } finally {
            firstAccountLock.unlock();
        }
    }

    // Reverse transaction to restore account balance in case of an error
    public void reverseTransaction(int fromAccountId, int toAccountId, BigDecimal amount) {
        System.out.println("Reversing Transaction ....");
        BankAccount fromAccount = accounts.get(fromAccountId);
        BankAccount toAccount = accounts.get(toAccountId);
        try {
            toAccount.withdraw(amount);
            fromAccount.deposit(amount);
            System.out.println("Reversed $" + amount + " from Account " + toAccountId + " to Account " + fromAccountId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //print balance of all accounts
    public void printAccountBalances() {
        for (BankAccount account : accounts.values()) {
            System.out.println("Account " + account.getId() + " Balance: $" + account.getBalance());
        }
    }

    public BankAccount getAccount(int accountID) {
        return accounts.get(accountID);
    }
}
