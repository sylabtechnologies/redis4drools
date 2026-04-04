
https://leetcode.com/problems/count-elements-with-at-least-k-greater-values/

public class L3759Kgreater {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        int k = 1;

        Solution solution = new Solution();
        int result = solution.countElements(arr, k);
        System.out.println("result: " + result);
    }
}

class Solution {
    public int countElements(int[] nums, int k) {
        java.util.Arrays.sort(nums);

        int len = nums.length;
        if (len < 2) return 0;
        if (k == 0) return len;

        int howManyGreater[] = new int[len];
        howManyGreater[len - 1] = 0;
        int leftmax = nums[len - 1];

        for (int i = len - 2; i >=0; i--) {
            if (nums[i] < leftmax) {
                howManyGreater[i] = howManyGreater[i+1] + 1;
            }
            else {
                howManyGreater[i] = howManyGreater[i+1];
            }

            leftmax = nums[i];
        }

        // count 'em
        int count = 0;
        for(int i = 0; i < len; i++) {
            if (howManyGreater[i] >= k) count++;
        }

        return count;
    }
}
