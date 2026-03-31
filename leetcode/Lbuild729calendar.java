import java.util.*;

// https://leetcode.com/problems/my-calendar-i/description/
public class Lbuild729calendar {
    public static void main(String[] args) {
        var calendar = new MyCalendar();
        System.out.println(calendar.book(10, 20));
        System.out.println(calendar.book(15, 25));
        System.out.println(calendar.book(20, 30));
    }
}

/// how to balance later - keep the number of intervals and after each 20 insertions,
/// rebuild the tree by traversing to the middle one
/// real calendar woule be month buckets w/ 15th as the root
class MyCalendar {
    IntervalTree myTree;

    public MyCalendar() {
        myTree = new IntervalTree();
        
    }
    
    public boolean book(int startTime, int endTime) {
        var newInterval = new Interval(startTime, endTime);

        if (myTree.overlaps(newInterval)) return false;
        myTree.insert(newInterval);
        return true;

    }
}

// 
class IntervalTree {
    Interval root;

    IntervalTree() {
        root = new Interval(Integer.MIN_VALUE, Integer.MIN_VALUE); // signal this is root
        root.left = null;
        root.right = null;
    }

    public void insert(Interval newInterval) {
        // first node
        if (root.left == null && root.right == null) {
            root.right = newInterval;
            return;
        }

        var current = root;
        while (true) {
            if (newInterval.end <= current.start) {
                if (current.left == null) {
                    current.left = newInterval;
                    break;
                } else {
                    current = current.left;
                }
            } else {
                if (current.right == null) {
                    current.right = newInterval;
                    break;
                } else {
                    current = current.right;
                }
            }
        }
    }

    public boolean overlaps(Interval newInterval) {

        var current = root;
        while (current != null) {

            if (current.start == Integer.MIN_VALUE) {
                // this is root
                current = current.right;
                continue;
            }

            if (current.overlaps(newInterval)) return true;

            if (newInterval.end <= current.start) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        
        return false;
    }

}

class Interval {
    // can have a parent pointer later for easy rebalancing
    Interval left, right;
    int start, end;

    public Interval(int s, int e) {
        this.start = s;
        this.end = e;
    }

    // zoom into overlaps operation
    public boolean overlaps(Interval other) {
        return this.start < other.end && other.start < this.end;
    }
}
