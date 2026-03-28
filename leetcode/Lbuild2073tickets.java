import java.util.*;

public class Lbuild2073tickets {
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] tickets = {2, 3, 2};
        var result = solution.timeRequiredToBuy(tickets, 2);
        System.out.println("result: " + result);
    }

}

// simulation for https://leetcode.com/problems/time-needed-to-buy-tickets
class Solution {
    public int timeRequiredToBuy(int[] tickets, int k) {
        int time = 0;
        LinkedList<Person> queue = new LinkedList<>();

        for (int i = 0; i < tickets.length; i++) {
            queue.add(new Person(i, tickets[i]));
        }

        // loop circularly
        while (true) {
            // buy ticket
            Person person = queue.removeFirst();
            person.ticketsToBuy--;
            time++;

            if (person.ticketsToBuy > 0) {
                queue.addLast(person);
                continue;
            }

            if (person.queuePosition == k) {
                break;
            }
        }
        
        return time;
    }
}

class Person {
    int queuePosition;
    int ticketsToBuy;
    
    public Person(int queuePosition, int ticketsToBuy) {
        this.queuePosition = queuePosition;
        this.ticketsToBuy = ticketsToBuy;
    }
}