#include <iostream>
#include <vector>
#include <list>
#include <algorithm>
using namespace std;

// https://leetcode.com/problems/sum-of-primes-between-number-and-its-reverse/

#define LOG(x) std::cout << (x) << std::endl

class Solution {
public:
    // Sieve of Eratosthenes: all primes up to 9999
    vector<int> buildPrimes(int start, int end) {
        const int LIMIT = 9999;
        vector<bool> sieve(LIMIT + 1, true);
        sieve[0] = sieve[1] = false;
        for (int i = 2; i * i <= LIMIT; i++) {
            if (sieve[i])
                for (int j = i * i; j <= LIMIT; j += i)
                    sieve[j] = false;
        }
        vector<int> primes;
        for (int i = 2; i <= LIMIT; i++) {
            if (sieve[i]) {
                if (i < start || i > end) continue;
                primes.push_back(i);
            }            
        }
        return primes;
    }

    int reverseN(int n) {
        int rev = 0;
        while (n > 0) {
            rev = rev * 10 + n % 10;
            n /= 10;
        }
        return rev;
    }

    int sumOfPrimesInRange(int n) {
        if (n == 0) return 0;

        int reversed = reverseN(n);
        int start = std::min(n, reversed);
        int end   = std::max(n, reversed);

        vector<int> primes = buildPrimes(start, end);
        int sum = 0;
        for (int p : primes) {
            sum += p;
            // LOG(p);
        }
        return sum;
    }
};