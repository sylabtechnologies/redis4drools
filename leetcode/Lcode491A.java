import java.util.Arrays;

// https://leetcode.com/problems/trim-trailing-vowels
public class Lcode491A {
    public static void main(String[] args) {
        Solution solution = new Solution();

        var result = solution.trimTrailingVowels("idea");
        System.out.println("result: " + result);
    }

}

class Solution {
    private static final char[] VOWELS = {'a', 'e', 'i', 'o', 'u'};

    public String trimTrailingVowels(String s) {

        int cutoff = s.length();
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            boolean isVowel = false;
            for (char v : VOWELS) {
                if (c == v) {
                    isVowel = true;
                    break;
                }
            }

            if (isVowel) {
                cutoff--;
            }
            else {
                break;
            }
        }

        return s.substring(0, cutoff);
    }
}