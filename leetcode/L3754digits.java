import java.util.*;

// https://leetcode.com/problems/concatenate-non-zero-digits-and-multiply-by-sum-i

public class L3754digits {
    public static void main(String[] args) {
        Solution sol = new Solution();
        int n = 10203004; // Example input
        long result = sol.sumAndMultiply(n);
        System.out.println("The result is: " + result);
    }
}

class Solution {
    public long sumAndMultiply(int n) {
        if (n == 0) {
            return 0;
        }

        StringBuilder ans = new StringBuilder();
        long sum = 0;
        while (n > 0) {
            int digit = n % 10;
            n /= 10;

            if (digit != 0) {
                sum += digit;
                ans.insert(0, digit);
            }

            // System.out.println("Current digit: " + digit + ", Sum: " + sum + ", Ans: " + ans);
        }

        return sum * Integer.parseInt(ans.toString());
    }
}
