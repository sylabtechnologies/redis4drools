import java.util.*;

public class digits {
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

        long ans = n;
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            n /= 10;
            if (digit == 0) continue;
            sum += digit;
        }

        return sum * ans;
    }
}
