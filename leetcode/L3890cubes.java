import java.util.*;

public class L3890cubes {
    public static void main(String[] args) {
        Solution solution = new Solution();
        List<Integer> goodIntegers = solution.findGoodIntegers(4170);
        System.out.println("Result: " + goodIntegers);
    }
} 

// compute taxicab numbers
class Solution {
    private static int MAX = 1000000000;
    private static int cubes[] = new int[1001];

    static {
        for (int i = 1; i < cubes.length; i++) {
            cubes[i] = (int) Math.pow(i, 3);
        }
        
    }

    public List<Integer> findGoodIntegers(int n) {
        int max = (int) Math.cbrt(n);
        List<Integer> goodIntegers = new ArrayList<>();
        if (n < 2) return goodIntegers;

        HashMap<Integer, Integer> taxicab = new HashMap<>();

        for (int i = 1; i <= 1000; i++) {
            int oper1 = cubes[i];

            for (int j = i + 1; j <= 1000; j++) {
                int oper2 = cubes[j];

                if (oper1 + oper2 <= n) {
                    int sum = oper1 + oper2;
                    var taxifreq = taxicab.getOrDefault(sum , 0);
                    taxicab.put(sum, taxifreq + 1);                    
                }
            }
        }

        for (Map.Entry<Integer, Integer> entry : taxicab.entrySet()) {
            if (entry.getKey() <= n && entry.getValue() >= 2)
                goodIntegers.add(entry.getKey());
        }

        return goodIntegers.stream().sorted().toList();
    }
}