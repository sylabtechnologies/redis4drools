// package com.app;

import java.util.*;

public class App {
    public List<String> solution(String logs) {
        
        // borrowed books
        HashMap<Integer, Book> borrowed  = new HashMap<>();

        // sets sorted by duration then by book id
        // FIRST SORT will keep the order, increments will increase the last element
        TreeMap<Integer, TreeSet<Book>> sorted = new TreeMap<>();

        var stringRecords = logs.split(", ");
        for (var rec : stringRecords) {
            String tokens[] = rec.split(" ");
            int id = Integer.parseInt(tokens[0]);
            boolean borrow = tokens[1].equals("borrow");
            String time = tokens[2];

            // borrow
            if (borrow) {
                if (borrowed.containsKey(id)) throw new IllegalArgumentException("invalid borrow " + rec);

                var borrowTime = getTime(time);
                borrowed.put(id, new Book(id, borrowTime, null, 0));
            }
            // return
            else {
                var obj = borrowed.get(id);
                if (obj == null) throw new IllegalArgumentException("invalid return " + rec);

                // reset
                borrowed.remove(id);
                var returnTime = getTime(time);
                int lastBorrowDuration  = timeDiff(obj.borrowedAt(), returnTime);
                var duration  = obj.duration() + lastBorrowDuration;
                Book newobj = new Book(obj.id(), null, returnTime, duration);

                // replace
                var refSet = sorted.get(duration);
                if (refSet == null) {
                    var newSet = new TreeSet<Book>();
                    newSet.add(newobj);
                    sorted.put(duration, newSet);
                }
                else {
                    if (refSet.contains(obj)) refSet.remove(obj);
                    refSet.add(newobj);
                }
            }
        }
        
        // scan for max borrow time
        ArrayList<String> ret = new ArrayList<>(100);
        for (var elem : sorted.lastEntry().getValue()) {
            ret.add(String.format("%d %02d:%02d", elem.id(), elem.duration() / 60, elem.duration()  % 60));
        }

        return ret;
    }

    private static LibraryTime getTime(String time) {
        String hhmm[] = time.split(":");
        if (hhmm.length != 2) throw new IllegalArgumentException("invalid " + time);
        return new LibraryTime(Integer.parseInt(hhmm[0]), Integer.parseInt(hhmm[1]));
    }

    private static int timeDiff(LibraryTime t1, LibraryTime t2) {
        int ans = 60*(t2.hh() - t1.hh()) + t2.mm() - t1.mm();
        return ans;
    }

}

record LibraryTime(int hh, int mm) {}

record Book(int id, LibraryTime borrowedAt, LibraryTime returnedAt, int duration) implements Comparable<Book> {
    @Override
    public int compareTo (Book other) {
        return Integer.compare(this.id, other.id);
    }
}
