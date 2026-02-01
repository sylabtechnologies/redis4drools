import java.util.Arrays;

// https://leetcode.com/problems/count-monobit-integers/description/
public class Lcode487A {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int n = 5;
        int result = solution.countMonobit(n);
        System.out.println("Number of monobit numbers less than " + n + ": " + result);
    }
}

class Solution {
    
    // count by the power of 2
    private int[] monobitCount = {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public int countMonobit(int n) {
        if (n == 0) return 1;
        if (n == 1) return 2;
        
        for (int i = 2; i < 10; i++ ) 
        {
            int number = (int) Math.pow(2, i);
            if (number <= n)
                monobitCount[i] = 1;
            else {
                int powerOf2 = Integer.highestOneBit(n + 1);
                if (n+1 == powerOf2)
                    monobitCount[i] = 1;
                break;
            }
        }
        
        return Arrays.stream(monobitCount).sum();
    }
}