import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 1. Immutable State Representation
record Account(String id, long balanceCents) {}

public class BankEngine {
    
    // Thread-safe map for overall storage
    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    public void createAccount(String id, long initialBalanceCents) {
        if (initialBalanceCents < 0) throw new IllegalArgumentException("Negative balance");
        accounts.putIfAbsent(id, new Account(id, initialBalanceCents));
    }

    public void transfer(String fromId, String toId, long amountCents) {
        if (amountCents <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (fromId.equals(toId)) throw new IllegalArgumentException("Cannot transfer to self");

        // 2. Consistent Locking Order to Prevent Deadlocks
        String firstLock = fromId.compareTo(toId) < 0 ? fromId : toId;
        String secondLock = fromId.compareTo(toId) < 0 ? toId : fromId;

        // 3. Pessimistic Locking on specific accounts
        synchronized (firstLock.intern()) { 
            synchronized (secondLock.intern()) {
                
                // 4. Double-check state inside the lock
                Account from = accounts.get(fromId);
                Account to = accounts.get(toId);

                if (from == null || to == null) {
                    throw new IllegalArgumentException("Account not found");
                }

                // 5. Validation Guard
                if (from.balanceCents() < amountCents) {
                    throw new IllegalStateException("Insufficient funds");
                }

                // 6. Atomic Update using new Records
                accounts.put(fromId, new Account(fromId, from.balanceCents() - amountCents));
                accounts.put(toId, new Account(toId, to.balanceCents() + amountCents));
            }
        }
    }
    
    public long getBalance(String accountId) {
        Account acc = accounts.get(accountId);
        return acc != null ? acc.balanceCents() : 0L;
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