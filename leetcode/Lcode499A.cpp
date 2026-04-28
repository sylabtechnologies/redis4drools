#include <iostream>
#include <vector>
#include <list>

// find valid elements
// https://leetcode.com/problems/valid-elements-in-an-array/description/ TOP SPEED!

using namespace std;

class Solution {
public:
    vector<int> findValidElements(vector<int>& nums) {
        int n = nums.size();
        if (n < 1) return {};

        int maxSoFar, minSoFar;
        maxSoFar = nums[0];

        // sweep right
        vector<char> isValid(nums.size(), false); // use char vs bool for speed
        isValid[0] = true;
        for (int i = 1; i < n; i++) {
            if (nums[i] > maxSoFar) {
                isValid[i] = true;
                if (nums[i] > maxSoFar) maxSoFar = nums[i];
            }
        }

        // sweep left
        maxSoFar = nums[n-1];
        isValid[n-1] = true;
        for (int i = n-2; i >= 0; i--) {
            if (nums[i] > maxSoFar) {
                isValid[i] = true;
                if (nums[i] > maxSoFar) maxSoFar = nums[i];
            }
        }

        vector<int> result;
        for (int i = 0; i < n; i++) {
            if (isValid[i]) result.push_back(nums[i]); 
        }

        return result;
    }
};