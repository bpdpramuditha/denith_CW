import java.math.BigDecimal;

public class Transaction implements Runnable{

    private final BankAccount fromAccount;
    private final BankAccount toAccount;
    private final BigDecimal amount;

    public Transaction(BankAccount fromAccount, BankAccount toAccount, BigDecimal amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    @Override
    public void run() {
        // lock accounts in a safe order based on their IDs to prevent Deadlock
        BankAccount firstLock = fromAccount.getId() < toAccount.getId() ? fromAccount : toAccount;
        BankAccount secondLock = fromAccount.getId() < toAccount.getId() ? toAccount : fromAccount;

        firstLock.getLock().lock();
        try {
            secondLock.getLock().lock();
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
                secondLock.getLock().unlock();
            }
        }finally {
            firstLock.getLock().unlock();
        }
    }
}
