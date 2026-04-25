package finalProjectOO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FinalProjectOO {
    private ArrayList<String> words;
    private String text;

    public FinalProjectOO(String filename) {
        words = new ArrayList<>();
        text = "";

        try {
            Scanner file = new Scanner(new File(filename));
            while (file.hasNextLine()) {
                String line = file.nextLine();
                text += line + " ";
                String[] lineWords = line.split("\\W+");
                for (String word : lineWords) {
                    if (!word.isEmpty()) {
                        words.add(word.toLowerCase());
                    }
                }
            }
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
    }

    // Question #1
    public int countTotalWords() {
        return words.size();
    }

    // Question #2
    public int countUniqueWords() {
        return new HashSet<>(words).size();
    }

    // Question #3
    public Map.Entry<String, Integer> mostCommonWord(Set<String> stopWords) {
        Map<String, Integer> freq = new HashMap<>();
        for (String word : words) {
            if (stopWords != null && stopWords.contains(word)) continue;
            freq.put(word, freq.getOrDefault(word, 0) + 1);
        }
        return Collections.max(freq.entrySet(), Map.Entry.comparingByValue());
    }

    // Question #4
    public List<String> filterWordsByLength(int minLength) {
        List<String> result = new ArrayList<>();
        for (String word : words) {
            if (word.length() >= minLength) result.add(word);
        }
        return result;
    }

    // Question #5
    public double averageWordLength() {
        int totalLength = 0;
        for (String word : words) totalLength += word.length();
        return (double) totalLength / words.size();
    }

    // Question #6
    public int countSentences() {
        String[] sentences = text.split("[.!?]");
        return sentences.length;
    }

    // Question #7
    public Map<Character, Integer> punctuationCount() {
        Map<Character, Integer> counts = new HashMap<>();
        char[] punctuations = {',', '.', '!', '?', ':', ';'};
        for (char c : text.toCharArray()) {
            for (char p : punctuations) {
                if (c == p) counts.put(p, counts.getOrDefault(p, 0) + 1);
            }
        }
        return counts;
    }

    // Question #8
    public long countWordsStartingWith(char letter) {
        return words.stream().filter(w -> w.startsWith(String.valueOf(letter).toLowerCase())).count();
    }

    // Question #9
    public Set<String> findPalindromes() {
        Set<String> palindromes = new HashSet<>();
        for (String word : words) {
            if (word.length() > 1 && word.equals(new StringBuilder(word).reverse().toString())) {
                palindromes.add(word);
            }
        }
        return palindromes;
    }

    // Question #10
    public Map<Integer, Integer> wordLengthFrequency() {
        Map<Integer, Integer> freq = new HashMap<>();
        for (String word : words) {
            int len = word.length();
            freq.put(len, freq.getOrDefault(len, 0) + 1);
        }
        return freq;
    }

    // Question #11
    public List<String> longestWord() {
        int maxLength = words.stream().mapToInt(String::length).max().orElse(0);
        List<String> result = new ArrayList<>();
        for (String word : words) {
            if (word.length() == maxLength) result.add(word);
        }
        Collections.sort(result);
        return result;
    }

    // Question #12
    public Map<Character, Integer> letterFrequency() {
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : text.toLowerCase().toCharArray()) {
            if (Character.isLetter(c)) {
                freq.put(c, freq.getOrDefault(c, 0) + 1);
            }
        }
        return freq;
    }

    // Question #13
    public List<Map.Entry<String, Integer>> top5Words() {
        Map<String, Integer> freq = new HashMap<>();
        for (String word : words) {
            freq.put(word, freq.getOrDefault(word, 0) + 1);
        }
        List<Map.Entry<String, Integer>> list = new ArrayList<>(freq.entrySet());
        list.sort((a, b) -> b.getValue() - a.getValue());
        return list.subList(0, Math.min(5, list.size()));
    }

    // Question #14
    public int countChapters() {
        String[] lines = text.split("\n");
        int count = 0;
        for (String line : lines) {
            if (line.toLowerCase().startsWith("chapter")) count++;
        }
        return count;
    }

    // Question #15
    public Set<List<String>> findAnagrams() {
        Set<List<String>> anagrams = new HashSet<>();
        Map<String, List<String>> groups = new HashMap<>();
        for (String word : words) {
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            groups.putIfAbsent(key, new ArrayList<>());
            groups.get(key).add(word);
        }
        for (List<String> group : groups.values()) {
            if (group.size() > 1) {
                Collections.sort(group);
                for (int i = 0; i < group.size(); i++) {
                    for (int j = i + 1; j < group.size(); j++) {
                        anagrams.add(Arrays.asList(group.get(i), group.get(j)));
                    }
                }
            }
        }
        return anagrams;
    }

    // Question #16
    public int countPhrase() {
        String phrase = "Seaweed Brain".toLowerCase();
        String lowerText = text.toLowerCase();
        int count = 0, index = 0;
        while ((index = lowerText.indexOf(phrase, index)) != -1) {
            count++;
            index += phrase.length();
        }
        return count;
    }

    // Main driver to test
    public static void main(String[] args) {
        FinalProjectOO project = new FinalProjectOO("Book.txt");

        System.out.println("Q1 Total words: " + project.countTotalWords());
        System.out.println("Q2 Unique words: " + project.countUniqueWords());
        System.out.println("Q3 Most common word (excluding stopwords): " + project.mostCommonWord(Set.of("the", "a", "in", "of", "at")));
        System.out.println("Q4 Words >= 7 letters: " + project.filterWordsByLength(7).subList(0, 10));
        System.out.println("Q5 Average word length: " + project.averageWordLength());
        System.out.println("Q6 Total sentences: " + project.countSentences());
        System.out.println("Q7 Punctuation count: " + project.punctuationCount());
        System.out.println("Q8 Words starting with 's': " + project.countWordsStartingWith('s'));
        System.out.println("Q9 Palindromes: " + project.findPalindromes());
        System.out.println("Q10 Word length frequency: " + project.wordLengthFrequency());
        System.out.println("Q11 Longest word(s): " + project.longestWord());
        System.out.println("Q12 Letter frequency: " + project.letterFrequency());
        System.out.println("Q13 Top 5 words: " + project.top5Words());
        System.out.println("Q14 Chapters: " + project.countChapters());
        System.out.println("Q15 Anagrams: " + project.findAnagrams());
        System.out.println("Q16 Phrase 'Seaweed Brain' count: " + project.countPhrase());
