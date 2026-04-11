import java.util.*;

// https://leetcode.com/problems/task-scheduler
public class Lbuild621tasks {

    public static void main(String[] args) {
        char[] tasks = {'A', 'A', 'A', 'B','B','B'};
        Solution s = new Solution();
        int ans = s.leastInterval(tasks, 2);
        System.out.println(ans);
    } 

}

class Solution
{
    public int leastInterval(char[] tasks, int cooldown)
    {
        PriorityQueue<Elem> maxHeap = new PriorityQueue<>(
            (a,b) -> {
                if (b.amount != a.amount)
                    return Integer.compare(b.amount, a.amount);
                else
                    return Integer.compare(a.myChar, b.myChar);
            }
        );

        int freq[] = getFreq(tasks);
        for (int i = 0; i < freq.length; i++)
        {
            if (freq[i] > 0)
                maxHeap.add(new Elem.Builder().amount(freq[i]).myChar((char)(i + 'A')).build());
        }

        int ans = 0;
        LinkedList<Elem> stack = new LinkedList<>();
        while (!maxHeap.isEmpty())
        {
            for (int i = 0; i <= cooldown; i++)
            {
                Elem curr = maxHeap.poll();
                ans++;
                
                if (--curr.amount > 0)
                    stack.add(curr);
                
                if (maxHeap.isEmpty())
                {
                    if (stack.isEmpty()) return ans;
                        
                    ans += cooldown - i;
                    break;
                }
            }
            
            while (!stack.isEmpty())
                maxHeap.add(stack.removeLast());
        }
        
        return ans;
    }

    private int[] getFreq(char[] tasks)
    {
        int ans[] = new int[26];
        for (int i = 0; i < tasks.length; i++)
        {
            int ix = tasks[i] - 'A';
            ans[ix] += 1;
        }
        return ans;
    }
    
    private static class Elem
    {
        int  amount;
        char myChar;

        private Elem(Builder builder)
        {
            this.amount = builder.amount;
            this.myChar = builder.myChar;
        }

        public static class Builder
        {
            private int amount;
            private char myChar;

            public Builder amount(int amount)
            {
                this.amount = amount;
                return this;
            }

            public Builder myChar(char myChar)
            {
                this.myChar = myChar;
                return this;
            }

            public Elem build()
            {
                return new Elem(this);
            }
        }

        @Override
        public String toString()
        {
            return "[" + amount + ", " + myChar + "]";
        }
    }    

public int[] solution(int[] roadA, int[] roadB) {
    int n = roadA.length;
    int m = roadB.length;
    int[] results = new int[n];
    
    // Test each starting position in roadA
    for (int start = 0; start < n; start++) {
        // Visited trackers for the CURRENT route
        boolean[] visitedA = new boolean[n];
        boolean[] visitedB = new boolean[m];
        
        int currRoad = 0; // 0 represents roadA, 1 represents roadB
        int currIdx = start;
        int distance = 0;
        
        while (true) {
            if (currRoad == 0) { // Currently on Road A
                if (visitedA[currIdx]) break; // Already visited? Stop jumping.
                
                visitedA[currIdx] = true;
                currIdx = roadA[currIdx]; // Jump to the index in Road B dictated by roadA
                currRoad = 1; // Now on Road B
                distance++;
            } else { // Currently on Road B
                if (visitedB[currIdx]) break; // Already visited? Stop jumping.
                
                visitedB[currIdx] = true;
                currIdx = roadB[currIdx]; // Jump to the index in Road A dictated by roadB
                currRoad = 0; // Now on Road A
                distance++;
            }
        }
        
        // At the end of the simulation, save the total distance covered
        results[start] = distance;
    }
    
    return results;
}

}
