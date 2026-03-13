#include <iostream>
#include <string>
#include <algorithm>
#include <vector>
#include <unordered_map>
#include <set>
#include <list>

using namespace std;

class Interval {
    int start;
    int end;

public:
    Interval(int start, int end) : start(start), end(end) {}

    int getStart() const { return start; }
    int getEnd() const { return end; }

    bool overlaps(const Interval &i) const {
        if (this->end < i.start) return false;
        if (this->start > i.end) return false;
        return true;
    }

    bool contains(const Interval &iv) const {
        return this->start <= iv.start && iv.end <= this->end;
    }

    bool equals(const Interval &iv) const {
        return iv.start == this->start && iv.end == this->end;
    }

    string to_string() const {
        return "[" + std::to_string(this->start) + "," + std::to_string(this->end) + "]";
    }

    Interval merge(const Interval &next) const {
        int nstart = min(this->start, next.start);
        int nend = max(this->end, next.end);
        return Interval(nstart, nend);
    }

    bool operator<(const Interval &ii) const {
        if (this->overlaps(ii))
            throw invalid_argument("overlapping");

        if (this->start < ii.start) return true;
        if (this->start > ii.start) return false;
        if (this->start < ii.start) return true;
        if (this->start > ii.start) return false;

        throw invalid_argument("overlapping again");
    }
};

class Solution {
public:
    const int MY_MAX =  2147483640;
    const int MY_MIN = -2147483640;

    vector<vector<int>> getSkyline(vector<vector<int>>& buildings) {
        vector<vector<int>> answer;
        if (buildings.empty()) return answer;

        // insert all buildings by a level
        unordered_multimap<int, Interval> levels;
        for (const auto& b : buildings) {
            auto interval = Interval(b[0], b[1]);
            levels.insert({b[2], interval});            
        }

        set<int> heights_set;
        for (const auto& kv : levels)
            heights_set.insert(kv.first);
        vector<int> heights(heights_set.rbegin(), heights_set.rend());
        trace(heights);

        set<Interval> groundLevel;
        groundLevel.insert(Interval(MY_MIN, MY_MAX));

        // iterate over levels in descending order
        for (const int &height : heights) {
            auto current_level = vector<Interval>();
            auto range = levels.equal_range(height);
            for (auto it = range.first; it != range.second; ++it) {
                current_level.push_back(it->second);
            }
            // sort
            try {
                sort(current_level.begin(), current_level.end());
            }
            catch (const invalid_argument &e) {
                cout << "exit w/ invalid argument";
                exit(1);
            }

            // trace
            cout << "at level " << height << ": ";
            trace(current_level);

            merge_intervals(current_level);
            cout << "after merge at " << height << ": ";
            trace(current_level);

            auto solved = vector<Interval>();
            // iterate over current level, find overlaps
            for(const auto& current_top : current_level) {

                auto overlaps = list<Interval>();
                bool first_found = false;
                Interval first = Interval(0,0);

                for (auto it = groundLevel.begin(); it != groundLevel.end(); ++it) {
                    if (it->overlaps(current_top) || it->getEnd() <= current_top.getStart()) {
                        first = Interval(it->getStart(), it->getEnd());
                        first_found = true;
                        break;
                    }
                }

                if (first_found && first.overlaps(current_top))
                    overlaps.push_back(first);

                for (auto it = groundLevel.rbegin(); it != groundLevel.rend(); ++it) {
                    if (it->getStart() > current_top.getEnd())
                        break;
                                            
                    overlaps.push_back(*it);
                }

                // Remove adjacent duplicates from overlaps
                for (auto it = overlaps.begin(); it != overlaps.end(); ) {
                    auto next = it;
                    ++next;
                    if (next != overlaps.end() && it->equals(*next)) {
                        it = overlaps.erase(next);
                    } else {
                        ++it;
                    }
                }

                // TODO - make a structure to keep sorted intervals
                for (auto it = overlaps.begin(); it != overlaps.end(); ++it) {
                    Interval test = Interval(it->getStart(), it->getEnd());

                    if (!test.overlaps(current_top)) continue;
                    if (test.getEnd() == current_top.getStart()|| current_top.getEnd() == test.getStart()) continue;

                    groundLevel.erase(test);

                    if (current_top.contains(test) || current_top.equals(test)) {
                        solved.push_back(Interval(test.getStart(), test.getEnd()));
                    }
                    else if (test.contains(current_top))
                    {
                        if (test.getStart() != current_top.getStart())
                            groundLevel.insert(Interval(test.getStart(), current_top.getStart()));
                        if (test.getEnd() != current_top.getEnd())
                            groundLevel.insert(Interval(current_top.getEnd(), test.getEnd()));
                        solved.push_back(current_top);
                    }
                    else if (test.getEnd() < current_top.getEnd()) {
                        solved.push_back(Interval(current_top.getStart(), test.getEnd()));
                        groundLevel.insert(Interval(test.getStart(), current_top.getStart()));
                    }
                    else { // current_top.end > test.end
                        solved.push_back(Interval(test.getStart(), current_top.getEnd()));
                        groundLevel.insert(Interval(current_top.getEnd(), test.getEnd()));
                    }
                }

            }

            cout << "solved now " << height << ": ";
            trace(solved);
        }

        return vector<vector<int>>();
    }

private:
    vector<Interval> merge_intervals(const vector<Interval> &sorted) {
        list<Interval> res;
        int i = 0;
        while (i < (int)sorted.size()) {
            Interval curr = sorted[i++];
            int j = i;
            while (j < (int)sorted.size()) {
                const Interval &next = sorted[j];
                if (!curr.overlaps(next)) break;
                curr = curr.merge(next);
                i++; j++;
            }
            res.push_back(curr);
        }
        return vector<Interval>(res.begin(), res.end());
    }

    void trace(const vector<int> &v) const {
        cout << "[";
        for (size_t i = 0; i < v.size(); i++) {
            if (i) cout << ", ";
            cout << v[i];
        }
        cout << "]" << endl;
    }

    void trace(const vector<Interval> &iv) const {
        for (size_t i = 0; i < iv.size(); i++) {
            if (i) cout << " ";
            cout << iv[i].to_string();
        }
        cout << endl;
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
