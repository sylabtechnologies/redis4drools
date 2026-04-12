import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// 1. Mutable State Representation using Atomics
class Account {
    private final String id;
    private final AtomicLong balance;

    public Account(String id, long initialBalance) {
        this.id = id;
        this.balance = new AtomicLong(initialBalance);
    }

    public String getId() {
        return id;
    }

    public long getBalance() {
        return balance.get();
    }

    /**
     * Withdraws the specified amount using a lock-free Compare-And-Swap (CAS) mechanism.
     * 
     * How CAS prevents thread interference:
     * 1. The thread reads the current balance.
     * 2. If it has enough funds, it calculates the new balance.
     * 3. It calls `compareAndSet` to update the balance ONLY IF the current balance hasn't changed.
     * 4. If another thread modified the balance in the meantime, `compareAndSet` returns false,
     *    and the `while (true)` loop retries the operation with the updated balance.
     * This optimistic approach guarantees thread-safety (atomicity) without blocking other threads,
     * avoiding costly synchronizations and potential deadlocks.
     */
    public boolean withdraw(long amount) {
        while (true) {
            long current = balance.get();
            if (current < amount) {
                return false; // Insufficient funds
            }
            // Compare and set loop for atomic updates
            if (balance.compareAndSet(current, current - amount)) {
                return true;
            }
        }
    }

    public void deposit(long amount) {
        balance.addAndGet(amount);
    }
}

public class BankEngineCAS {
    
    // Thread-safe map for overall storage
    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    public void createAccount(String id, long initialBalanceCents) {
        if (initialBalanceCents < 0) throw new IllegalArgumentException("Negative balance");
        accounts.putIfAbsent(id, new Account(id, initialBalanceCents));
    }

    public void transfer(String fromId, String toId, long amountCents) {
        if (amountCents <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (fromId.equals(toId)) throw new IllegalArgumentException("Cannot transfer to self");

        Account from = accounts.get(fromId);
        Account to = accounts.get(toId);

        if (from == null || to == null) {
            throw new IllegalArgumentException("Account not found");
        }

        // 2. Lock-free Atomic Transfer
        if (!from.withdraw(amountCents)) {
            throw new IllegalStateException("Insufficient funds");
        }
        
        to.deposit(amountCents);
    }
    
    public long getBalance(String accountId) {
        Account acc = accounts.get(accountId);
        return acc != null ? acc.getBalance() : 0L;
    }

    public static void main(String[] args) throws InterruptedException {
        BankEngine engine = new BankEngine();

        System.out.println("Creating accounts A (1000 cents) and B (500 cents)...");
        engine.createAccount("A", 1000);
        engine.createAccount("B", 500);

        System.out.println("Initial Balance A: " + engine.getBalance("A"));
        System.out.println("Initial Balance B: " + engine.getBalance("B"));

        System.out.println("\nTransferring 200 cents from A to B...");
        engine.transfer("A", "B", 200);

        System.out.println("Balance A: " + engine.getBalance("A"));
        System.out.println("Balance B: " + engine.getBalance("B"));

        System.out.println("\nSimulating concurrent transfers (A -> B: 100, B -> A: 50)...");
        Thread t1 = new Thread(() -> engine.transfer("A", "B", 100));
        Thread t2 = new Thread(() -> engine.transfer("B", "A", 50));

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        // 1000 - 200 - 100 + 50 = 750
        System.out.println("Final Balance A: " + engine.getBalance("A") + " (Expected: 750)");
        // 500 + 200 + 100 - 50 = 750
        System.out.println("Final Balance B: " + engine.getBalance("B") + " (Expected: 750)");
    }
}