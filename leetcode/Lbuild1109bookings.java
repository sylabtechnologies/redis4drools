import java.util.*;

// https://leetcode.com/problems/corporate-flight-bookings/description/
class Solution {
    public int[] corpFlightBookings(int[][] bookings, int n) {

        int result[] = new int[n];
        for (var booking : bookings) {

            for (int i = booking[0]; i <= booking[1]; i++) {
                result[i - 1] += booking[2];
            }
        }

        return result;
    }
}