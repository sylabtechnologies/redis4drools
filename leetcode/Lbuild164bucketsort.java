
import java.util.*;

// Leetcode 164 - use bucket sort
// https://leetcode.com/problems/maximum-gap
public class Lbuild164bucketsort {

    public static void main(String[] args) {
        int[] nums = {3,11,12,13};
        Solution s = new Solution();
        int ans = s.maximumGap(nums);
        System.out.println(ans);
    } 

}

class Solution {
    public int maximumGap(int[] nums) {
        int n = nums.length;

        if (n < 2) return 0;
        if (n == 2) return Math.max(nums[0] - nums[1], nums[1] - nums[0]);

        int[] minMaxResult = maxmin(nums);
        int hibucket = minMaxResult[0];
        int lowbucket = minMaxResult[1];

        if (lowbucket == hibucket) return 0;

        // create buckets
        BucketMinMax[] buckets = new BucketMinMax[n];

        int bucketNo = 0;
        for (var x : nums) {
            if (x == hibucket) 
                bucketNo = n - 1;
            else {
                bucketNo = (int) (((long) x - lowbucket) * (n - 1) / (hibucket - lowbucket));
            }

            if (buckets[bucketNo] == null) {
                buckets[bucketNo] = new BucketMinMax();
            }
            buckets[bucketNo].min = Math.min(buckets[bucketNo].min, x);
            buckets[bucketNo].max = Math.max(buckets[bucketNo].max, x);
        }

        long gap = 0;
        int prevMax = lowbucket;

        for (int i = 0; i < n; i++) {
            if (buckets[i] == null) continue;
            
            gap = Math.max(gap, (long) buckets[i].min - prevMax);
            prevMax = buckets[i].max;
        }

        return (int) gap;
    }

    class BucketMinMax {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
    }

    private int[] maxmin(int[] arr) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int x : arr) {
            max = Math.max(max, x);
            min = Math.min(min, x);
        }
        return new int[]{max, min};
    }
}