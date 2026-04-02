import  java.util.*;

// https://leetcode.com/problems/design-event-manager
// todo - ok THIS WORKS FOR ints ONLY - update to longs
// and use a faster tree map
public class Lbuild3885eventmanager {
    public static void main(String[] args) {
        int[][] events = new int[][] {
            {5, 7},
            {2, 7},
            {9, 4}
        };

        EventManager eventManager = new EventManager(events);
        System.out.println(eventManager.pollHighest());
        eventManager.updatePriority(9, 7);
        System.out.println(eventManager.pollHighest());
        System.out.println(eventManager.pollHighest());
    }
}

class EventManager {
    PriorityQueue<Event> q;
    HashMap<Integer, Event> events;

    public EventManager(int[][] eventsArr) {
        this.q = new PriorityQueue<>(
            (a, b) ->(b.pri() != a.pri() ?
                Integer.compare(b.pri(), a.pri()) :
                Integer.compare(a.id(), b.id()))
        );
        this.events = new HashMap<>();

        for (var e : eventsArr) {
            var myEvent = new Event(e[1], e[0]);
            this.events.put(e[0], myEvent);
            this.q.add(myEvent);
        }
    
        System.out.println(this.events);
        System.out.println(this.q);

    }
    
    public void updatePriority(int eventId, int newPriority) {
        var oldone = this.events.get(eventId);
        var replacer = new Event(newPriority, eventId);
       
        this.q.remove(oldone);
        this.events.remove(eventId);
        this.events.put(eventId, replacer);
        this.q.add(replacer);

        System.out.println(this.events);
        System.out.println(this.q);
    }
    
    public int pollHighest() {
        System.out.println(this.events);
        System.out.println(this.q);
        if (this.q.isEmpty()) {
            return -1;
        }
        return q.poll().id();
    }
}

record Event(Integer pri, Integer id) {}

