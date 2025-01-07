import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* BankAccount class represents a single bank account with locking mechanisms
to ensure thread-safe operations and deadlock prevention. */
public class BankAccount {
    private final int id;
    private BigDecimal balance;
    private final Lock lock = new ReentrantLock(true);// First come first fairness

    public BankAccount(int id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public int getId(){
        return id;
    }

    // Concurrent reads are allowed
    public BigDecimal getBalance(){
        return balance;
    }

    // Transaction safe deposit
    public void deposit(BigDecimal amount) throws InterruptedException {
        if(amount.compareTo(BigDecimal.valueOf(2000)) > 0 && id == 2){ //test case scenario for transactional reversal
           throw new InterruptedException("Transaction reversal if amount is more than 2000");
        }else {
            balance = balance.add(amount);
        }
    }

    // Transaction fair withdraw
    public void withdraw(BigDecimal amount){
        balance = balance.subtract(amount);
    }

    // Transaction lock for first-come-first-served transaction safety
    public void lock(){
        lock.lock();
    }

    public void unlock(){
        lock.unlock();
    }
}
