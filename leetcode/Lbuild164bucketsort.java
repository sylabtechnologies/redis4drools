
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
        TreeMap<Integer, List<Integer>> buckets = new TreeMap<>();
        int bucketNo = Integer.MIN_VALUE;
        for (var x : nums) {
            if (x == hibucket) 
                bucketNo = n - 2;
            else {
                bucketNo = (int) (((long) x - lowbucket) * (n - 1) / (hibucket - lowbucket));
            }

            List<Integer> bucket = buckets.get(bucketNo);
            if (bucket == null) {
                bucket = new ArrayList<>();
                buckets.put(bucketNo, bucket);
            }
            bucket.add(x);
        }

        long gap = Integer.MIN_VALUE;
        boolean firstTime = true;
        int prevMin = Integer.MAX_VALUE;
        int prevMax = Integer.MIN_VALUE;

        for (int i = 0; i < n - 1; i++) {
            var bucket = buckets.get(i);
            // System.out.println("bucketNo: " + i + " bucket: " + bucket);;
            if (bucket == null) continue;
            
            var maxmin = maxmin(bucket);
            if (firstTime) {
                prevMax = maxmin[0];
                prevMin = maxmin[1];
                firstTime = false;
                continue;
            }

            var bucketMax = maxmin[0];
            var bucketMin = maxmin[1];
            gap = Math.max(gap, (long) bucketMin - prevMax);
            prevMax = maxmin[0];
            prevMin = maxmin[1];
        }

        return (int) gap;
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

    private int[] maxmin(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int x : list) {
            max = Math.max(max, x);
            min = Math.min(min, x);
        }
        return new int[]{max, min};
    }
}
