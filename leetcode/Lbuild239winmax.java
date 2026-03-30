import java.util.*;

// https://leetcode.com/problems/sliding-window-maximum
public class Lbuild239winmax {

    public static void main(String[] args) {
        Lbuild239winmax instance = new Lbuild239winmax();
        int[] nums = {1,3,-1,-3,5,3,6,7};
        Solution sol = new Solution();
        int[] res = sol.maxSlidingWindow(nums, 3);
        System.out.println(Arrays.toString(res));
    }
}

// OK WALK W/ FREQ COUNT
class Solution
{
    public int[] maxSlidingWindow(int[] nums, int winLen)
    {
        int res[] = new int[nums.length - winLen + 1];

        TreeMap<Integer, Integer> win = new TreeMap<>();
        for (int i = 0; i < winLen; i++)
        {
            Integer elem = win.getOrDefault(nums[i], 0);
            win.put(nums[i], elem + 1);
        }
        
        res[0] = win.lastKey();
        
        int count = 1;
        for (int i = 1; i <= nums.length - winLen; i++)
        {
            int left = nums[i-1];
            Integer frq = win.get(left);
            if (frq == 1) {
                win.remove(left);
            }
            else {
                win.put(left, frq - 1);
            }
            
            int right = nums[i + winLen - 1];
            frq = win.getOrDefault(right, 0);
            win.put(right, frq + 1);
            res[count++] = win.lastKey();
        }
        
        return res;
    }
}
