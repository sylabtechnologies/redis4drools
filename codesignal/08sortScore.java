package com.app;

import java.util.*;

public class App {
    public List<int[]> analyzeCompetition(String logs) {
        
        // record by student id
        HashMap<Integer, Student> records = new HashMap<>();
        var stringRecords = logs.split(", ");
        
        for (var rec : stringRecords) {
            String tokens[] = rec.split(" ");
            int id = Integer.parseInt(tokens[0]);
            boolean success = tokens[1].equals("solve");
            int score = 0;
            if (success) score = Integer.parseInt(tokens[3]);
            
            Student oldObj = records.get(id);
            Student replaceObj = null;

            if (oldObj != null) {
                if (success) {
                    replaceObj = new Student(id, score + oldObj.score(), 1 + oldObj.success(), oldObj.penalty());
                }
                else {
                    replaceObj = new Student(id, oldObj.score(), oldObj.success(), 1 + oldObj.penalty());
                }
            }
            else {
                if (success) {
                    replaceObj = new Student(id, score, 1, 0);
                }
                else {
                    replaceObj = new Student(id, 0, 0, 1);
                }
            }
            
            records.put(id, replaceObj);
        }
        
        System.out.println(records);

        // sort by score
        TreeMap<Integer, Student> scoreSort = new TreeMap<>(Collections.reverseOrder());
        for (var elem : records.values()) {
            if (elem.success() == 0) continue;
            scoreSort.put(elem.score(), elem);
        }

        ArrayList<int[]> ret = new ArrayList<>(100);
        for (var elem : scoreSort.values()) {
            int answers[] = new int[4];

            answers[0] = elem.id();
            answers[1] = elem.score();
            answers[2] = elem.success();
            answers[3] = elem.penalty();

            ret.add(answers);
        }

        return ret;
    }
}

record Student(int id, int score, int success, int penalty) {}