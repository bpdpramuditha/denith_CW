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
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true); // fair lock for concurrent read/writes

    public BankAccount(int id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public int getId(){
        return id;
    }

    // Concurrent reads are allowed
    public BigDecimal getBalance(){
        readWriteLock.readLock().lock();
        try {
            return balance;
        }finally {
            readWriteLock.readLock().unlock();
        }
    }

    // Transaction safe deposit
    public void deposit(BigDecimal amount){
        readWriteLock.writeLock().lock();
        try {
            balance = balance.add(amount);
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    // Transaction fair withdraw
    public void withdraw(BigDecimal amount){
        readWriteLock.writeLock().lock();
        try {
            balance = balance.subtract(amount);
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    // Transaction lock for first-come-first-served transaction safety
    public void lock(){
        lock.lock();
    }

    public void unlock(){
        lock.unlock();
    }

}
