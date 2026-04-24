// package com.app;

import java.util.*;

public class App {

    public List<String> solution(String logs) {
        
        // records by book id, out there and on hand
        HashMap<Integer, Book> borrowed  = new HashMap<>();
        TreeMap<Integer, Integer> duration = new TreeMap<>();

        int maxBorrowTime = Integer.MIN_VALUE;

        var stringRecords = logs.split(", ");
        for (var rec : stringRecords) {
            String tokens[] = rec.split(" ");
            int id = Integer.parseInt(tokens[0]);
            boolean borrow = tokens[1].equals("borrow");
            String time = tokens[2];

            if (borrow) {
                if (borrowed.containsKey(id)) throw new IllegalArgumentException("invalid borrow " + rec);

                var borrowTime = getTime(time);
                borrowed.put(id, new Book(id, borrowTime, null));
            }
            else {
                var obj = borrowed.get(id);
                if (obj == null) throw new IllegalArgumentException("invalid return " + rec);
                borrowed.remove(id);

                var returnTime = getTime(time);
                int borrowDuration  = timeDiff(obj.borrowedAt(), returnTime);
                var newDuration     = duration.getOrDefault(id, 0) + borrowDuration;
                duration.put(id, newDuration);
                maxBorrowTime = Math.max(maxBorrowTime, newDuration);
            }
        }
        
        System.out.println(duration);

        // scan for max borrow time
        ArrayList<String> ret = new ArrayList<>(100);
        for (var elem : duration.entrySet()) {
            int id = elem.getKey();
            int time = elem.getValue();
            if (time < maxBorrowTime) continue;
            
            ret.add(String.format("%d %02d:%02d", id, time / 60, time % 60));
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

// BETTER REFACTOR TO BOOK_BORROW, AND BOOK STATS
// THEN SCAN & SORT THE CABBAGE PATCH BY ID
// use time api concepts
record LibraryTime(int hh, int mm) {}
record Book(int id, LibraryTime borrowedAt, LibraryTime returnedAt) {}
