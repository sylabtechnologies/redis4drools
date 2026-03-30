import java.util.*;

public class Lbuild140wordbreak {
    public static void main(String[] args) {
        Solution solution = new Solution();

        String s = "catsanddog";
        List<String> wordDict = Arrays.asList("cat","cats","and","sand","dog");
        var result = solution.wordBreak(s, wordDict);
        System.out.println("result: " + result);
    }

}

class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {

        String words[] = new String[wordDict.size()];
        int maxWordLength = 0;
        for (int i = 0; i < wordDict.size(); i++) {
            words[i] = wordDict.get(i);
            maxWordLength = Math.max(maxWordLength, words[i].length());
        }

        StreamChecker streamChecker = new StreamChecker(words);

        WordNode root = new WordNode(null, 0, null);
        Deque<WordNode> queue = new LinkedList<>();
        queue.addFirst(root);

        // BFS queue to construct the answer
        List<String> answer = new ArrayList<>();
        while (!queue.isEmpty()) {
            var elem = queue.removeFirst();
            var start = elem.startsAt();

            System.out.println("working on: " + elem.word());

            int letterCount = 0;
            streamChecker.clear();
            for (char c : s.substring(start).toCharArray()) {
                letterCount++;
                var nextWord = streamChecker.query(c);
                if (nextWord != null) {
                    System.out.println("found word: " + nextWord);

                    int weAreAt = start + nextWord.length();
                    WordNode child = new WordNode(nextWord, weAreAt, elem);

                    if (weAreAt != s.length()) {
                        elem.addChild(child);
                        queue.addLast(child);
                    }
                    else {
                        answer.add(traverseWordTree(child));
                    }
                }
                else {
                    if (letterCount > maxWordLength) {
                        clearWordTree(elem);
                        break;
                    }
                }
            }

        }
        return answer;
    }

    private String traverseWordTree(WordNode node) {
        List<String> words = new ArrayList<>();
        while (node != null && node.word() != null) {
            words.add(node.word());
            node = node.parent();
        }
        Collections.reverse(words);
        return String.join(" ", words);
    }

    private void clearWordTree(WordNode node) {
        if (node == null || node.parent() == null) return;
        node.parent().children().remove(node);
        if (node.parent().children().isEmpty() && node.parent().word() != null) {
            clearWordTree(node.parent());
        }
    }
}

record WordNode(
    String word,
    int startsAt,
    WordNode parent,
    List<WordNode> children
) {
    public WordNode(String word, int startsAt, WordNode parent) {
        this(word, startsAt, parent, new ArrayList<>());
    }

    public void addChild(WordNode child) {
        this.children.add(child);
    }
}

class StreamChecker
{
    Queue<TrieNode> queue;
    TrieNode root;

    public StreamChecker(String[] words)
    {
        root= new TrieNode();
        queue = new LinkedList<>();
        
        for (String w : words)
        {
            TrieNode curr = root;
            for (int i = 0; i < w.length(); i++)
            {
                char c = w.charAt(i);
                if (curr.children[c-'a'] == null)
                {
                  curr.children[c-'a'] = new TrieNode(); 
                }
                curr = curr.children[c-'a'];                 
            }
            curr.word = w;
        }        
    }
    
    public String query(char letter)
    {
        int size = queue.size();
        
        int i = 0;
        String foundWord = null;
        while (i < size)
        {
            TrieNode current = queue.poll();
            if (current.children[letter-'a'] != null)
            {
                if (current.children[letter-'a'].word != null)
                    foundWord = current.children[letter-'a'].word;
                queue.add(current.children[letter-'a']);
            }
            i++;
        }
    
        if (root.children[letter-'a'] != null)
        {
            if (root.children[letter-'a'].word != null) 
                foundWord = root.children[letter-'a'].word;
            queue.add(root.children[letter-'a']);
        }

        return foundWord;    
    }

    // reset to initial state
    public void clear()
    {
        queue.clear();
    }    
}

class TrieNode 
{ 
    TrieNode[] children = new TrieNode[26]; 
    String word; 

    TrieNode()
    { 
        word = null; 
    } 
}
