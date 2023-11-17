package WordEditing;

import java.util.ArrayList;
import java.util.List;

public class WordJSON {
    private String wordID;

    public String getWordID() {
        return wordID;
    }

    private List<NodeJSON> node_list = new ArrayList<>();

    public WordJSON() {

    }

    public WordJSON(String wordID) {
        this.wordID = wordID;
    }

    public void addNode(NodeJSON node) {
        node_list.add(node);
    }
}
