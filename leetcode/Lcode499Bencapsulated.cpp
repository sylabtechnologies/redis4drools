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
};

bool Vowel::isVowel(const char c) {
    char index = tolower(c) - 'a';

    if (index == 14) {
        return true;
    }

    if (index == 16 || index == 24) {
        return false;
    }

    return index % 4 == 0;
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
            std::cout << c << std::endl;

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
