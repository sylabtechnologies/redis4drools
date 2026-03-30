// https://leetcode.com/problems/keys-and-rooms
import java.util.*;

public class Lbuild841visitrooms {
    public static void main(String[] args) {
        Solution solution = new Solution();

        List<List<Integer>> rooms = Arrays.asList(
            Arrays.asList(1, 3),
            Arrays.asList(3, 0, 1),
            Arrays.asList(2),
            Arrays.asList(0),
            Arrays.asList()
        );

        var result = solution.canVisitAllRooms(rooms);
        System.out.println("result: " + result);
        
    }
}

class Solution {
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        boolean[] visited = new boolean[rooms.size()];
        Deque<Integer> canVisit = new LinkedList<>();
        canVisit.add(0);

        int visitCount = 0;
        while (!canVisit.isEmpty()) {
            var  room = canVisit.poll();
            if (visited[room]) continue;

            visited[room] = true;
            visitCount++;

            for (Integer key : rooms.get(room)) {
                canVisit.addLast(key);
            }
        }

        return visitCount == rooms.size();
    }
}