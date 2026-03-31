import java.util.ArrayDeque;
import java.util.Deque;
import java.util.TreeMap;

public class RechargeTime {

    public static void main(String[] args) {
        int[] caps = {7, 5, 13};
        int[] recharges = {10, 10, 10}; // High recharge logic
        long t = 40;

        System.out.println("Total Usages: " + countUsages(caps, recharges, t));
    }

    
    static class Battery {
        int id;
        int capacity;
        int rechargeTime;
        long availableAt;

        public Battery(int id, int capacity, int rechargeTime) {
            this.id = id;
            this.capacity = capacity;
            this.rechargeTime = rechargeTime;
            this.availableAt = 0;
        }

        @Override
        public String toString() {
            return "Battery{id=" + id + ", cap=" + capacity + ", recharge=" + rechargeTime + ", availableAt=" + availableAt + "}";
        }
    }

    public static int countUsages(int[] capacities, int[] rechargeTimes, long targetTime) {
        int n = capacities.length;
        Deque<Battery> queue = new ArrayDeque<>();
        
        // TreeMap tracks: <AvailableTime, Number of batteries becoming ready at this time>
        // We use a Counter (Integer) because multiple batteries might have the same availableAt.
        TreeMap<Long, Integer> timeMap = new TreeMap<>();

        for (int i = 0; i < n; i++) {
            Battery b = new Battery(i, capacities[i], rechargeTimes[i]);
            queue.addLast(b);
            timeMap.put(b.availableAt, timeMap.getOrDefault(b.availableAt, 0) + 1);
        }

        long currentTime = 0;
        int totalUsages = 0;

        while (currentTime < targetTime) {
            Battery current = queue.removeFirst();

            System.out.println("getting " + current + " at time " + currentTime);

            // 1. O(1) check: Is there ANY battery ready right now?
            // We check if the smallest key in our TreeMap is <= currentTime
            long earliestInSystem = timeMap.firstKey();

            if (current.availableAt > currentTime) {
                if (earliestInSystem > currentTime) {
                    // DEADLOCK: No one is ready. Jump to the absolute earliest time.
                    currentTime = earliestInSystem;
                    queue.addFirst(current); // Process this battery immediately
                } else {
                    // Someone else is ready. Move 'current' to back and cycle.
                    System.out.println("charging " + current + " at time " + currentTime);
                    queue.addLast(current);
                }
                continue;
            }

            // 2. Use the battery
            // Before updating, remove the old time from the map
            removeTime(timeMap, current.availableAt);

            currentTime += current.capacity;
            totalUsages++;

            // 3. Update and cycle
            current.availableAt = currentTime + current.rechargeTime;
            queue.addLast(current);
            
            // Add the new time to the map
            timeMap.put(current.availableAt, timeMap.getOrDefault(current.availableAt, 0) + 1);
        }

        return totalUsages;
    }

    // Helper to manage the TreeMap counter
    private static void removeTime(TreeMap<Long, Integer> map, long time) {
        int count = map.get(time);
        if (count == 1) {
            map.remove(time);
        } else {
            map.put(time, count - 1);
        }
    }
}