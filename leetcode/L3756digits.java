import java.util.*;

// https://leetcode.com/problems/concatenate-non-zero-digits-and-multiply-by-sum-ii

public class L3756digits {
    public static void main(String[] args) {
        Solution sol = new Solution();
        // String s = "10203004";
        // int query[][] =  {{0,7},{1,3},{4,6}};
        String s = "2711785625";
        int query[][] =  {{0,2}};
        var result = sol.sumAndMultiply(s, query);
        System.out.println("The result is: " + Arrays.toString(result));
    }
}

class Solution {
    private final static long MYMODULO = 1000000007;

    public int[] sumAndMultiply(String s, int[][] queries) {
        int m = s.length();

        int sums[] = new int[m];

        for (int i = 0; i < m; i++) {
            int digit = s.charAt(i) - '0';

            if (i == 0) {
                sums[i] = digit;
            }
            else {
                sums[i] = digit + sums[i-1];
            }
        }

        int ans[] = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            var q = queries[i];
            int start = q[0];
            int end = q[1];

            long operand1 = start > 0 ? sums[end] - sums[start - 1] : sums[end];

            var mySubstring = s.substring(start, end + 1);
            StringBuilder digits = new StringBuilder();
            for (char c : mySubstring.toCharArray()) {
                if (c != '0') {
                    digits.append(c);
                }
            }
            System.out.println(Arrays.toString(q));
            System.out.println(digits.toString());

            long operand2 = digits.isEmpty() ? 0 : Integer.parseInt(digits.toString());
            System.out.println(digits.toString());

            ans[i] = (int) ((operand1 * operand2) % MYMODULO);
        }

        return ans;
    }
}
