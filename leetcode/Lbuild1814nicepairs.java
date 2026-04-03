// https://leetcode.com/problems/count-nice-pairs-in-an-array/
public class Lbuild1814nicepairs {
    public static void main(String[] args) {
        int nums[] =  {42,11,1,97};
        var s = new Solution();
        System.out.println(s.countNicePairs(nums));
    }
}

class Solution {
    public int countNicePairs(int[] nums) {
        int count = 0;
        int n = nums.length;

        if (n < 2) return 0;

        // xform into diff
        int diff[] = new int[n];
        for (int i = 0; i < n; i++) {
            diff[i] = nums[i] - (int)reverse(nums[i]);
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (diff[i] == diff[j]) {
                    count++;
                }

            }
        }

        return count;
    }

    private long reverse(int num) {
        long rev = 0;
        while (num > 0) {
            rev = rev * 10 + num % 10;
            num /= 10;
        }
        return rev;
    }

}