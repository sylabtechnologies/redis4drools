import java.util.*;

// https://leetcode.com/problems/koko-eating-bananas/ BIN SEARCH
public class LcodeNo857koko {
    public static void main(String[] args) {
        Solution solution = new Solution();

        var result = solution.minEatingSpeed(new int[]{1000000000,1000000000}, 3);
        System.out.println("result: " + result);
    }

}

class Solution {
    public int minEatingSpeed(int[] piles, int h) {

        Arrays.sort(piles);
//        double max4BigOne = piles[piles.length - 1] /  (double) h;

        int maxSpeed = piles[piles.length - 1];
        maxSpeed++;
        int minSpeed = 1;

        // bin search for k
        while (minSpeed < maxSpeed) {
            int middle = minSpeed + (maxSpeed - minSpeed) / 2;
            if (canEat(piles, h, middle)) {
                maxSpeed = middle;
            } else {
                minSpeed = middle + 1;
            }
        }

        return minSpeed;
        
    }

    private boolean canEat(int[] piles, int h, int k) {
        int hours = 0;
        for (int pile : piles) {
            hours += (pile + k - 1) / k;
        }
        return hours <= h;
    }
}