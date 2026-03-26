import java.util.HashMap;

// https://leetcode.com/problems/find-the-score-difference-in-a-game/description/
public class Lcode490B {
    public static void main(String[] args) {
        Solution solution = new Solution();

        int n = 40585;
        var result = solution.isDigitorialPermutation(n);
        System.out.println("digitirial? " + result);
    }

}

class Solution {
    public boolean isDigitorialPermutation(int n) {
        var isDigitorial = false;

        if (n == 1 || n == 2) {
            isDigitorial = true;
        }
        else {
            var digits = String.valueOf(n).toCharArray();
            var frequency = new HashMap<Integer, Integer>();
            for (var digit : digits) {
                int digitValue = digit - '0';
                frequency.put(digitValue, frequency.getOrDefault(digitValue, 0) + 1);
            }
            // System.out.println("digitirial? " + frequency.toString());
            
            var freqStr = frequency.toString();

            if (freqStr.equals("{1=1, 4=1, 5=1}")) {
                isDigitorial = true;
            }
            else if (freqStr.equals("{0=1, 4=1, 5=2, 8=1}")) {
                isDigitorial = true;
            }
        }

        return isDigitorial;
    }
}