#include <iostream>
#include <vector>
#include <list>
#include <algorithm>
#include <array>
using namespace std;

// https://leetcode.com/problems/sum-of-primes-between-number-and-its-reverse/
// NOW IT IS @70%!

#define LOG(x) std::cout << (x) << std::endl

// Sieve computed once at compile time for [0, 9999]
static constexpr int SIEVE_LIMIT = 9999;

static constexpr std::array<bool, SIEVE_LIMIT + 1> buildSieve() {
    std::array<bool, SIEVE_LIMIT + 1> sieve = {};
    sieve[0] = sieve[1] = true;  // not prime
    for (int i = 2; i * i <= SIEVE_LIMIT; i++) {
        if (!sieve[i])
            for (int j = i * i; j <= SIEVE_LIMIT; j += i)
                sieve[j] = true;  // mark as not prime
    }
    return sieve;
}

static constexpr auto SIEVE = buildSieve();  // evaluated at compile time

class Solution {
public:
    // Reads from the compile-time sieve — no runtime computation
    vector<int> buildPrimes(int start, int end) {
        vector<int> primes;
        for (int i = start; i <= end; i++) {
            if (i >= 2 && !SIEVE[i])
                primes.push_back(i);
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