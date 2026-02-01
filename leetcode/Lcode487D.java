import java.util.ArrayList;
import java.util.Arrays;

// https://leetcode.com/problems/longest-alternating-subarray-after-removing-at-most-one-element/description/
public class Lcode487D {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int n = 5;
        int result = solution.countMonobit(n);
        System.out.println("Number of monobit numbers less than " + n + ": " + result);
    }
}

public record Pair<L, R>(L left, R right) {}

// GET OSCILLATION TRIPLES EAST N OPTIMIZE
class Solution {
    private ArrayList<Pair> oscillators = new ArrayList<>(100000);

    public int longestAlternating(int[] nums) {

        computeOscillators(nums);

        return 0;
    }

    private void computeOscillators(int[] nums) {
    }
   
}