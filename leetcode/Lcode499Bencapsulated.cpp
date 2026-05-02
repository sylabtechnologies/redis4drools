// freq sort beats 90%
// https://leetcode.com/problems/sort-vowels-by-frequency

class Vowel {
    char value;
    int firstOccurrence;
    int frequency;

    public:
        Vowel(char c, int firstOccurrence) : value(c), firstOccurrence(firstOccurrence), frequency(0) {}

        char getValue() const { return value; }
        int getFirstOccurrence() const { return firstOccurrence; }

        int getFrequency() const { return frequency; }
        void frequencyIncrement() { frequency++; }
        void frequencyDecrement() { frequency--; }

        bool operator<(const Vowel& other) const {
            if (frequency == other.frequency) {
                return firstOccurrence < other.firstOccurrence;
            }
            return frequency > other.frequency;
        }

        static bool isVowel(const char c);

        std::string to_string() const {
            return std::string("Vowel{value=") + value +
                   ", firstOccurrence=" + std::to_string(firstOccurrence) +
                   ", frequency=" + std::to_string(frequency) + "}";
        }
};

bool Vowel::isVowel(const char c) {
    char lc = tolower(c);
    return lc == 'a' || lc == 'e' || lc == 'i' || lc == 'o' || lc == 'u';
}

class Solution {
public:
    string sortVowels(string s) {
        std::map<char, Vowel> myVowels;

        if (s.empty()) {
            return s;
        }

        for (int i = 0; i < s.size(); i++) {
            char c = s[i];
            if (Vowel::isVowel(c)) {
                auto it = myVowels.find(c);
                if (it == myVowels.end()) {
                    auto newvowel = Vowel(c, i);
                    newvowel.frequencyIncrement();
                    myVowels.insert({c, newvowel});
                } else {
                    it->second.frequencyIncrement();
                }
            }
        }

        // sorting a bit
        std::vector<Vowel> occuredVowels;
        for (auto& kv : myVowels) {
            occuredVowels.push_back(kv.second);
        }

        std::sort(occuredVowels.begin(), occuredVowels.end());

        if (occuredVowels.empty()) {
            return s;
        }

        vector<char> result;
        auto iter = occuredVowels.begin();
        auto currentVowel = *iter;
        for (int i = 0; i < s.size(); i++) {
            if (Vowel::isVowel(s[i])) {
                if (currentVowel.getFrequency() == 0) {
                    iter++;
                    if (iter == occuredVowels.end()) break;
                    currentVowel = *iter;
                }

                s[i] = currentVowel.getValue();
                currentVowel.frequencyDecrement();
            }
        }

        return s;
    }

};
