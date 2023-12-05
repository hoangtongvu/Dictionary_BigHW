package Dictionary;

import Main.ProjectDirectory;
import Word.WordBlock;

import java.io.*;
import java.util.*;
import java.util.List.*;
import java.lang.String;

import static java.util.Collections.sort;

public class SearchHistory {
    private static SearchHistory instance;
    private static File historyFile;
    private static final String path = ProjectDirectory.resourcesPath + "/data/searchHistory.txt";

    private SearchHistory() {
        wordHistory = new ArrayList<>();
        historyFile = new File(path);
        loadHistory();
    }

    public static SearchHistory getInstance() {
        if (instance == null) {
            instance = new SearchHistory();
        }
        return instance;
    }

    private List<String> wordHistory;


    public List<String> getWordHistory() {
        return wordHistory;
    }

    //Load when session start
    public void loadHistory() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(historyFile));
            String line;
            while ((line = reader.readLine()) != null) {
                wordHistory.add(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *
     * @param newWord insert new word into current ArrayList to display for current session.
     *                Content written to wordHistory.txt will not be loaded until the next session
     */
    public void updateHistory(String newWord) {
        boolean existed = false;
        for (String word : wordHistory) {
            if (word.equals(newWord)) {
                wordHistory.remove(word);
                wordHistory.add(0,newWord);
                existed = true;
                break;
            }
        }
        if (!existed) {
            wordHistory.add(0, newWord);
        }
        saveStatus();
    }

    public void clearHistory() {
        wordHistory.clear();
        saveStatus();
    }

    public void saveStatus() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(historyFile, false));
            for (String word : wordHistory) {
                writer.write(word + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteWord(String targetWord) {
        for (String word : wordHistory) {
            if (word.equals(targetWord)) {
                wordHistory.remove(word);
                break;
            }
        }
        saveStatus();
    }


//    public static void main(String[] args) {
//        HashMap<String, String> map = new LinkedHashMap<>();
//        map.put("A", "B");
//        map.put("C", "D");
//        map.
//    }
}
