#include <iostream>
#include <string>
#include <algorithm>
#include <vector>
#include <unordered_map>
#include <set>
#include <list>

using namespace std;

class Interval {
private:
    int start;
    int end;
    int height;

    friend class IntervalTree;

public:
    Interval() : start(0), end(0), height(0) {}
    Interval(int start, int end, int height) : start(start), end(end), height(height) {}

    int getStart() const { return start; }
    int getEnd() const { return end; }
    int getHeight() const { return height; }

    int getLength() const { return end - start; }

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
        return "[" + std::to_string(this->start) + "," + std::to_string(this->end) + "," + std::to_string(this->height) + "]";
    }

    Interval merge(const Interval &next) const {
        int nstart = min(this->start, next.start);
        int nend = max(this->end, next.end);
        return Interval(nstart, nend, this->height);
    }
};

typedef map<int, Interval> Intervals;

// Map by lefts endpoints OPTIMIZE FOR BST/MEMORY LATER
class IntervalTree {
public:
    void insert(Interval &newInter) {
        int newleft = newInter.start;

        Intervals overlaps;
        findOverlaps(overlaps, newInter);
        if (overlaps.size() == 0) {
             lefts.emplace(newleft, newInter);
             return;
        }

        // shadow new interval from the other higher intervals
        vector<Interval> shadows;
        Interval* restartWith = nullptr;
        for (const auto& kv : overlaps) {
            const Interval& shadow = kv.second;

            // total shadow
            if (shadow.contains(newInter)) return;

            if (newInter.contains(shadow)) {
                restartWith = new Interval(shadow.end, newInter.end, newInter.height);
                newInter.end = shadow.start;
                this->addToTree(newInter);
                break;
            }

            // go left
            if (shadow.end > newInter.end)
            {
                newInter.end = shadow.start;
            }
            else if (shadow.start < newInter.start)     // go right
            {
                newInter.start = shadow.end;
            }

            if (newInter.getLength() == 0) break;
        }

        if (restartWith != nullptr)
            this->insert(*restartWith);
        else
            this->addToTree(newInter);

        return;
    }

    // Iterator access
    auto begin() { return lefts.begin(); }
    auto end() { return lefts.end(); }

private:
    Intervals lefts;

    void addToTree(Interval &newInter) {
        if (newInter.getLength() != 0)
        {
            lefts.emplace(newInter.start, newInter);
        }
    }

    void findOverlaps(Intervals& result, const Interval &iv) const {
        // Start iterator at closest left endpoint to iv.start
        auto it = lefts.lower_bound(iv.start);
        if (it != lefts.begin() && (it == lefts.end() || it->first != iv.start)) {
            --it;
        }
        for (; it != lefts.end(); ++it) {
            if (it->second.end < iv.start) continue; // skip lefties
            
            if (it->second.overlaps(iv)) {
                result[it->first] = it->second;
            }

            if (it->second.start > iv.end) break;
        }
    }
};

void mergeIntervals(list<Interval>& mergedIntervals, pair<unordered_multimap<int, Interval>::const_iterator, unordered_multimap<int, Interval>::const_iterator> range);

class Solution {
public:
    vector<vector<int>> getSkyline(vector<vector<int>>& buildings) {
        vector<vector<int>> answer;
        if (buildings.empty()) return answer;

        // insert all buildings by a level
        unordered_multimap<int, Interval> levels;
        for (const auto& b : buildings) {
            auto interval = Interval(b[0], b[1], b[2]);
            levels.insert({b[2], interval});            
        }

        // start w/ waterline
        IntervalTree skyline;
        // sort heights
        set<int> heights;
        for (const auto& kv : levels)
            heights.insert(kv.first);

        // iterate over levels in descending order
        for (auto it = heights.crbegin(); it != heights.crend(); ++it) {
            int height = *it;
            cout << "at height " << height << endl;

            // merge all intervals at this height
            list<Interval> mergedIntervals;
            auto range = levels.equal_range(height);
            mergeIntervals(mergedIntervals, range);

            for (auto it = mergedIntervals.begin(); it != mergedIntervals.end(); ++it) {
                auto myInterval = *it;
                skyline.insert(myInterval);
            }

            // cout << "skyline: " ;
            // for(auto it = skyline.begin(); it != skyline.end(); ++it) {
            //     cout << it->second.to_string() << " ";
            // }
            // cout << endl;
        }

        // compute solution
        vector<vector<int>> ret;
        ret.reserve(500);
        bool first = true;
        int last = -1;
        for(auto it = skyline.begin(); it != skyline.end(); ++it) {
            Interval current = it->second;
            last = current.getEnd();
            if (first) {
                ret.push_back({current.getStart(), current.getHeight()});
                first = false;
                continue;
            }

            Interval prev = (std::prev(it))->second;
            if(current.getStart() > prev.getEnd()) {
                // glue
                ret.push_back({prev.getEnd(), 0});
            }
            ret.push_back({current.getStart(), current.getHeight()});
        }
        ret.push_back({last, 0});

        return ret;
    }
};

void mergeIntervals(list<Interval>& mergedIntervals,
    pair<unordered_multimap<int, Interval>::const_iterator, unordered_multimap<int, Interval>::const_iterator> range) {

    // merge by left endpoint
    Intervals currentLevel;
    for (auto it = range.first; it != range.second; ++it) {
        const Interval& interval = it->second;
        const int start = interval.getStart();

        if (currentLevel.find(it->second.getStart()) == currentLevel.end()) {
            currentLevel[start] = interval;
            continue;
        }

        auto existing = currentLevel[start];
        if (interval.contains(existing)) continue;

        currentLevel[start] = interval;
    }

    // move to list
    mergedIntervals.clear();
    for (auto it = currentLevel.cbegin(); it != currentLevel.cend(); ++it)
    {
        mergedIntervals.push_back(it->second);
    }

    // clear overlaps
    for (auto it = mergedIntervals.begin(); it != mergedIntervals.end(); ) {
        auto current = *it;
        auto nextIt = std::next(it);
        if (nextIt == mergedIntervals.end()) break;
        if (current.overlaps(*nextIt)) {
            Interval merged = current.merge(*nextIt);
            *nextIt = merged;
            it = mergedIntervals.erase(it); // erase returns the next valid iterator
        } else {
            ++it;
        }
    }
}

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
