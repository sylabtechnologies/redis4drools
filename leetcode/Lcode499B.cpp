#include <iostream>
#include <vector>
#include <array>
#include <list>

// freq sort
// https://leetcode.com/problems/sort-vowels-by-frequency

using namespace std;

class Solution {
public:
    string sortVowels(string s) {
        std::array<int, 26> freq;
        std::array<int, 26> firstOccurrence;

        if (s.empty()) {
            return s;
        }

        for (int i = 0; i < s.size(); i++) {
            char c = s[i];
            int index = c - 'a';

            if (isVowel(c)) {
                freq[index]++;
                if (firstOccurrence[index] == 0) {
                    firstOccurrence[index] = i + 1;
                }
            }
        }

        // sorting a bit
        std::list<char> occurringVowels;
        int vowelCount = 0;
        for (int i = 0; i < 26; i++) {
            char c = 'a' + i;
            if (freq[i] > 0 && isVowel(c)) {
                occurringVowels.push_back(c);
            }
        }
        
        if (occurringVowels.empty()) {
            return s;
        }

        occurringVowels.sort([&](char a, char b) {
            int indexOfA = a - 'a';
            int indexOfB = b - 'a';

            int freqA = freq[indexOfA];
            int freqB = freq[indexOfB];

            int occurenceA = firstOccurrence[indexOfA];
            int occurenceB = firstOccurrence[indexOfB];

            // same - sort by occurrence
            if (freqA == freqB) {
                return occurenceA < occurenceB; 
            }
            // or sort by freq
            return freqA > freqB; 
        });

        // rearranging
        char currentVowel = occurringVowels.front();

        vector<char> result;
        for (int i = 0; i < s.size(); i++) {
            if (isVowel(s[i])) {
                int currentIndex = currentVowel - 'a';

                if (freq[currentIndex] == 0) {
                    if (occurringVowels.empty()) break;
                    occurringVowels.pop_front();
                    currentVowel = occurringVowels.front();
                    currentIndex = currentVowel - 'a';
                }

                s[i] = currentVowel;
                freq[currentIndex]--;
            }
        }

        return s;
    }

    bool isVowel(char c) {
        c = tolower(c);
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }
};
