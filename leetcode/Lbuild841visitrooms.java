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
        Set<Integer> visited = new HashSet<>();
        Set<Integer> canVisit = new HashSet<>();
        canVisit.add(0);

        while (!canVisit.isEmpty()) {
            Set<Integer> nextVisit = new HashSet<>();

            for (Integer room : canVisit) {
                if (visited.contains(room)) {
                    continue;
                }

                visited.add(room);

                // append keys in this room to nextVisit
                for (Integer key : rooms.get(room)) {
                        nextVisit.add(key);
                }
            }

            canVisit = nextVisit;
        }

        return visited.size() == rooms.size();        
    }
}