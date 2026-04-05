
// https://leetcode.com/problems/sort-an-array
// good performance with heap sort

public class L0912heapify {

    public static void main(String[] args) {
        int[] nums = {5,1,1,2,0,0};
        Solution solution = new Solution();
        var result = solution.sortArray(nums);
        System.out.println(java.util.Arrays.toString(result));
    }

}

class Solution {
    public int[] sortArray(int[] nums) {
        if (nums.length < 2) return nums;

        heapify(nums);
        int[] sorted = new int[nums.length];
        
        int n = nums.length;
        for (int i = 0; i < nums.length; i++) {
            sorted[i] = nums[0];
            swap(nums, 0, n - 1);
            n--;
            heapifyNode(nums, n, 0);
        }

        return sorted;
    }

    public void heapify(int[] nums) {
        int n = nums.length;

        // Start from the last non-leaf node and heapify each node
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapifyNode(nums, n, i);
        }
    }

    private void heapifyNode(int[] nums, int n, int i) {
        int smallest = i; // Initialize smallest as root
        int left = 2 * i + 1; // left child index
        int right = 2 * i + 2; // right child index

        // If left child is smaller than root
        if (left < n && nums[left] < nums[smallest]) {
            smallest = left;
        }

        // If right child is smaller than smallest so far
        if (right < n && nums[right] < nums[smallest]) {
            smallest = right;
        }

        // If smallest is not root
        if (smallest != i) {
            swap(nums, i, smallest); // Swap root with smallest

            // Recursively heapify the affected sub-tree
            heapifyNode(nums, n, smallest);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}