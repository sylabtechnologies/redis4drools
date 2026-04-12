
public class SpyCode {
    public static void main(String[] args) {
        int fragments[] =  {1, 212, 12, 12};
        int access = 1212;
        var s = new Solution();
        System.out.println(s.howmany(fragments, access));
    }
}

class Solution {
    public int howmany(int[] fragments, int access) {
        int count = 0;
        int n = fragments.length;

        if (n < 2) return 0;

        // memoize the multiplier
        int multiplier[] =  new int[n];
        for (int i = 0; i < n; i++) {
            var charlen = String.valueOf(fragments[i]).length();
            multiplier[i] = (int) Math.pow(10, charlen);
        }
       
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) continue;

                var test = fragments[i] * multiplier[j] + fragments[j];
                if (test == access) {
                    count++;
                    System.out.println(fragments[i] + ", " + fragments[j]);
                }

            }
        }

        return count;
    }

}