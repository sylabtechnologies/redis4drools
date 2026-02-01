import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;

// https://leetcode.com/problems/design-ride-sharing-system/description/
public class Lcode487C {
    public static void main(String[] args) {
        var test = new RideSharingSystem();

        test.addRider(3);
        test.addDriver(2);
        test.addRider(1);
        var result = test.matchDriverWithRider();
        System.out.println("Solution: " + Arrays.asList(result[0], result[1]));
    }
}

class RideSharingSystem {

    private Queue<Integer> ridersQueue;
    private Queue<Integer> driversQueue;

    public RideSharingSystem() {
        driversQueue = new LinkedList<>();
        ridersQueue = new LinkedList<>();   
    }
    
    public void addRider(int riderId) {
        ridersQueue.add(riderId);
        System.out.println("Riders: " + ridersQueue);
    }
    
    public void addDriver(int driverId) {
        driversQueue.add(driverId);
        System.out.println("Drivers: " + driversQueue);
    }
    
    public int[] matchDriverWithRider() {
        if (ridersQueue.isEmpty() || driversQueue.isEmpty()) {
            return new int[] {-1, -1};
        }
        int riderId = ridersQueue.poll();
        int driverId = driversQueue.poll();
        return new int[] {driverId, riderId};
    }
    
    public void cancelRider(int riderId) {
        ridersQueue.remove(riderId);
    }
}