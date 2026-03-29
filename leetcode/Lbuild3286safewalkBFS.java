import java.util.*;

// https://leetcode.com/problems/find-a-safe-walk-through-a-grid
public class Lbuild3286safewalk {
    public static void main(String[] args) {
        Solution solution = new Solution();

        int health = 3;
        Integer gridArr[][] = {{0,1,1,0,0,0},{1,0,1,0,0,0},{0,1,1,1,0,1},{0,0,1,0,1,0}};
        // {{1,1,1},{1,0,1},{1,1,1}}; 5
        // {{0,1,0,0,0},{0,1,0,1,0},{0,0,0,1,0}}; 1

        List<List<Integer>> grid = new ArrayList<>();
        for (var row : gridArr) {
            grid.add(Arrays.asList(row));
        }

        var result = solution.findSafeWalk(grid, health);
        System.out.println("result: " + result);
        System.out.println("grid:\n" + grid);
    }
}

// THE TRICK = WOW  0-1 BFS
class Solution {

    public boolean findSafeWalk(List<List<Integer>> grid, int healthPoints) {
        if (grid == null || grid.isEmpty() || grid.get(0).isEmpty()) {
            return false;
        }

        int rows = grid.size();
        int cols = grid.get(0).size();
        int[][] intGrid = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            List<Integer> row = grid.get(i);
            for (int j = 0; j < cols; j++) {
                intGrid[i][j] = row.get(j);
            }
        }

        return findMinCost(intGrid) < healthPoints;
    }

    public int findMinCost(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        // 1. Initialize the static bounds for our Point class
        Point.maxX = rows;
        Point.maxY = cols;

        // 2. Setup the distance array to track the shortest path to each cell
        int[][] dist = new int[rows][cols];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        // 3. Setup the Deque for 0-1 BFS
        Deque<Point> deque = new LinkedList<>();
        
        // Start at top-left (0, 0)
        Point start = new Point(0, 0);
        deque.addFirst(start);
        
        // The starting cost is whatever value is at grid[0][0]
        dist[0][0] = grid[0][0];

        // 4. Run the BFS
        while (!deque.isEmpty()) {
            Point current = deque.pollFirst();
            int cx = current.x;
            int cy = current.y;

            // Optional: early exit if we are at the bottom-right corner
            if (cx == rows - 1 && cy == cols - 1) {
                return dist[cx][cy];
            }

            // Get all valid neighbors using your encapsulated method
            for (Point neighbor : Point.getNeighbors(current)) {
                int nx = neighbor.x;
                int ny = neighbor.y;
                int weight = grid[nx][ny];

                // If we found a strictly shorter path to this neighbor
                if (dist[cx][cy] + weight < dist[nx][ny]) {
                    dist[nx][ny] = dist[cx][cy] + weight;

                    // THE 0-1 BFS ROUTING RULE:
                    if (weight == 0) {
                        deque.addFirst(neighbor); // No cost? Prioritize it.
                    } else {
                        deque.addLast(neighbor);  // Cost of 1? Process it later.
                    }
                }
            }
        }

        // Return the shortest distance to the bottom right corner
        return dist[rows - 1][cols - 1];
    }

}

class Point {
    private static final int steps[][] = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; 
    static int maxX;
    static int maxY;

    int x, y;
    boolean isValid;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.isValid = true;
    }

    public Point(Point p, int[] dir) {
        this.x = p.x + dir[0];
        this.y = p.y + dir[1];

        if (this.x < 0 || this.x >= maxX || this.y < 0 || this.y >= maxY) {
           this.isValid = false; 
           return;
        }

        this.isValid = true;
    }

    public static List<Point> getNeighbors(Point p) {
        List<Point> nextPoints = new ArrayList<>();
        for (int[] step : steps) {
            Point nextPoint = new Point(p, step);
            if (nextPoint.isValid) {
                nextPoints.add(nextPoint);
            }
        }
        return nextPoints;
    }

    @Override
    public String toString() { return "[" + x + ", " + y + "]"; }
}

public class GridPathfinder {
    
}