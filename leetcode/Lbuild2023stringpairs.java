
public class Lbuild2023stringpairs {
    public static void main(String[] args) {
        String fragments[] =  {"777","7","77","77"};
        String access = "7777";
        var s = new Solution();
        System.out.println(s.numOfPairs(fragments, access));
    }
}

class Solution {
    public int numOfPairs(String[] fragments, String access) {
        int count = 0;
        int n = fragments.length;

        if (n < 2) return 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) continue;

                String test = fragments[i] + fragments[j];
                if (test.equals(access)) {
                    count++;
//                    System.out.println(fragments[i] + ", " + fragments[j]);
                }

            }
        }

        return count;
    }

}