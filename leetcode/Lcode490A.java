import java.util.Arrays;

// https://leetcode.com/problems/find-the-score-difference-in-a-game/description/
public class Lcode490A {
    public static void main(String[] args) {
        Solution solution = new Solution();

        int n[] = {12,76,55,5,4,12,28,30,65};
        var result = solution.scoreDifference(n);
        System.out.println("score: " + result);
    }

}

class Solution {
    public int scoreDifference(int[] n) {
        var firstIsActive = true;
        var firstScore = 0;
        var secondScore = 0;

        for (int i = 0; i < n.length; i++) {
            if (n[i] % 2 == 1) {
                firstIsActive = !firstIsActive;
            }

            if ((i + 1) % 6 == 0) {
                firstIsActive = !firstIsActive;
            }

            if (firstIsActive) {
                firstScore += n[i];
            } else {
                secondScore += n[i];
            }
        }

        return firstScore - secondScore;
    }
}