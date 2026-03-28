import java.util.*;

// https://leetcode.com/problems/fruits-into-baskets-ii/
public class Lbuild3477fruitbaskets {
    public static void main(String[] args) {
        Solution solution = new Solution();

        int fruits[] = {3,6,1}, baskets[] = {6,4,7};

        var result = solution.numOfUnplacedFruits(fruits, baskets);
        System.out.println("result: " + result);
    }

}


/// naive solution: O(n*m) time, O(1) space
/// possible optimization: create a tree map where to start the scan
class Solution {
    public int numOfUnplacedFruits(int[] fruits, int[] baskets) {
        int unplacedFruits = fruits.length;

        for (int fruit : fruits) {
            for (int i = 0; i < baskets.length; i++) {
                if (baskets[i] < 0) continue;

                // can place
                if (fruit <= baskets[i]) {
                    baskets[i] = -1; // occupied
                    unplacedFruits--;
                    break;
                }
            }
        }

        return unplacedFruits;
    }
}