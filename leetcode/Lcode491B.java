import java.util.Arrays;

// https://leetcode.com/problems/minimum-cost-to-split-into-ones/description/
// feels like the path does not matter
public class Lcode491B {
    public static void main(String[] args) {
        Solution solution = new Solution();

        var result = solution.minCost(4);
        System.out.println("result: " + result);
    }

}

class Solution {
    public int minCost(int n) {
        return (n*(n-1))/2;
    }
}