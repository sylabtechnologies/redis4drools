#include <iostream>
#include <vector>
#include <list>

// https://leetcode.com/problems/count-indices-with-opposite-parity/ TURTLE SPEED!

using namespace std;

class Solution {
public:
    vector<int> countOppositeParity(vector<int>& nums) {
        vector<int> ans;

        if (nums.empty()) return ans;
        int *parity = new int[nums.size()];

        for (size_t i = 0; i < nums.size(); i++) {
            parity[i] = nums[i] % 2;
        }

        for (size_t i = 0; i < nums.size() - 1; i++) {
            int count = 0;
            for (size_t j = i + 1; j < nums.size(); j++) {
                if (parity[i] != parity[j]) {
                    count++;
                }
            }

            ans.push_back(count);
        }        

        ans.push_back(0);
        delete[] parity;
        return ans;
    }
};