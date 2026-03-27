import java.util.*;

// https://leetcode.com/problems/count-residue-prefixes
public class Lcode484A {
    public static void main(String[] args) {
        Solution solution = new Solution();

        var result = solution.residuePrefixes("bbbb");
        System.out.println("result: " + result);
    }

}

class Solution {
    public int residuePrefixes(String s) {
        HashSet<Character> foundChars = new HashSet<>();
        int distinctChars = 0;
        int result = 0;
        int prefixLen = 0;

        for (char c : s.toCharArray()) {
            prefixLen++;
            if (!foundChars.contains(c)) {
                foundChars.add(c);
                distinctChars++;
            }

            if (distinctChars  == prefixLen % 3)
                result++;

        }

        return result;
    }
}