#include <iostream>
#include <vector>
#include <list>
#include <algorithm>
#include <map>
#include <set>
#include <limits>
using namespace std;

// https://leetcode.com/problems/the-skyline-problem/
// Interval struct for skyline problem

struct Interval {
    int beg, end, height;
    Interval(int a, int b, int h = 0) : beg(a), end(b), height(h) {
        if (a >= b) {
            throw std::invalid_argument("bad interval");
        }
    }
    bool operator<(const Interval& other) const {
        if (beg != other.beg) return beg < other.beg;
        return end < other.end;
    }
    bool overlaps(const Interval& next) const {
        return !(end < next.beg || beg > next.end);
    }
    bool contains(const Interval& iv) const {
        return beg <= iv.beg && iv.end <= end;
    }
    Interval merge(const Interval& next) const {
        int nbeg = std::min(beg, next.beg);
        int nend = std::max(end, next.end);
        return Interval(nbeg, nend, height);
    }
};


class Solution {
public:
    vector<vector<int>> getSkyline(vector<vector<int>>& buildings) {

        vector<vector<int>> ret;
        if (buildings.empty()) return ret;

        // Map height to intervals
        map<int, vector<Interval>, greater<int>> levels;
        for (const auto& b : buildings)
            levels[b[2]].emplace_back(b[0], b[1], b[2]);

        set<Interval> zero;
        zero.insert(Interval(std::numeric_limits<int>::min(), std::numeric_limits<int>::max()));

        vector<Interval> solved;
        for (auto it = levels.begin(); it != levels.end(); ++it) {
            auto& curLevel = it->second;
            sort(curLevel.begin(), curLevel.end());
            // Merge intervals
            vector<Interval> merged;
            for (size_t i = 0; i < curLevel.size(); ) {
                Interval curr = curLevel[i++];
                while (i < curLevel.size() && curr.overlaps(curLevel[i])) {
                    curr = curr.merge(curLevel[i]);
                    ++i;
                }
                merged.push_back(curr);
            }
            curLevel = merged;

            for (const auto& curTop : curLevel) {
                vector<Interval> overlaps;
                auto first = zero.lower_bound(curTop);
                if (first != zero.begin()) --first;
                if (first != zero.end()) overlaps.push_back(*first);
                for (auto ii = zero.lower_bound(curTop); ii != zero.end(); ++ii) {
                    if (ii->beg > curTop.end) break;
                    overlaps.push_back(*ii);
                }
                for (const auto& test : overlaps) {
                    if (!test.overlaps(curTop)) continue;
                    if (test.end == curTop.beg || curTop.end == test.beg) continue;
                    zero.erase(test);
                    if (curTop.contains(test) || (curTop.beg == test.beg && curTop.end == test.end)) {
                        solved.push_back(Interval(test.beg, test.end, curTop.height));
                    } else if (test.contains(curTop)) {
                        if (test.beg != curTop.beg)
                            zero.insert(Interval(test.beg, curTop.beg));
                        if (test.end != curTop.end)
                            zero.insert(Interval(curTop.end, test.end));
                        solved.push_back(curTop);
                    } else if (test.end < curTop.end) {
                        solved.push_back(Interval(test.beg, test.end, curTop.height));
                        zero.insert(Interval(test.beg, curTop.beg));
                    } else {
                        solved.push_back(Interval(test.beg, curTop.end, curTop.height));
                        zero.insert(Interval(curTop.end, test.end));
                    }
                }
            }
        }

        for (const auto& iv : zero) {
            if (iv.beg == std::numeric_limits<int>::min() || iv.end == std::numeric_limits<int>::max()) continue;
            solved.push_back(iv);
        }
        sort(solved.begin(), solved.end(), [](const Interval& a, const Interval& b) {
            if (a.beg != b.beg) return a.beg < b.beg;
            return a.end < b.end;
        });
        int currH = 0;
        Interval last(0,0,0);
        for (const auto& iv : solved) {
            ret.push_back({iv.beg, iv.height});
            last = iv;
        }
        ret.push_back({last.end, 0});
        return ret;
    }

};

int main() {
    Solution solution;

    vector<vector<int>> buildings = {{2,9,10}, {3,7,15}, {5,12,12}, {15,20,10}, {19,24,8}};
    auto skyline = solution.getSkyline(buildings);
    for (const auto& point : skyline) {
        cout << "[" << point[0] << ", " << point[1] << "] ";
    }
    cout << endl;

    return 0;
}
