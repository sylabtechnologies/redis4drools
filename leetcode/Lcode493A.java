import java.util.Arrays;

// https://leetcode.com/problems/count-commas-in-range/
public class Lcode494A {
    public static void main(String[] args) {
        Solution solution = new Solution();

        int number = 1002;
        var result = solution.countCommas(number);
        System.out.println("how many commas : " + result);
    }
}

class Solution {
    public int countCommas(int n) {
        if (n < 1000) {
            return 0;
        }
        else {
            return n - 1000 + 1;
        }
    }
}