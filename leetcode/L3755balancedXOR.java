import java.util.*;

// https://leetcode.com/problems/find-maximum-balanced-xor-subarray-length/
// DP technique - the state must be equal to its first occurance 
// Use Java records hashCode

public class L3755balancedXOR {

    public static void main(String[] args) {
        int nums[] = {3,1,3,2,0};
        Solution solution = new Solution();
        int n = solution.maxBalancedSubarray(nums);
        System.out.print("result: " + n);
    }
}

class Solution {
    public int maxBalancedSubarray(int[] nums) {
        int len = nums.length;
        if (len < 2) return 0;

        HashMap<State, Integer> map = new HashMap<>();

        int currentSum = 0;
        int currentXOR = 0;
        int maxLength = 0;        

        // Base case: a sum of 0 exists at index -1
        map.put(new State(0, 0), -1);
        
        for (int i = 0; i < nums.length; i++) {
            // Treat odd as 1, even as -1
            currentSum += (nums[i] % 2 != 0) ? 1 : -1;
            currentXOR ^= nums[i];

            State currentState = new State(currentSum, currentXOR);

            if (map.containsKey(currentState)) {
                int prevIndex = map.get(currentState);
                maxLength = Math.max(maxLength, i - prevIndex);
            } else {
                // Store the first occurrence of this prefix sum
                map.put(currentState, i);
            }
        }
        
        return maxLength;
    }    
}

record State (int sum, int xor) {}
