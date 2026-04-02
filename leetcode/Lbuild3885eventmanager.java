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
    PriorityQueue<Event> q; //todo - replace this w/ faster tree map
    HashMap<Long, Event> events;

    public EventManager(int[][] eventsArr) {
        this.q = new PriorityQueue<>(
            (a, b) ->(b.pri() != a.pri() ?
                Long.compare(b.pri(), a.pri()) :
                Long.compare(a.id(), b.id()))
        );
        this.events = new HashMap<>();

        for (var e : eventsArr) {
            var myEvent = new Event(e[1], e[0]);
            this.events.put(new Long(e[0]), myEvent);
            this.q.add(myEvent);
        }

    }
    
    public void updatePriority(long eventId, long newPriority) {
        var oldone = this.events.get(eventId);
        var replacer = new Event(newPriority, eventId);
       
        this.q.remove(oldone);
        this.events.remove(eventId);
        this.events.put(eventId, replacer);
        this.q.add(replacer);
    }
    
    public int pollHighest() {
        if (this.q.isEmpty()) {
            return -1;
        }
        return (int) q.poll().id();
    }
}

record Event(long pri, long id) {}

