
// https://leetcode.com/problems/sort-an-array
// w/ quick sort

public class L0912quicksort {

    public static void main(String[] args) {
        int[] nums = {5,1,1,2,0,0};
        Solution solution = new Solution();
        var result = solution.sortArray(nums);
        System.out.println(java.util.Arrays.toString(result));
    }

}

class Solution {
    public int[] sortArray(int[] nums) {
        quicksort(nums, 0, nums.length - 1);
        return nums;
    }

    private void quicksort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivotIndex = selectMedianPivot(nums, left, right);
        int pivotValue = nums[pivotIndex];
        
        swap(nums, pivotIndex, right);
        int storeIndex = left;

        for (int i = left; i < right; i++) {
            if (nums[i] < pivotValue) {
                swap(nums, i, storeIndex);
                storeIndex++;
            }
        }
        swap(nums, storeIndex, right);

        quicksort(nums, left, storeIndex - 1);
        quicksort(nums, storeIndex + 1, right);
    }

    private int selectMedianPivot(int[] nums, int left, int right) {
        int mid = left + (right - left) / 2;
        int minIndex = -1;
        int maxIndex = -1;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        
        if (nums[left] < min) min = left;
        if (nums[mid] < min) min = mid;
        if (nums[right] < min) min = right;



        return mid;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}

