import java.util.*;

public class Lbuild140DFSwordbreak {
    public static void main(String[] args) {
        Solution solution = new Solution();

        String s = "anothertestcaseforwordbreakproblemwithamuchlongerstringtoverifythatthememoizationisworkingcorrectlyandefficiently";
        List<String> wordDict = Arrays.asList("another", "test", "case", "for", "word", "break", "problem", "with", "a", "much", "longer", "string", "to", "verify", "that", "the", "memoization", "is", "working", "correctly", "and", "efficiently", "an", "other", "pro", "blem", "work", "ing", "correct", "ly");
        var result = solution.wordBreak(s, wordDict);
        System.out.println("result: " + result);
    }

}

class Solution {
    // Memoization map to cache results of previously computed subproblems.
    // Key: starting index in 's'. Value: list of valid sentence suffixes from this index.
    Map<Integer, List<String>> memo = new HashMap<>();
    
    WordDictionary dict;

    public List<String> wordBreak(String s, List<String> wordDict) {

        dict = new WordDictionary();
        for (String word : wordDict) {
            dict.addWord(word);
        }
        
        return dfs(s, 0);
    }

    // DFS with backtracking and memoization
    private List<String> dfs(String s, int start) {
        // EOL, we done
        if (start == s.length()) {
            return Arrays.asList("");
        }

        // --- Memoization Check ---
        if (memo.containsKey(start)) {
            var cachedResult = memo.get(start);
            return cachedResult;
        }

        List<String> validSentences = new ArrayList<>();

        // --- Backtracking / Exploration ---
        for (int end = start + 1; end <= s.length() && end - start <= dict.maxWordLength; end++) {
            String currentWord = s.substring(start, end);

            // If found
            if (dict.search(currentWord)) {
                
                List<String> subSentences = dfs(s, end);

                // attach found sentences
                for (String subSentence : subSentences) {
                    if (subSentence.isEmpty()) {
                        validSentences.add(currentWord);
                    } else {
                        // Not the last word
                        validSentences.add(currentWord + " " + subSentence);
                    }
                }
            }
        }

        memo.put(start, validSentences);

        return validSentences;
    }
}

class WordDictionary
{
    private TrieNode root;
    public int maxWordLength = 0;

    public WordDictionary()
    {
        root = new TrieNode();
    }
    
    public void addWord(String key)
    {
        maxWordLength = Math.max(maxWordLength, key.length());
        int level; 
        int length = key.length(); 
        int index; 

        TrieNode pCrawl = root; 

        for (level = 0; level < length; level++) 
        { 
            index = key.charAt(level) - 'a'; 
            if (pCrawl.children[index] == null) 
                pCrawl.children[index] = new TrieNode(); 

            pCrawl = pCrawl.children[index]; 
        } 

        pCrawl.isEndOfWord = true; 
    }
    
    public boolean search(String word)
    {
        TrieNode pCrawl = root;
        return helper(pCrawl, word, 0);
    }
    
    public boolean helper(TrieNode pCrawl, String key, int level)
    {
        int length = key.length(); 

        for (; level < length; level++) 
        { 
            char c  = key.charAt(level);
            
            if (c == '.') return helper2(pCrawl, key, level);
            
            int ix = c - 'a';
            
            if (pCrawl.children[ix] == null) 
                    return false; 

            pCrawl = pCrawl.children[ix]; 
        } 

        return (pCrawl != null && pCrawl.isEndOfWord); 
    }

    private boolean helper2(TrieNode pCrawl, String key, int level)
    {
        boolean found = false;
        for (int i = 0; i < TrieNode.ALPHABET_SIZE; i++)
        {
            if (pCrawl.children[i] == null) continue;

            found = helper(pCrawl.children[i], key, level + 1);
            if (found) break;
        }

        return found;
    }
}

class TrieNode 
{ 
    public static final int ALPHABET_SIZE = 26;
    TrieNode[] children = new TrieNode[ALPHABET_SIZE]; 
    boolean isEndOfWord; 

    TrieNode()
    { 
        isEndOfWord = false; 
        for (int i = 0; i < ALPHABET_SIZE; i++) 
            children[i] = null; 
    } 
}; 