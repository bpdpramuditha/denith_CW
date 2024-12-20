import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** BankAccount class represents a single bank account with locking mechanisms
to ensure thread-safe operations and deadlock prevention.**/
public class BankAccount {
    private final int id;
    private BigDecimal balance;
    private final Lock lock = new ReentrantLock(true);

    public BankAccount(int id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public int getId(){
        return id;
    }

    public BigDecimal getBalance(){
        return balance;
    }

    public void deposit(BigDecimal amount){
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount){
        balance = balance.subtract(amount);
    }

    public Lock getLock(){
        return lock;
    }
}
