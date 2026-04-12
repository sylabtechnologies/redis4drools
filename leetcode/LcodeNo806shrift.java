import java.util.*;

// https://leetcode.com/problems/number-of-lines-to-write-string

public class LcodeNo806shrift {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] widths = {4, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
        String s = "bbbcccdddaaa";
        int[] result = solution.numberOfLines(widths, s);
        System.out.println(Arrays.toString(result));
    }
    
}

class Solution {
    public int[] numberOfLines(int[] widths, String s) {
        var count = Arrays.stream(widths).count();
        if (count != 26) {
            throw new IllegalArgumentException("invalid font");
        }

        List<Letter> letters = new ArrayList<Letter>(1000);
        letters = s.chars()
            .mapToObj(c -> new Letter((char) c, widths[c - 'a']))
            .toList();  

        int currentWidth = 0;
        int ans[] = new int[] {0,0};
        count = 0;
        for (var l : letters) {
            if (currentWidth + l.width() <= 100) {
                currentWidth += l.width();
            }
            else {
                count++;
                ans[1] = currentWidth;
                currentWidth = l.width();
            }
        }

        if (currentWidth != 0) {
            count++;
            ans[1] = currentWidth;
        }

        ans[0] = (int) count;

        return ans;        
    }
}

record Letter(char c, int width) {}