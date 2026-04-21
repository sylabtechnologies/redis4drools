import java.util.*;

// sort by stats
// "alice@example.com, Hello, 08:00; bob@example.com, Meeting, 09:00; 
// alice@example.com, Re: Hello, 10:00"


public class App {
    public List<String> organizeInbox(String inboxString) {

        HashMap<String, Integer> users = new HashMap<>();
        String[] rows = inboxString.split(";");
    
        for (String row : rows) {
            var email = row.split(", ")[0].trim();
            int count = users.getOrDefault(email, 0) + 1;
            users.put(email, count);
        }
        
        TreeMap<Integer, ArrayList<String>> countList = new TreeMap<>();
        for (var entry : users.entrySet()) {
            var user  = entry.getKey(); 
            var count = entry.getValue(); 
            
            var byCount = countList.getOrDefault(count , new ArrayList<String>());
            byCount.add(user);
            countList.put(count, byCount);
        }

        ArrayList<String> ret = new ArrayList<>(200);
        for (var entry : countList.descendingMap().entrySet()) {
            var count = entry.getKey();
            var valuesArray = entry.getValue();
            Collections.sort(valuesArray);  // sort the small patches only
            
            for (var row : valuesArray) {
              ret.add(row + " " + count);
            }
        }
        
        // System.out.println(ret);
        
        return ret;
    }
}
