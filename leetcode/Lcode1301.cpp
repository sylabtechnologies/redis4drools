#include <iostream>
#include <vector>
#include <list>
using namespace std;

// https://leetcode.com/problems/number-of-paths-with-max-score/
// solve by propagating the best score and number of paths from S to E

class WayPoint {
public:
    int score;
    int numPaths;
    WayPoint(int score, int num) : score(score), numPaths(num) {}
    void copy(const WayPoint& pt) {
        score = pt.score;
        numPaths = pt.numPaths;
    }
    void addScore(int incr) { score += incr; }
    void setPath(int num) { numPaths = num; }
    void combinePaths(const WayPoint& pt, int maxWays) {
        numPaths = (numPaths + pt.numPaths) % maxWays;
    }
};

class Solution {
public:
    static const int MAXWAYS = 1000000007;

    vector<int> pathsWithMaxScore(const vector<string>& board) {
        int len = board.size();
        if (len != board[0].size())
            throw invalid_argument("fix square");

        vector<vector<int>> grid = converttoInt(board);
        vector<WayPoint*> dp(len, nullptr);
        dp[0] = new WayPoint(0, 1);

        for (int j = 1; j < len; j++) {
            if (grid[0][j] < 0) break;
            dp[j] = new WayPoint(dp[j-1]->score + grid[0][j], dp[j-1]->numPaths);
        }

        WayPoint* diagonJump = nullptr;

        for (int i = 1; i < len; i++) {
            bool pathFound = false;
            for (int j = 0; j < len; j++) {
                int cell = grid[i][j];
                if (cell < 0) {
                    diagonJump = dp[j];
                    dp[j] = nullptr;
                    continue;
                }
                if (j == 0) {
                    if (dp[0] != nullptr) {
                        dp[0]->addScore(cell);
                        pathFound = true;
                    }
                    diagonJump = nullptr;
                    continue;
                }
                if (dp[j] == nullptr && diagonJump != nullptr) {
                    dp[j] = new WayPoint(diagonJump->score, diagonJump->numPaths);
                    dp[j]->addScore(cell);
                    pathFound = true;
                    diagonJump = nullptr;
                    continue;
                }
                if (dp[j] == nullptr) {
                    if (dp[j-1] != nullptr) {
                        dp[j] = new WayPoint(dp[j-1]->score, dp[j-1]->numPaths);
                        dp[j]->addScore(cell);
                        pathFound = true;
                    }
                    continue;
                }
                if (dp[j-1] == nullptr) {
                    dp[j]->addScore(cell);
                    diagonJump = nullptr;
                    pathFound = true;
                    continue;
                }
                pathFound = true;
                if (dp[j-1]->score > dp[j]->score) {
                    dp[j]->copy(*dp[j-1]);
                    dp[j]->addScore(cell);
                } else if (dp[j-1]->score < dp[j]->score) {
                    dp[j]->addScore(cell);
                } else {
                    dp[j]->addScore(cell);
                    dp[j]->combinePaths(*dp[j-1], MAXWAYS);
                }
            }
            if (!pathFound) {
                for (auto ptr : dp) if (ptr) delete ptr;
                return {0, 0};
            }
        }
        vector<int> result = {dp[len-1] ? dp[len-1]->score : 0, dp[len-1] ? dp[len-1]->numPaths : 0};
        for (auto ptr : dp) if (ptr) delete ptr;
        return result;
    }

private:
    vector<vector<int>> converttoInt(const vector<string>& board) {
        int len = board.size();
        vector<vector<int>> result(len, vector<int>(len));
        for (int i = len - 1; i >= 0; i--) {
            int row = len - 1 - i;
            for (int j = len - 1; j >= 0; j--) {
                char c = board[i][j];
                int ix = len - 1 - j;
                switch(c) {
                    case 'E':
                    case 'S':
                        result[row][ix] = 0;
                        break;
                    case 'X':
                        result[row][ix] = -1;
                        break;
                    default:
                        result[row][ix] = c - '0';
                        if (result[row][ix] < 0 || result[row][ix] > 9)
                            throw invalid_argument("fix cells");
                }
            }
        }
        return result;
    }
};

int main() {
    Solution solution = Solution();

    vector<string> board = {"E23","2X2","12S"};
    auto solved = solution.pathsWithMaxScore(board);
    cout << solved[0] << " " << solved[1] << endl;

    board = {"E12","1X1","21S"};
    solved = solution.pathsWithMaxScore(board);
    cout << solved[0] << " " << solved[1] << endl;

    return 0;
}

